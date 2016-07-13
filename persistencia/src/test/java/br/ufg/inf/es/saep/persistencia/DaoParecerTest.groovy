package br.ufg.inf.es.saep.persistencia

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel
import br.ufg.inf.es.saep.sandbox.dominio.Nota
import br.ufg.inf.es.saep.sandbox.dominio.Parecer
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao
import br.ufg.inf.es.saep.sandbox.dominio.Radoc
import br.ufg.inf.es.saep.sandbox.dominio.Relato
import br.ufg.inf.es.saep.sandbox.dominio.Valor
import junit.framework.TestCase

/**
 * Created by paulo on 13/07/16.
 */
class DaoParecerTest extends TestCase {

    DaoParecer daoParecer = new DaoParecer();
    Parecer parecer = new Parecer();
    Map<String, Valor> map = new HashMap<>();
    Valor valor = new Valor("43");

    Relato relato = new Relato("relato",map);
    List<Relato> relatos = new ArrayList<>();
    Radoc radoc = new Radoc("identificador", 2016 , relatos);

    void testById() {
        daoParecer.persisteParecer(parecer);
        Parecer parecer1 = daoParecer.byId(parecer.getId());
        assertEquals(parecer.getId(), parecer1.getId());
    }

    void testPersisteParecer() {
        daoParecer.persisteParecer(parecer);
        Parecer parecer1 = daoParecer.byId(parecer.getId());
        assertEquals(parecer.getId(), parecer1.getId());
    }

    void testRemoveParecer() {
        daoParecer.removeParecer(parecer.getId());
        Parecer parecer1 = daoParecer.byId(parecer.getId());
        assertEquals(null, parecer1.getId());
    }

    void testPersisteRadoc() {

        map.put("testeMap", valor);
        System.out.println(daoParecer.persisteRadoc(radoc));
        Radoc radoc1 = daoParecer.radocById(radoc.getId());
        assertEquals(radoc.getId(), radoc1.getId());

    }

    void testRemoveRadoc() {
        daoParecer.removeRadoc(radoc.getId());
    }

    void testRadocById() {
        map.put("testeMap", valor);
        System.out.println(daoParecer.persisteRadoc(radoc));
        Radoc radoc1 = daoParecer.radocById(radoc.getId());
        assertEquals(radoc.getId(), radoc1.getId());
    }

    void testAtualizaFundamentacao() {
        daoParecer.atualizaFundamentacao(parecer.getId(), "fundamentacao");
        Parecer parecer1 = daoParecer.byId(parecer.getId());

        assertEquals("fundamentacao", parecer.getFundamentacao());
    }

    void testAdicionaNota() {

        boolean encontrouNota = false;

        Valor valor = new Valor("fasdfas");
        Pontuacao pontuacao = new Pontuacao("pontuacao", valor);

        Avaliavel x = pontuacao;
        Avaliavel y = pontuacao;

        Nota nota = new Nota(x, y, "Nova Justificativa");
        daoParecer.adicionaNota(parecer.getId(),nota);

        Parecer parecer1 = parecer.getId();
        List<Nota> listaNotas = parecer1.getNotas();

        for(Nota notaTmp: listaNotas){
            Avaliavel avaliavelTmp = notaTmp.getItemOriginal();
            Avaliavel original = pontuacao;
            String jsonOriginal = gson.toJson(original);

            if(jsonOriginal.equals(gson.toJson(avaliavelTmp))){
                encontrouNota = true;
            }
        }

        assertEquals(true, encontrouNota);

    }

    void testRemoveNota() {
        boolean encontrouNota = false;

        Valor valor = new Valor("fasdfas");
        Pontuacao pontuacao = new Pontuacao("pontuacao", valor);

        Avaliavel x = pontuacao;
        Avaliavel y = pontuacao;

        Nota nota = new Nota(x, y, "Nova Justificativa");
        daoParecer.adicionaNota(parecer.getId(),nota);

        Parecer parecer1 = parecer.getId();
        List<Nota> listaNotas = parecer1.getNotas();

        for(Nota notaTmp: listaNotas){
            Avaliavel avaliavelTmp = notaTmp.getItemOriginal();
            Avaliavel original = pontuacao;
            String jsonOriginal = gson.toJson(original);

            if(jsonOriginal.equals(gson.toJson(avaliavelTmp))){
                encontrouNota = true;
            }
        }

        assertEquals(false, encontrouNota);
    }
}
