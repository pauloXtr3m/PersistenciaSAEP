package br.ufg.inf.es.saep.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;

import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by paulo on 04/07/16.
 */
public class MainTest {

    public static void main(String args[]) {
        DataBase.createCollections();
        DaoParecer daoParecer = new DaoParecer();
        Parecer parecer = new Parecer();

//-------------TESTE DE PERSISTIR E REMOVER PARECER-------------
//        daoParecer.persisteParecer(parecer);
//        try{
//            sleep(10000);
//        }catch(InterruptedException e){
//
//        }
//        daoParecer.removeParecer("31a7f033-b197-4579-ab13-f8f6d20b424c");
//        Parecer parecerTemp = daoParecer.byId("30f4bb66-8c35-41e2-bd71-5213210d354b");
//        System.out.println(parecerTemp.getId());
//        List<Nota> notas = parecerTemp.getNotas();
//        System.out.println(notas.get(0).getJustificativa());


//-------------TESTE DE ADICIONAR NOTA E REMOVER NOTA-------------

//        Valor valor = new Valor("eita");
//        Pontuacao  pontuacao = new Pontuacao("pontuacao", valor);
//        Avaliavel x = pontuacao;
//        Avaliavel y = new Avaliavel() {
//            @Override
//            public Valor get(String atributo) {
//                return valor;
//            }
//        };
//
//
//        Nota nota = new Nota(x, y, "Nova Justificat");
//        daoParecer.adicionaNota("d366030b-75c8-440b-abce-bc0d23c2603f",nota);
//
//
//        Nota nota2 = new Nota(x, y, "Nova Jus");
//        daoParecer.removeNota("d366030b-75c8-440b-abce-bc0d23c2603f", nota.getItemOriginal());
//

//-------------TESTE DE ADICIONAR FUNDAMENTACAO-------------
//        daoParecer.atualizaFundamentacao("30f4bb66-8c35-41e2-bd71-5213210d354b", "testando");


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
