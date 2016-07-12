package br.ufg.inf.es.saep.persistencia;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;




public class DaoParecer implements ParecerRepository{


    private Gson gson = new GsonBuilder().registerTypeAdapter(Avaliavel.class, new InterfaceAdapter<Avaliavel>())
            .create();

    private final String ID = "id", OBJETO = "objeto";

    private MongoCollection parecerCollection = DataBase.db.getCollection(DataBase.PARECER_COLLECTION);
    private MongoCollection radocCollection = DataBase.db.getCollection(DataBase.RADOC_COLLECTION);

    //TESTADO
    public Parecer byId(String id) {

        Parecer parecer = new Parecer();

        //Busca Parece através do id fornecido
        Document document = (Document) parecerCollection.find(new Document(ID, id)).first();
        try{
            //transforma o documento encontrado em json
            String json = document.toJson();

            //atribui os valores do documento a um objeto parecer
            parecer = gson.fromJson(json, Parecer.class);

        }catch(NullPointerException e ){

            //Caso alguma referencia não seja encontrada no banco, retorna null
            return null;
        }

        // retorna o objeto de Parecer encontrado
        return parecer;
    }

    //TESTADO
    public void persisteParecer(Parecer parecer) {

        Document doc = new Document().parse(gson.toJson(parecer));
        parecerCollection.insertOne(doc);
    }
    //TESTADO
    public void removeParecer(String id){

        parecerCollection.deleteOne(new Document(ID, id));
    }

    //TESTADO

    public String persisteRadoc(Radoc radoc){


        try{
            //verifica se o radoc tem id
            if(radoc.getId() == null)
                throw new IdentificadorDesconhecido("Radoc com ID nulo");

            //se o objeto está nulo e a exceção NullPointerException for lançada,
            //lança IdentificadorDesconhecido
        }catch(NullPointerException e){
            throw new IdentificadorDesconhecido("Radoc com ID nulo");
        }

        //procura por radoc com mesmo id
        Radoc radocTmp = radocById(radoc.getId());
        if(radocTmp == null) {
            Document doc = new Document().parse(gson.toJson(radoc));
            radocCollection.insertOne(doc);
            return radoc.getId();

        }else {

            return null;
        }

    }

    //TESTADO
    public void removeRadoc(String id){
        radocCollection.deleteOne(new Document(ID, id));
    }

    //TESTADO
    public Radoc radocById(String id) {

        Radoc radoc = null;
        Document document = (Document) radocCollection.find(new Document(ID, id)).first();
        try{

            String json = document.toJson();
            radoc = gson.fromJson(json, Radoc.class);

        }catch(NullPointerException e ){

            return null;
        }


        return radoc;


    }


    //TESTADO
    public void atualizaFundamentacao(String parecer, String fundamentacao){
        //Procura por Parecer com o id fornecido
        Parecer parecerObj = byId(parecer);

        //Se o Parecer não foi encontrado, lança uma exceção
        if(parecerObj == null){
            throw new IdentificadorDesconhecido("Parecer não existente");
        }

        //Atualiza a fundamentacao no parecer
        parecerCollection.findOneAndUpdate(new Document(ID, parecer), new Document("$set", new Document("fundamentacao", fundamentacao)));

    }


    //TESTADO
    public void adicionaNota(String parecer, Nota nota){

        //Procura por Parecer com o id fornecido
       Parecer parecerObj = byId(parecer);

        //Se o Parecer não foi encontrado, lança uma exceção
       if(parecerObj == null){
            throw new IdentificadorDesconhecido("Parecer não existente");
        }

//        List<Nota> listaNotas = parecerObj.getNotas();
//
//                        //Caso o parecer ainda nao tenha nenhuma nota, cria uma nova lista de notas
//                                if(listaNotas == null){
//                        listaNotas = new ArrayList<Nota>();
//                   }
//
//                       //Adiciona uma nova Nota na lista lida
//                                listaNotas.add(nota);
//
//                        //Cria um novo objeto Parecer com a lista de notas atualizada
//          Parecer novoParecer = new Parecer(parecerObj.getId(),
//                  parecerObj.getResolucao(),
//                  parecerObj.getRadocs(),
//                  parecerObj.getPontuacoes(), parecerObj.getFundamentacao(), listaNotas);
//
//               //remove o parecer desatualizado
//          removeParecer(parecer);
//
//          //armazena o novo objeto parecer com a nova Nota
//          persisteParecer(novoParecer);



        Document doc = new Document().parse(gson.toJson(nota));
        parecerCollection.findOneAndUpdate(new Document(ID, parecer), new Document("$push", new Document("notas", doc)));

    }

    //TESTADO
    public void removeNota(String parecer, Avaliavel original){
            Avaliavel test;
            Parecer parecerObj = byId(parecer);

            //Se o Parecer não foi encontrado, lança uma exceção
            if(parecerObj.getId() == null){
                throw new IdentificadorDesconhecido("Parecer não existente");
            }

            List<Nota> listaNotas = parecerObj.getNotas();
            Nota notaEncontrada = null;

            //procura uma nota com o Avaliavel recebido
            for(Nota nota: listaNotas){
                test = nota.getItemOriginal();
                String jsonTest = gson.toJson(test);
                String jsonOriginal = gson.toJson(original);
                //se a nota encontrada tem o mesmo Avaliavel recebido
                //exclui a nota
                if(jsonOriginal.equals(jsonTest)){
                    notaEncontrada = nota;
                }
            }

            if(notaEncontrada != null){
                Document doc = new Document().parse(gson.toJson(notaEncontrada));
                parecerCollection.findOneAndUpdate(new Document("id", parecer), new Document("$pull", new Document("notas", doc)));
            }
//
//            //Cria um novo objeto Parecer com a lista de notas atualizada
//            Parecer novoParecer = new Parecer(parecerObj.getId(), parecerObj.getResolucao(),
//                    parecerObj.getRadocs(), parecerObj.getPontuacoes(),
//                    parecerObj.getFundamentacao(), listaNotas);
//
//            //remove o parecer desatualizado
//            removeParecer(parecer);
//
//            //armazena o novo objeto parecer com a nova Nota
//            persisteParecer(novoParecer);

    }



}
