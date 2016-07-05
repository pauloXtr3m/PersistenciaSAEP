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
       daoParecer.removeRadoc("a7ff5048-8ad5-4a0e-a6f6-1f15a28da431");
        //daoParecer.persisteParecer(parecer);



    }
}
