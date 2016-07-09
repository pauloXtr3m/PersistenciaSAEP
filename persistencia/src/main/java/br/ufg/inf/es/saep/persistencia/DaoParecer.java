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
    MongoCollection radocCollection = DataBase.db.getCollection(DataBase.RADOC_COLLECTION);

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

    //TESTADO

    public String persisteRadoc(Radoc radoc){

        //verifica se o radoc tem id
        if(radoc.getId() == null)
            throw new IdentificadorDesconhecido("Radoc com ID nulo");

        //procura por radoc com mesmo id

        try{
            Radoc radocTmp = radocById(radoc.getId());

            return null;
        }catch(NullPointerException e ){

            radocCollection.insertOne(new Document("radoc",
                    new Document()
                            .append(ID, radoc.getId())
                            .append(OBJETO, gson.toJson(radoc))));
            return radoc.getId();
        }

    }

    //TESTADO

    public void removeRadoc(String id){
        radocCollection.deleteOne(new Document("radoc.id", id));
    }

    //TESTADO
    public Radoc radocById(String id) {

        Radoc radoc;

        Document document = (Document) radocCollection.find(new Document("radoc.id", id)).first();
        Document radocDoc = (Document)document.get("radoc");
        String json = radocDoc.getString(OBJETO);

        radoc = gson.fromJson(json, Radoc.class);

        return radoc;


    }


    //TESTADO

    public void atualizaFundamentacao(String parecer, String fundamentacao){
        //Procura por Parecer com o id fornecido
        Parecer parecerObj = byId(parecer);

        //Se o Parecer não foi encontrado, lança uma exceção
        if(parecerObj.getId() == null){
            throw new IdentificadorDesconhecido("Parecer não existente");
        }

        //Cria um novo objeto Parecer com a nova fundamentacao
        Parecer novoParecer = new Parecer(parecerObj.getId(), parecerObj.getResolucao(),
                parecerObj.getRadocs(), parecerObj.getPontuacoes(),
                fundamentacao, parecerObj.getNotas());

        //remove o Parecer desatualizado
        removeParecer(parecer);

        //armazena o novo objeto parecer com a fundamentacao atualizada
        persisteParecer(novoParecer);
    }


    //TESTADO

    public void adicionaNota(String parecer, Nota nota){


        //Procura por Parecer com o id fornecido
        Parecer parecerObj = byId(parecer);

        //Se o Parecer não foi encontrado, lança uma exceção
        if(parecerObj.getId() == null){
            throw new IdentificadorDesconhecido("Parecer não existente");
        }

        //Caso o parecer tenha sido encontrado, lê a sua lista de notas
        List<Nota> listaNotas = parecerObj.getNotas();

        //Caso o parecer ainda nao tenha nenhuma nota, cria uma nova lista de notas
        if(listaNotas == null){
            listaNotas = new ArrayList<Nota>();
        }

        //Adiciona uma nova Nota na lista lida
        listaNotas.add(nota);

        //Cria um novo objeto Parecer com a lista de notas atualizada
        Parecer novoParecer = new Parecer(parecerObj.getId(), parecerObj.getResolucao(),
                parecerObj.getRadocs(), parecerObj.getPontuacoes(),
                parecerObj.getFundamentacao(), listaNotas);

        //remove o parecer desatualizado
        removeParecer(parecer);

        //armazena o novo objeto parecer com a nova Nota
        persisteParecer(novoParecer);


    }

    public void removeNota(Avaliavel original){

//        original.get();
    }



}
