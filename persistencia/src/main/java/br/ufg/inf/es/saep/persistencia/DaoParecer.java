package br.ufg.inf.es.saep.persistencia;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.xml.crypto.Data;


public class DaoParecer implements ParecerRepository{

    private Gson gson = new Gson();

    public Parecer byId(String id) {
        return null;
    }

    public void persisteParecer(Parecer parecer) {

        DataBase.db.getCollection(DataBase.PARECER_COLLECTION).insertOne(new Document()
                .append("id", parecer.getId())
                .append("parecer",gson.toJson(parecer)));
}
    public void removeParecer(String id){

        DataBase.db.getCollection(DataBase.PARECER_COLLECTION).deleteMany(new Document("id", id));
    }

    public String persisteRadoc(Radoc radoc){
        DataBase.db.getCollection(DataBase.PARECER_COLLECTION).insertOne(new Document()
                .append("id", radoc.getId())
                .append("radoc",gson.toJson(radoc)));

        return null;
    }


    public void removeRadoc(String id){
        DataBase.db.getCollection(DataBase.PARECER_COLLECTION).deleteMany(new Document("id", id));
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
