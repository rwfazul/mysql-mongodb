/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.mysql;

import banco.relacional.mysql.RegistrosMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import model.Palestra;

/**
 *
 * @author rhau
 */
public class PalestraDAOMySQL extends RegistrosMySQL<Palestra> {

    public PalestraDAOMySQL() {
        setSqlInsercao("INSERT INTO palestra (id_palestrante, id_evento, id_sala, titulo, assunto_area, descricao_palestra, data_inicio, data_fim) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        setSqlAlteracao("UPDATE palestra set id_evento = ?, id_sala = ?, titulo = ?, assunto_area = ?, descricao_palestra = ?, data_inicio = ?, data_fim = ? WHERE id_palestra = ?");
        setSqlExclusao("DELETE FROM palestra WHERE id_palestra = ?");
        setSqlBuscaChavePrimaria("SELECT * FROM palestra WHERE id_palestra = ?");
        setSqlBusca("SELECT * FROM palestra WHERE id_evento = ?"); 
        setSqlBuscaTodos("SELECT * FROM palestra");
    }
    
    @Override
    protected void preencherInsercao(PreparedStatement ps, Palestra p) throws SQLException {
        ps.setInt(1, p.getPalestrante().getId());
        ps.setInt(2, Integer.parseInt(p.getEvento().getId()));
        ps.setInt(3, p.getSala().getId());
        ps.setString(4, p.getTitulo());
        ps.setString(5, p.getAssunto());
        ps.setString(6, p.getDescricao());
        ps.setTimestamp(7, new java.sql.Timestamp(p.getInicio().getTime()));
        ps.setTimestamp(8, new java.sql.Timestamp(p.getFim().getTime()));
    }

    @Override
    protected void preencherAlteracao(PreparedStatement ps, Palestra p) throws SQLException {
        ps.setInt(1, p.getPalestrante().getId());
        ps.setInt(2, Integer.parseInt(p.getEvento().getId()));
        ps.setInt(3, p.getSala().getId());
        ps.setString(4, p.getTitulo());
        ps.setString(5, p.getAssunto());
        ps.setString(6, p.getDescricao());
        ps.setTimestamp(7, new java.sql.Timestamp(p.getInicio().getTime()));
        ps.setTimestamp(8, new java.sql.Timestamp(p.getFim().getTime()));
        ps.setInt(9, p.getId());
    }

    @Override
    protected void preencherExclusao(PreparedStatement ps, Palestra p) throws SQLException {
        ps.setInt(1, p.getId());
    }

    @Override
    protected void preencherBusca(PreparedStatement ps, Palestra p) throws SQLException {
        ps.setString(1, p.getEvento().getId());
    }

    @Override
    protected Palestra preencher(ResultSet rs) throws SQLException {
        Palestra p = new Palestra();
        p.setId(rs.getInt("id_palestra"));
        p.setPalestrante( new PalestranteDAOMySQL().buscar(rs.getInt("id_palestrante")) );
        // p.setEvento( new EventoDAOMySQL().buscar(rs.getInt("id_evento")) );
        p.setSala( new SalaDAOMySQL().buscar(rs.getInt("id_sala"))); 
        p.setTitulo(rs.getString("titulo"));
        p.setAssunto(rs.getString("assunto_area"));
        p.setDescricao(rs.getString("descricao_palestra"));
        p.setInicio(rs.getTimestamp("data_inicio"));
        p.setFim(rs.getTimestamp("data_fim"));
        return p;
    }

    @Override
    protected Collection<Palestra> preencherLista(ResultSet rs) throws SQLException {
        Collection<Palestra> palestras = new ArrayList<>();
        while ( rs.next() )
            palestras.add(preencher(rs));
        return palestras;
    }
    
}
