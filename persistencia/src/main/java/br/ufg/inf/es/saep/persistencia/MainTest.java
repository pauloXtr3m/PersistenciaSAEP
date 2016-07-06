package br.ufg.inf.es.saep.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;

import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * Created by paulo on 04/07/16.
 */
public class MainTest {

    public static void main(String args[]){
        DataBase.createCollections();
        DaoParecer daoParecer = new DaoParecer();
        Parecer parecer = new Parecer();
//        daoParecer.persisteParecer(parecer);
//
//        try{
//            sleep(10000);
//        }catch(InterruptedException e){
//
//        }
//        daoParecer.removeParecer("31a7f033-b197-4579-ab13-f8f6d20b424c");

        parecer = daoParecer.byId("9c4f9120-218b-48d3-9afa-82c8c19c434a");

        System.out.println(parecer.getId());




    }
}
