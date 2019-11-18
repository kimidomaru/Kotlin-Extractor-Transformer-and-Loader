import org.slf4j.LoggerFactory
import vo.TimeEntry
import java.io.File
import java.text.Normalizer

class Extractor {
    protected val LOG = LoggerFactory.getLogger(Extractor::class.java)

    //Padrao Regex para retornar apenas caracteres UNICODE
    private val regexSemAcento = "\\p{InCombiningDiacriticalMarks}+".toRegex()

    //Expressao Regular do Empregado
    val padraoEmpregado = ";Empregado:".toRegex()

    //Expressao Regular dos dias
    val padraoDia = Regex("""\d{2}/\d{2} - \w{3}\;{3}.+""")

    //Expressao Regular da jornada de Trabalho
    //val patternJornada = Regex(""";Jornada.+ Abono\;{7}Jornada total\;{4}\d{2}:\d{2}""")

    val entradas: MutableList<TimeEntry> = ArrayList();

    fun CharSequence.unaccent(): String{
        val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
        return regexSemAcento.replace(temp, "")
    }
    fun extrair(): MutableList<TimeEntry>{

        var empregadoStr = "";
        var diaStr = "";
        var horasStr: String? = "";

        //Abrindo e lendo o arquivo
        val arquivo = "src/test/RelExtraPorPeriodo.csv"
        val linhas: List<String> = File(arquivo).readLines();

        //Percorrendo o arquivo e executando o restante do codigo para cada linha do mesmo
        linhas.forEach{ linha ->
            horasStr = ""
            //Se achar a expressão regular de empregado ...
            if(padraoEmpregado.containsMatchIn(linha)){
                val linhaSemAcento = linha.unaccent();
                //tira os ;
                val empregado = linhaSemAcento.replace(";","", true)
                empregadoStr = empregado.replace("Empregado:","");
                //println("$empregado")
            }
            //Se achar a expressão regular dos dias ...
            if(padraoDia.containsMatchIn(linha)){

                val linhaSemAcento = linha.unaccent();
                //le a linha inteira
                val linhaInteira = padraoDia.find(linhaSemAcento)!!.value
                //limita ate onde a informacao e necessaria
                val limite = linhaInteira.indexOf(";;;;;;;")
                //retira as informacoes desnecessarias que estao depois dos ;;;;;;;;
                val informacoes: String? = if(limite == -1) null
                else linhaInteira.substring(0, limite)


                if(informacoes != null){
                    //tira os ;
                    val dia = informacoes!!.replace(";;;"," - ")
                    //separa as informacoes pra criar os objetos
                    val infos:List<String> = dia.split(" - ")
                    //println(infos)
                    diaStr = infos[0]
                    if(infos.size > 2){
                        horasStr = infos[2]
                    }

                    if(!(horasStr.equals("")) and (!horasStr.equals(null))){
                        val obj = TimeEntry(empregadoStr, diaStr, horasStr!!)
                        entradas.add(obj)
                        LOG.debug(obj.toString())
                    }
                }
            }

        }
        LOG.info("Processo de extração concluído. Foram extraídos " + entradas.size + " elementos.\n")
        return entradas
    }
}