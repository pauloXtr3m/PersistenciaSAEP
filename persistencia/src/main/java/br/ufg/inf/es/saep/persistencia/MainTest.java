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
        daoParecer.removeParecer("b6c3a1a8-5ec1-4359-ba92-6b07607990e9");
         //daoParecer.persisteParecer(parecer);



    }
}
