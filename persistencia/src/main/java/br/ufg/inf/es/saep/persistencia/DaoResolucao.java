package br.ufg.inf.es.saep.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoCommandException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.print.Doc;
import java.nio.file.attribute.AclEntry;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.sun.deploy.cache.Cache.exists;

/**
 * Created by paulo on 30/06/16.
 */
public class DaoResolucao implements ResolucaoRepository {

    private Gson gson = new Gson();
    private final String ID = "id", OBJETO = "objeto";

    MongoCollection resolucaoCollection = DataBase.db.getCollection(DataBase.RESOLUCAO_COLLECTION);
    MongoCollection tipoCollection = DataBase.db.getCollection(DataBase.TIPO_COLLECTION);

//TESTADO
    public String persiste(Resolucao resolucao) {
        Resolucao resolucaoTmp = null;

        try{//Verifica se a Resolucao recebida tem id

            if(resolucao.getId() != null){
                //Verifica se já existe uma Resolucao com o mesmo id
                resolucaoTmp = byId( resolucao.getId());
            }

            //Se a Resolucao recebida nao tem id,
            // joga uma exceção informando que a Resolucao está sem id
        }catch (NullPointerException e ){
            throw new CampoExigidoNaoFornecido("Resolução sem id");
        }

        // Se não existe Resolucao com o mesmo id, persiste Resolucao
        if(resolucaoTmp == null){

            Document doc = new Document().parse(gson.toJson(resolucao));

            resolucaoCollection.insertOne(doc);
            return resolucao.getId();

        }else{

            throw new IdentificadorExistente("Uma resolução com este identificador já está registrada");
        }


    }

//TESTADO
    public List<String> resolucoes() {
        List<String> listaResolucoes = new ArrayList<>();

        FindIterable<Document> iterable = resolucaoCollection.find(Filters.exists("id"));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {

                String idResolucao = document.getString(ID);
                listaResolucoes.add(idResolucao);
            }
        });
        return listaResolucoes;
    }
//TESTADO
    public boolean remove(String identificador) {
        boolean result;
        try{
            resolucaoCollection.deleteOne(new Document(ID, identificador));
            return true;
        }catch(MongoCommandException e){
            return false;
        }
    }


//TESTADO
    public Resolucao byId(String identificador) {
        Resolucao resolucao;
        Document document = (Document) resolucaoCollection.find(new Document(ID, identificador)).first();

        try{
            //Se não foi encontrada nenhuma resolução
            // Será lançada NullPointerException
            String json = document.toJson();
            resolucao = gson.fromJson(json, Resolucao.class);
            return resolucao;
        }catch(NullPointerException e ){
            // Se Resolucao não foi encontrada, retorna null
            return  null;
        }

    }
//TESTADO
    public Tipo tipoPeloCodigo(String codigo){
        Tipo tipo;
        Document document = (Document) tipoCollection.find(new Document(ID, codigo)).first();
        try{
            String json = document.toJson();

            tipo = gson.fromJson(json, Tipo.class);

            return tipo;
        }catch(NullPointerException e){
            return null;
        }


    }
//TESTADO
    public List<Tipo> tiposPeloNome(String nome){
        List<Tipo> listaTipos = new ArrayList<>();


        FindIterable<Document> iterable = tipoCollection.find(new Document("nome", nome));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {

                String json = document.toJson();
                listaTipos.add(gson.fromJson(json, Tipo.class));
            }
        });
        return listaTipos;
    }
//TESTADO
    public void persisteTipo(Tipo tipo) {
        Document doc = new Document().parse(gson.toJson(tipo));
        tipoCollection.insertOne(doc);
    }
//TESTADO
    public void removeTipo(String codigo){
        tipoCollection.deleteOne(new Document(ID, codigo));
    }


}
