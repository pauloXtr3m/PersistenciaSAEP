package br.ufg.inf.es.saep.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.google.gson.Gson;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoCommandException;
import org.bson.Document;

import java.util.List;

/**
 * Created by paulo on 30/06/16.
 */
public class DaoResolucao implements ResolucaoRepository {


    public String persiste(Resolucao resolucao) {
        Gson gson = new Gson();

        try{
            DataBase.db.getCollection(DataBase.RESOLUCAO_COLLECTION).insertOne(new Document("resolucao",
                    new Document().append("id", resolucao.getId())
                    .append("objeto", gson.toJson(resolucao))));
            return "true";

        }catch(MongoBulkWriteException e){
            return "false";
        }

    }


    public List<String> resolucoes() {
        return null;
    }

    public boolean remove(String identificador) {

        try{
            DataBase.db.getCollection(DataBase.RESOLUCAO_COLLECTION).deleteMany(new Document("resolucao.id", identificador));
            return true;
        }catch(MongoCommandException e){
            return false;
        }
    }


    public Resolucao byId(String identificador) {
        return null;
    }

    public Tipo tipoPeloCodigo(String codigo){
        return null;
    }
    public List<Tipo> tiposPeloNome(String nome){
        return null;
    }
    public void persisteTipo(Tipo tipo){

    }
    public void removeTipo(String codigo){

    }


}
