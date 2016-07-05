package br.ufg.inf.es.saep.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by paulo on 04/07/16.
 */
public class MainTest {

    public static void main(String args[]){
        DataBase.createCollections();
        DaoParecer daoParecer = new DaoParecer();
        Parecer parecer = new Parecer();
       daoParecer.removeParecer("ecd89405-e589-4d30-ac95-693d7b6718a4");
       //daoParecer.persisteParecer(parecer);



    }
}
