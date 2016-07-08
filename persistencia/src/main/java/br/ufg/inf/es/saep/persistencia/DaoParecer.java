package br.ufg.inf.es.saep.persistencia;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.util.JSON.parse;


public class DaoParecer implements ParecerRepository{

    private Gson gson = new Gson();
    private final String ID = "id", OBJETO = "objeto";

    MongoCollection parecerCollection = DataBase.db.getCollection(DataBase.PARECER_COLLECTION);
    MongoCollection notaCollection = DataBase.db.getCollection(DataBase.NOTA_COLLECTION);


    //TESTADO
    public Parecer byId(String id) {

        Parecer parecer;
        Document document = (Document) parecerCollection.find(new Document("parecer.id", id)).first();
        Document parecerDoc = (Document) document.get("parecer");
        String json = parecerDoc.getString(OBJETO);
        parecer = gson.fromJson(json, Parecer.class);

        return parecer;
    }

    //TESTADO
    public void persisteParecer(Parecer parecer) {

        parecerCollection.insertOne(new Document("parecer",
                new Document().append(ID, parecer.getId())
                        .append(OBJETO, gson.toJson(parecer))));
    }
    //TESTADO
    public void removeParecer(String id){

        parecerCollection.deleteOne(new Document("parecer.id", id));
    }


    public String persisteRadoc(Radoc radoc){

        parecerCollection.insertOne(new Document("radoc",
                new Document()
                        .append(ID, radoc.getId())
                        .append(OBJETO, gson.toJson(radoc))));

        return null;
    }


    public void removeRadoc(String id){
        parecerCollection.deleteOne(new Document("radoc.id", id));
    }
    public Radoc radocById(String id) {

        Radoc radoc;
        Document document = (Document) parecerCollection.find(new Document("radoc.id", id)).first();
        Document radocDoc = (Document)document.get("radoc");
        String json = radocDoc.getString(OBJETO);

        radoc = gson.fromJson(json, Radoc.class);

        return radoc;

    }



    public void atualizaFundamentacao(String parecer, String fundamentacao){

    }

    public void adicionaNota(String parecer, Nota nota){
        Parecer parecerObj = byId(parecer);
        List<Nota> listaNotas = parecerObj.getNotas();
        listaNotas.add(nota);

    }

    public void removeNota(Avaliavel original){

//        original.get();
    }



}
