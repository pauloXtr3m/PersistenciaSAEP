package br.ufg.inf.es.saep.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class DaoParecer implements ParecerRepository {


    private final String ID = "id";
    private Gson gson = new GsonBuilder().registerTypeAdapter(Avaliavel.class, new InterfaceAdapter<Avaliavel>())
            .create();
    private MongoCollection parecerCollection = DataBase.db.getCollection(DataBase.PARECER_COLLECTION);
    private MongoCollection radocCollection = DataBase.db.getCollection(DataBase.RADOC_COLLECTION);

    public Parecer byId(String id) {

        Parecer parecer = new Parecer();

        //Busca Parecer através do id fornecido
        Document document = (Document) parecerCollection.find(new Document(ID, id)).first();
        try {
            //transforma o documento encontrado em json
            String json = document.toJson();

            //atribui os valores do documento a um objeto parecer
            parecer = gson.fromJson(json, Parecer.class);

        } catch (NullPointerException e) {

            //Caso alguma referencia não seja encontrada no banco, retorna null
            return null;
        }

        // retorna o objeto de Parecer encontrado
        return parecer;
    }


    public void persisteParecer(Parecer parecer) {
        Parecer parecerTmp = byId(parecer.getId());
        if (parecerTmp == null) {
            Document doc = new Document().parse(gson.toJson(parecer));
            parecerCollection.insertOne(doc);
        } else {
            throw new IdentificadorExistente("Parecer com identificador já existente");
        }
    }

    public void removeParecer(String id) {

        parecerCollection.deleteOne(new Document(ID, id));
    }


    public String persisteRadoc(Radoc radoc) {


        try {
            //verifica se o radoc tem id
            if (radoc.getId() == null)
                throw new IdentificadorDesconhecido("Radoc com ID nulo");

            //se o objeto está nulo e a exceção NullPointerException for lançada,
            //lança IdentificadorDesconhecido
        } catch (NullPointerException e) {
            throw new IdentificadorDesconhecido("Radoc com ID nulo");
        }

        //procura por radoc com mesmo id
        Radoc radocTmp = radocById(radoc.getId());
        if (radocTmp == null) {
            Document doc = new Document().parse(gson.toJson(radoc));
            radocCollection.insertOne(doc);
            return radoc.getId();

        } else {

            return null;
        }

    }


    public void removeRadoc(String id) {
        boolean idEmUso = false;

        List<Parecer> listaPareceres = new ArrayList<>();
        FindIterable<Document> iterable = parecerCollection.find();

        //Para cada Parecer encontrado, transforma em objeto Parecer e adiciona em uma lista
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {

                String json = document.toJson();
                listaPareceres.add(gson.fromJson(json, Parecer.class));
            }
        });

        //Percorre a lista de pareceres e para cada Parecer, checa a lista de radocs associados
        for (Parecer parecer : listaPareceres) {

            List<String> idRadocs = parecer.getRadocs();

            for (String idRadoc : idRadocs) {
                // se o ID deste radoc é igual a fornecido, o radoc fornecido não pode ser excluído
                if (idRadoc.equals(id)) {
                    idEmUso = true;
                }
            }
        }

        if (idEmUso) {
            throw new ExisteParecerReferenciandoRadoc("Radoc recebido já existe em algum Parecer");
        } else {
            radocCollection.deleteOne(new Document(ID, id));
        }


    }


    public Radoc radocById(String id) {

        Radoc radoc = null;

        //Procura o radoc com o id fornecido
        Document document = (Document) radocCollection.find(new Document(ID, id)).first();
        try {

            String json = document.toJson();
            // Se encontrou algum radoc, o transforma em objeto
            radoc = gson.fromJson(json, Radoc.class);

        } catch (NullPointerException e) {
            //Se não encontrou, retorna null
            return null;
        }


        return radoc;


    }


    public void atualizaFundamentacao(String parecer, String fundamentacao) {
        //Procura por Parecer com o id fornecido
        Parecer parecerObj = byId(parecer);

        //Se o Parecer não foi encontrado, lança uma exceção
        if (parecerObj == null) {
            throw new IdentificadorDesconhecido("Parecer não existente");
        }

        //Atualiza a fundamentacao no parecer
        parecerCollection.findOneAndUpdate(new Document(ID, parecer), new Document("$set", new Document("fundamentacao", fundamentacao)));

    }


    public void adicionaNota(String parecer, Nota nota) {

        //Procura por Parecer com o id fornecido
        Parecer parecerObj = byId(parecer);

        //Se o Parecer não foi encontrado, lança uma exceção
        if (parecerObj == null) {
            throw new IdentificadorDesconhecido("Parecer não existente");
        }

        Document doc = new Document().parse(gson.toJson(nota));
        parecerCollection.findOneAndUpdate(new Document(ID, parecer), new Document("$push", new Document("notas", doc)));

    }

    public void removeNota(String parecer, Avaliavel original) {
        Avaliavel test;
        Parecer parecerObj = byId(parecer);

        //Se o Parecer foi encontrado, tenta procurar nota
        if (parecerObj != null) {
            List<Nota> listaNotas = parecerObj.getNotas();
            Nota notaEncontrada = null;

            //procura uma nota com o Avaliavel recebido
            for (Nota nota : listaNotas) {
                test = nota.getItemOriginal();
                String jsonTest = gson.toJson(test);
                String jsonOriginal = gson.toJson(original);
                //se a nota encontrada tem o mesmo Avaliavel recebido
                //exclui a nota
                if (jsonOriginal.equals(jsonTest)) {
                    notaEncontrada = nota;
                }
            }
            // Se a nota encontrada foi encontrada, tenta excluí-la
            if (notaEncontrada != null) {
                Document doc = new Document().parse(gson.toJson(notaEncontrada));
                parecerCollection.findOneAndUpdate(new Document("id", parecer), new Document("$pull", new Document("notas", doc)));
            }
        }


    }


}
