/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.mongo;

import banco.nosql.mongodb.RegistrosMongo;
import java.util.ArrayList;
import java.util.Collection;
import model.Evento;
import model.conversores.EventoConversor;
import org.bson.Document;

/**
 *
 * @author isabella
 */
public class EventoDAOMongo extends RegistrosMongo<Evento> {

    @Override
    public void inserir(Evento e) {
        Document doc = new EventoConversor().toDocument(e);
        inserirDocumento(doc);
    }

    @Override
    public void alterar(Evento e){
        Document doc = new EventoConversor().toDocument(e);
        String chave = "_id"; 
        String valor = e.getId();
        alterarDocumento("_id",  e.getId(), doc);
    }

    @Override
    public void excluir(Evento e) {
        excluirDocumento("_id", e.getId());
    }

    @Override
    public Collection<Evento> buscar(Evento e) {
        Collection<Document> documentos = buscarDocumento("nome", e.getNome()); 
        
        Collection<Evento> eventos = new ArrayList<Evento>();
        for (Document doc : documentos) {
            Evento evento = new EventoConversor().toModel(doc);
            eventos.add(evento);
        }
        
        return eventos;
    }

    @Override
    public Collection<Evento> buscarTodos(){
        Collection<Evento> eventos = new ArrayList<Evento>();

        Collection<Document> documentos = buscarTodosDocumentos();         
        for (Document doc : documentos) {
            Evento evento = new EventoConversor().toModel(doc);
            eventos.add(evento);
        }

        return eventos;    
    }
    
}