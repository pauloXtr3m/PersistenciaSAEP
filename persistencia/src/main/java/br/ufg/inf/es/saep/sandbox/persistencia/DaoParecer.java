package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Alteracao;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


public class DaoParecer implements ParecerRepository {

    MongoClient mongoClient =  new MongoClient();
    MongoDatabase db = mongoClient.getDatabase("saep");


    public void persisteParecer(Parecer parecer) {

    }


    public void remove(String id) {

    }


    public Parecer byId(String id) {
        return null;
    }


    public void adicionaAlteracao(String parecer, Alteracao alteracao) {

    }

    public void atualizaFundamentacao(String fundamentacao) {

    }

}
