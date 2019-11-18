package alp

import org.junit.Test
import Extractor
import org.junit.Assert

class ExtractorTeste {
    @Test
    fun testeExtract () {
        val extract = Extractor()
        val entradas = extract.extrair()

        Assert.assertNotNull(entradas)
        Assert.assertEquals(72, entradas.size)

        for(entrada in entradas){
            Assert.assertNotNull(entrada.employee);
            Assert.assertNotNull(entrada.date);
            Assert.assertNotNull(entrada.date)
        }
    }
}