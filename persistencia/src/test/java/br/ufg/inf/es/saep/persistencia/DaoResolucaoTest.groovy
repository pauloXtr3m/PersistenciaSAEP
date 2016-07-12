package br.ufg.inf.es.saep.persistencia

import br.ufg.inf.es.saep.sandbox.dominio.Atributo
import br.ufg.inf.es.saep.sandbox.dominio.Regra
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao
import br.ufg.inf.es.saep.sandbox.dominio.Tipo
import junit.framework.TestCase

/**
 * Created by paulo on 12/07/16.
 */
class DaoResolucaoTest extends TestCase{
    String str = "fsf";
    List<String> listaString = new ArrayList<>();
    Regra regra = new Regra("fsda", 1, "fasdf", 80, 40, "sdfwe", "fasdf", "sdfwe", "sdfwe", 1,listaString);
    List<Regra> listaRegra = new ArrayList<>();
    DaoResolucao daoResolucao = new DaoResolucao();
    Date date = new Date();

    Resolucao res;

    void testPersiste() {
        listaRegra.add(regra);
        date.getTime();
        res = new Resolucao("1256", "haha", "descricao", date, listaRegra);
        DaoResolucao daoResolucao = new DaoResolucao();
        daoResolucao.persiste(res);
        Resolucao res1 = daoResolucao.byId("1256");
        assertEquals(res.getId(), res1.getId());
    }

    void testResolucoes() {
        List<String> listaRes = daoResolucao.resolucoes();
        assertEquals(listaRes.get(0), "1256");
    }

    void testRemove() {
        daoResolucao.remove("1256");

    }

    void testById() {
        Resolucao res1 = daoResolucao.byId("1256");
        assertEquals("1256", res1.getId());
    }


    Atributo atr = new Atributo("haha", "fhsud", 1);
    Set<Atributo> atributos = new HashSet<>();
    Tipo tipo = new Tipo("codigo", "nome", "fhfapoiewj", atributos);

    void testTipoPeloCodigo() {
        atributos.add(atr);
        daoResolucao.persisteTipo(tipo);
        Tipo tipo1 = daoResolucao.tipoPeloCodigo(tipo.getId());

        assertEquals(tipo.getId(), tipo1.getId());
    }

    void testTiposPeloNome() {
        List<Tipo> tipo2 = daoResolucao.tiposPeloNome(tipo.getNome());
        assertEquals(tipo.getId(), tipo2.get(0));
    }

    void testPersisteTipo() {
        Tipo tipo1 = daoResolucao.tipoPeloCodigo(tipo.getId());

        assertEquals(tipo.getId(), tipo1.getId());
    }

    void testRemoveTipo() {
        daoResolucao.removeTipo(tipo.getId());
        Tipo tipo1 = daoResolucao.tipoPeloCodigo(tipo.getId());
        assertEquals(null, tipo1 );
    }
}
