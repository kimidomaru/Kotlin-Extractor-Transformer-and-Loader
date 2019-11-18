package alp

import org.junit.Test
import domain.Transformer
import Extractor
import org.junit.Assert

class TransformerTeste{

    @Test
    fun testeTrans (){
        val extract = Extractor()
        val entradas = extract.extrair()
        val lista = Transformer().transformar(entradas)

        Assert.assertNotNull(lista)
        Assert.assertEquals(72, lista.size)

        for(diaTrabalho in lista){
            Assert.assertNotNull(diaTrabalho.employee);
            Assert.assertNotNull(diaTrabalho.date);
            Assert.assertNotNull(diaTrabalho.punches);
            Assert.assertTrue(diaTrabalho.punches.size > 0);
        }
    }
}
