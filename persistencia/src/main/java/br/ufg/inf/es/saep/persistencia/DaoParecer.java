package br.ufg.inf.es.saep.persistencia;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


public class DaoParecer implements ParecerRepository{

    MongoClient mongoClient =  new MongoClient();
    MongoDatabase db = mongoClient.getDatabase("saep");


    public Parecer byId(String id) {
        return null;
    }

    public void persisteParecer(Parecer parecer) {

    }
    public void removeParecer(String id){

    }




    public String persisteRadoc(Radoc radoc){
        return null;
    }


    public void removeRadoc(String id){

    }
    public Radoc radocById(String id) {

        return null;
    }



    public void atualizaFundamentacao(String parecer, String fundamentacao){

    }

    public void adicionaNota(String parecer, Nota nota){

    }

    public void removeNota(Avaliavel original){

    }



}
