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
    private final String ID = "id";

    MongoCollection resolucaoCollection = DataBase.db.getCollection(DataBase.RESOLUCAO_COLLECTION);
    MongoCollection tipoCollection = DataBase.db.getCollection(DataBase.TIPO_COLLECTION);


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

    public List<String> resolucoes() {
        List<String> listaResolucoes = new ArrayList<>();

        //Recebe a lista de resolucoes
        FindIterable<Document> iterable = resolucaoCollection.find(Filters.exists("id"));
        //Para cada Resolucao da lista, transforma em objeto e adiciona em uma nova lista a ser retornada
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {

                String idResolucao = document.getString(ID);
                listaResolucoes.add(idResolucao);
            }
        });
        return listaResolucoes;
    }

    public boolean remove(String identificador) {
        boolean result;

        resolucaoCollection.deleteOne(new Document(ID, identificador));

        //Verifica se a Resolucao com o identificador recebido foi removida
        Resolucao res = byId(ID);

        if(res == null){
            return true;
        }else{
            return false;
        }

    }


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

    public List<Tipo> tiposPeloNome(String nome){
        List<Tipo> listaTipos = new ArrayList<>();

    //Adiciona todos os tipos encontrados para um array
        FindIterable<Document> iterable = tipoCollection.find(new Document("nome", nome));

    //Para cada tipo encontrado, transforma em objeto Tipo
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {

                String json = document.toJson();
                listaTipos.add(gson.fromJson(json, Tipo.class));
            }
        });
        return listaTipos;
    }

    public void persisteTipo(Tipo tipo) {

        Tipo tipoTmp = tipoPeloCodigo(tipo.getId());

        if(tipoTmp != null){Document doc = new Document().parse(gson.toJson(tipo));
            tipoCollection.insertOne(doc);
        }else {
            throw new IdentificadorExistente("O tipo já existe");
        }


    }

    public void removeTipo(String codigo){
        boolean tipoUsado = false;

        List<String> listaResolucoes = resolucoes();

        for(String idRes: listaResolucoes){
            Resolucao res = byId(idRes);
            List<Regra> regras = res.getRegras();

            for(Regra regra: regras){
                if(codigo.equals(regra.getTipoRelato())){
                    tipoUsado = true;
                }
            }
        }

        if(tipoUsado){
            throw new ResolucaoUsaTipoException("Este tipo está sendo usado em uma Resolução");
        }else{
            tipoCollection.deleteOne(new Document(ID, codigo));
        }

    }


}
