package domain

import etl.Employee
import etl.WorkDay
import vo.TimeEntry
import org.slf4j.LoggerFactory

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.TreeMap


class Transformer {
    protected val LOG = LoggerFactory.getLogger(Transformer::class.java)

    fun transformar(entradas: MutableList<TimeEntry>): MutableList<WorkDay> {
        val resultado = ArrayList<WorkDay>()
        val empregados = TreeMap<String, Employee>()

        for ((empregadoString, dataString, horasString) in entradas) {

            // Cria/Recupera o objeto do empregado
            var empregado: Employee? = empregados[empregadoString]

            if (empregado == null) {
                empregado = Employee(empregadoString)
                empregados[empregadoString] = empregado
            }

            // Cria o objeto da jornada de trabalho
            val data = LocalDate.parse("$dataString/19", DateTimeFormatter.ofPattern("dd/MM/yy"))

            val pontos = ArrayList<LocalTime>()
            val arrayDePontos = horasString.split(" ")

            for (pontoString in arrayDePontos) {
                val ponto = LocalTime.parse(pontoString)
                pontos.add(ponto)
            }

            val diaDeTrabalho = WorkDay(empregado, data, pontos)
            resultado.add(diaDeTrabalho)

            LOG.debug(diaDeTrabalho.toString())
        }
        LOG.info("Processo de transformação concluído. Foram transformados " + resultado.size + " elementos.")
        return resultado
    }
}