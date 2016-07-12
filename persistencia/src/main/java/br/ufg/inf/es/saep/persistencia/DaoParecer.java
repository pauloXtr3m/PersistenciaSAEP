package br.ufg.inf.es.saep.persistencia;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;

import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;




public class DaoParecer implements ParecerRepository{

    private Gson gson = new Gson();
    private final String ID = "id", OBJETO = "objeto";

    private MongoCollection parecerCollection = DataBase.db.getCollection(DataBase.PARECER_COLLECTION);
    private MongoCollection radocCollection = DataBase.db.getCollection(DataBase.RADOC_COLLECTION);

    //TESTADO
    public Parecer byId(String id) {

        Parecer parecer = new Parecer();

        //Busca Parece através do id fornecido
        Document document = (Document) parecerCollection.find(new Document("parecer.id", id)).first();
        try{
            //busca o documento "parecer" dentro do documento encontrado
            Document parecerDoc = (Document) document.get("parecer");

            //busca o campo do objeto dentro do documento "parecer"
            String json = parecerDoc.getString(OBJETO);

            //transforma o json encontrado em um objeto da classe Parecer
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
            radocCollection.insertOne(new Document("radoc",
                    new Document()
                            .append(ID, radoc.getId())
                            .append(OBJETO, gson.toJson(radoc))));
            return radoc.getId();

        }else {

            return null;
        }

    }

    //TESTADO

    public void removeRadoc(String id){
        radocCollection.deleteOne(new Document("radoc.id", id));
    }

    //TESTADO
    public Radoc radocById(String id) {

        Radoc radoc = null;
        Document document = (Document) radocCollection.find(new Document("radoc.id", id)).first();
        try{

            Document parecerDoc = (Document) document.get("radoc");
            String json = parecerDoc.getString(OBJETO);
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
        if(parecerObj == null){
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

    public void removeNota(String parecer, Avaliavel original){
            Avaliavel test;
            Parecer parecerObj = byId(parecer);

            //Se o Parecer não foi encontrado, lança uma exceção
            if(parecerObj.getId() == null){
                throw new IdentificadorDesconhecido("Parecer não existente");
            }

            Relato relato = null;

            List<Nota> listaNotas = parecerObj.getNotas();

            //procura uma nota com o Avaliavel recebido
            for(Nota nota: listaNotas){
                test = nota.getItemOriginal();

                //se a nota encontrada tem o mesmo Avaliavel recebido
                //exclui a nota
                if(test.hashCode() == original.hashCode()){
                    listaNotas.remove(nota);
                }
            }

            //Cria um novo objeto Parecer com a lista de notas atualizada
            Parecer novoParecer = new Parecer(parecerObj.getId(), parecerObj.getResolucao(),
                    parecerObj.getRadocs(), parecerObj.getPontuacoes(),
                    parecerObj.getFundamentacao(), listaNotas);

            //remove o parecer desatualizado
            removeParecer(parecer);

            //armazena o novo objeto parecer com a nova Nota
            persisteParecer(novoParecer);

    }



}
