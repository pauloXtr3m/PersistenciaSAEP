package br.ufg.inf.es.saep.persistencia;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoDatabase;

public class DataBase {
    public static MongoClient mongoClient =  new MongoClient();
    public static MongoDatabase db = mongoClient.getDatabase("saep");
    public static String PARECER_COLLECTION = "parecer_collection";
    public static String RADOC_COLLECTION = "radoc_collection";
    public static String RESOLUCAO_COLLECTION = "resolucao_collection";
    public static String TIPO_COLLECTION = "tipo_collection";

    public static void createCollections(){
        try{
            db.createCollection(DataBase.PARECER_COLLECTION);
            db.createCollection(DataBase.RADOC_COLLECTION);
            db.createCollection(DataBase.RESOLUCAO_COLLECTION);
            db.createCollection(DataBase.TIPO_COLLECTION);

        }catch(MongoCommandException e ){
            System.out.println("Collections já criadas");
        }

    }


}
