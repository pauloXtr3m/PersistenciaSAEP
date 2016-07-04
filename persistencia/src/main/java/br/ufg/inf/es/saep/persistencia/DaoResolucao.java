package br.ufg.inf.es.saep.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;

import java.util.List;

/**
 * Created by paulo on 30/06/16.
 */
public class DaoResolucao implements ResolucaoRepository {


    public void persiste(Tipo tipo) {

    }

    public String persiste(Resolucao resolucao) {
        return null;
    }


    public List<String> resolucoes() {
        return null;
    }

    public boolean remove(String identificador) {
        return false;
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
