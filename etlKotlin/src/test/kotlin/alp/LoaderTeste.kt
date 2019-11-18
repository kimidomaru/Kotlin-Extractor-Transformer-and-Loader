package alp

import alp.domain.Loader
import domain.Transformer
import Extractor
import org.junit.Test

class LoaderTeste {

    @Test
    fun testeLoader(){
        val extract = Extractor()
        val entradas = extract.extrair()
        val lista = Transformer().transformar(entradas)
        Loader().load(lista)
    }
}