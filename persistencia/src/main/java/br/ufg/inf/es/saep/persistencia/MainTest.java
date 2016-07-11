package br.ufg.inf.es.saep.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;

import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by paulo on 04/07/16.
 */
public class MainTest {

    public static void main(String args[]){
        DataBase.createCollections();
        DaoParecer daoParecer = new DaoParecer();
        Parecer parecer = new Parecer();

//-------------TESTE DE PERSISTIR E REMOVER PARECER-------------
//        daoParecer.persisteParecer(parecer);
//
//        try{
//            sleep(10000);
//        }catch(InterruptedException e){
//
//        }
//        daoParecer.removeParecer("31a7f033-b197-4579-ab13-f8f6d20b424c");




//-------------TESTE DE ADICIONAR NOTA-------------

        Valor valor = new Valor(53453);
        Avaliavel x = new Avaliavel() {
            @Override
            public Valor get(String atributo) {
                return valor;
            }
        };
        Avaliavel y = new Avaliavel() {
            @Override
            public Valor get(String atributo) {
                return valor;
            }
        };


        Nota nota = new Nota(x, y, "Nova Justificativa");
        daoParecer.adicionaNota("4c4751d7-d430-45bd-8921-81426a1bdf51",nota);


        try{
            sleep(10000);
        }catch(InterruptedException e){

        }
//        daoParecer.removeParecer("31a7f033-b197-4579-ab13-f8f6d20b424c");

        System.out.println(nota.getItemOriginal());
        daoParecer.removeNota("4c4751d7-d430-45bd-8921-81426a1bdf51", nota.getItemOriginal());

//-------------TESTE DE ADICIONAR FUNDAMENTACAO-------------
//        daoParecer.atualizaFundamentacao("6f214ee1-6d1e-4964-bb17-d4b951568a51", "Nova fundamentação");


//-------------TESTE DE PERSISTIR E BUSCAR RADOC------------
//        Map<String, Valor> map = new HashMap<>();
//        Valor valor = new Valor("43");
//        map.put("haha", valor);
//        Relato relato = new Relato("fasdfsd",map);
//        List<Relato> relatos = new ArrayList<>();
//        Radoc radoc = new Radoc("fasdf", 2016 , relatos);
//
//        System.out.println(daoParecer.persisteRadoc(radoc));
//
//        Radoc radoc1 = daoParecer.radocById(radoc.getId());
//        System.out.println(radoc1.getId());

//-------------REMOVER RADOC CRIADO ACIMA-------------
//           daoParecer.removeRadoc("fasdf");
    }
}
