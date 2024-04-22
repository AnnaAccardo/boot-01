package com.example.boot01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// localhost:8080/test
// localhost:8080 - ufficio corrispondenza
// /test - ufficio
@Controller
@RequestMapping("/test") //specifica qual è l'ufficio
public class TestController {

    // localhost:8080/test
    @GetMapping //il controller mappa la richiesta indirizzata a /test, richiesta che arriva con Method GET, questo metodo gestirà la RICHIESTA PRIMARIA che arriva all'ufficio
    @ResponseBody //permette che la stringa ritornata sia effettivamente il corpo nella risposta, quindi non si cercherà il file html nei templates ma si risponde con questa stringa, bypassando quindi il sistema di default di Spring che cercherebbe "Benvenuto in questa applicazione" come nome del file html
    public String metodoUno(){ //impiegato
        return "Benvenuto in questa applicazione";
    }

    // localhost:8080/test/due
    @GetMapping("/due")
    @ResponseBody
    public String metodoDue(){ //impiegato
        return "<h1>Benvenuto</h1>"; //possiamo scrivere codice html
    }

    // localhost:8080/test/tre?nome=Mario&cognome=Rossi
    @GetMapping("/tre")
    @ResponseBody
    public String metodoTre(
            @RequestParam("nome") String nomeUtente, //specifichiamo che arriverà un parametro con riferimento alla chiave nome, serve soprattutto in casi in cui chiave (quello nella request) e parametro (variabile java) hanno nome diverso
            @RequestParam("cognome") String cognomeUtente
    ){
        return "<h1>Benvenuto " + nomeUtente + " " + cognomeUtente +"</h1>";
    }
    //se manco un parametro nella richiesta sul broswer l'errore e' Bad Request, 400

    // localhost:8080/test/quattro?nome=Mario&cognome=Rossi
    @GetMapping("/quattro")
    @ResponseBody
    public String metodoQuattro(
            @RequestParam(name = "nome", required = false, defaultValue = "Sconosciuto") String nomeUtente, //required = false così che funzioni e non dia errore 400 se non passo uno o entrambi i parametri
            @RequestParam(name = "cognome", required = false, defaultValue = "Sconosciuto") String cognomeUtente
    ){
        if(cognomeUtente == null)
            cognomeUtente = "Sconosciuto";
        return "<h1>Benvenuto " + nomeUtente + " " + cognomeUtente +"</h1>";
    }

    // localhost:8080/test/cinque?numero=10
    @GetMapping("/cinque")
    @ResponseBody
    public String metodoCinque(@RequestParam(name = "numero", required = false, defaultValue = "0") int numeroUtente){
        //se si lascia vuoto numero=, non va bene perché viene assegnata una stringa vuota a numero, e non va bene perché noi ci aspettiamo un numero e non una stringa, quindi bisogna aggiungere un defaultValue
        numeroUtente++;
        return "Il numero incrementato e' " + numeroUtente;
    }

    // localhost:8080/test/sei?numero=ciao
    @GetMapping("/sei")
    @ResponseBody
    public String metodoSei(@RequestParam(name = "numero", required = false) String numeroUtente){ //per gestire eventuali inserimenti errati, usiamo una String invece di un int
        String risposta;
        try
        {
            int numero = Integer.parseInt(numeroUtente);
            numero++;
            risposta = "Il numero incrementato e' " + numero;
        }catch (Exception e)
        {
            risposta = "Ci hai fornito un valore non corretto";
        }

        return  risposta;
    }

}
