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
            resolucaoCollection.insertOne(new Document("resolucao",
                    new Document().append(ID, resolucao.getId())
                            .append(OBJETO, gson.toJson(resolucao))));
            return resolucao.getId();

        }else{

            //Se existe returna null
            return null;
        }


    }


    public List<String> resolucoes() {
        List<String> listaResolucoes = new ArrayList<>();

        FindIterable<Document> iterable = resolucaoCollection.find(Filters.exists("resolucao.id"));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                Document resolucaoDoc = (Document) document.get("resolucao");
                String idResolucao = resolucaoDoc.getString(ID);
                listaResolucoes.add(idResolucao);
            }
        });
        return listaResolucoes;
    }

    public boolean remove(String identificador) {

        try{
            resolucaoCollection.deleteOne(new Document("resolucao.id", identificador));
            return true;
        }catch(MongoCommandException e){
            return false;
        }
    }



    public Resolucao byId(String identificador) {
        Resolucao resolucao;
        Document document = (Document) resolucaoCollection.find(new Document("resolucao.id", identificador)).first();
        Document resolucaoDoc = (Document) document.get("resolucao");
        String json = resolucaoDoc.getString(OBJETO);

        if(json == null){

            return  null;
        }else{
            resolucao = gson.fromJson(json, Resolucao.class);

            return resolucao;
        }

    }

    public Tipo tipoPeloCodigo(String codigo){
        Tipo tipo;
        Document document = (Document) resolucaoCollection.find(new Document("tipo.id", codigo)).first();
        Document tipoDoc = (Document) document.get("tipo");
        String json = tipoDoc.getString(OBJETO);

        tipo = gson.fromJson(json, Tipo.class);

        return tipo;

    }

    public List<Tipo> tiposPeloNome(String nome){
        List<Tipo> listaTipos = new ArrayList<>();


        FindIterable<Document> iterable = resolucaoCollection.find(new Document("tipo.nome", nome));
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                Document tipoDoc = (Document) document.get("tipo");
                String json = tipoDoc.getString(OBJETO);
                listaTipos.add(gson.fromJson(json, Tipo.class));
            }
        });
        return listaTipos;
    }

    public void persisteTipo(Tipo tipo){

        resolucaoCollection.insertOne(new Document("tipo",
                new Document().append(ID, tipo.getId())
                        .append("nome", tipo.getNome())
                        .append(OBJETO, gson.toJson(tipo))));
    }
    public void removeTipo(String codigo){
        resolucaoCollection.deleteOne(new Document("tipo.id", codigo));
    }


}
