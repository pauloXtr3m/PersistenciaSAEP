package br.ufg.inf.es.saep.persistencia;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;


public class DaoParecer implements ParecerRepository{

    private Gson gson = new Gson();

    public Parecer byId(String id) {
        Parecer parecer;
        FindIterable<Document> iterable = DataBase.db.getCollection(DataBase.PARECER_COLLECTION)
                .find(new Document("parecer.id", id));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                if(document.containsKey("parecer.objeto")){

                    //parecer =  gson.fromJson(document.get("parecer.objeto").toString(), Parecer.class);
                }

            }
        });
        return null;
    }

    public void persisteParecer(Parecer parecer) {

        DataBase.db.getCollection(DataBase.PARECER_COLLECTION).insertOne(new Document("parecer",
                new Document().append("id", parecer.getId())
                .append("objeto",gson.toJson(parecer))));
}
    public void removeParecer(String id){

        DataBase.db.getCollection(DataBase.PARECER_COLLECTION).deleteMany(new Document("parecer.id", id));
    }

    public String persisteRadoc(Radoc radoc){
        DataBase.db.getCollection(DataBase.PARECER_COLLECTION).insertOne(new Document("radoc",
                new Document().append("id", radoc.getId())
                        .append("objeto",gson.toJson(radoc))));

        return null;
    }


    public void removeRadoc(String id){
        DataBase.db.getCollection(DataBase.PARECER_COLLECTION).
                deleteMany(new Document("radoc.id", id));
    }
    public Radoc radocById(String id) {

        return null;
    }



    public void atualizaFundamentacao(String parecer, String fundamentacao){

    }

    public void adicionaNota(String parecer, Nota nota){
        Gson gson = new Gson();
        DataBase.db.getCollection(DataBase.PARECER_COLLECTION).
                updateOne(new Document("parecer.id", parecer), new Document().append("parecer.nota", gson.toJson(nota)));
    }

    public void removeNota(Avaliavel original){

    }



}
