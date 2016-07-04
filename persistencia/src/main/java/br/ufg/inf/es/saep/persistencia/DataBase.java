package br.ufg.inf.es.saep.persistencia;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by paulo on 04/07/16.
 */
public class DataBase {
    public static MongoClient mongoClient =  new MongoClient();
    public static MongoDatabase db = mongoClient.getDatabase("saep");
    public static String PARECER_COLLECTION = "parecerRepository";
    public static String RESOLUCAO_COLLECTION = "resolucaoRepository";

    public static void createCollections(){
        try{
            db.createCollection(DataBase.PARECER_COLLECTION);
            db.createCollection(DataBase.RESOLUCAO_COLLECTION);
        }catch(MongoCommandException e ){
            System.out.println("Collections já criadas");
        }

    }


}
