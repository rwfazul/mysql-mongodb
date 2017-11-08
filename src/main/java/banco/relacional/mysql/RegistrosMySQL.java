/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.relacional.mysql;

import banco.Registro;
import banco.RegistroDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author isabella
 * @param <T>
 */
public abstract class RegistrosMySQL<T extends Registro> implements RegistroDAO<T> {

    private String sqlInsercao;
    private String sqlAlteracao;
    private String sqlExclusao;
    private String sqlBusca;
    private String sqlBuscaTodos;

    public String getSqlInsercao() {
        return sqlInsercao;
    }

    public void setSqlInsercao(String sqlInsercao) {
        this.sqlInsercao = sqlInsercao;
    }

    public String getSqlAlteracao() {
        return sqlAlteracao;
    }

    public void setSqlAlteracao(String sqlAlteracao) {
        this.sqlAlteracao = sqlAlteracao;
    }

    public String getSqlExclusao() {
        return sqlExclusao;
    }

    public void setSqlExclusao(String sqlExclusao) {
        this.sqlExclusao = sqlExclusao;
    }

    public String getSqlBusca() {
        return sqlBusca;
    }

    public void setSqlBusca(String sqlBusca) {
        this.sqlBusca = sqlBusca;
    }

    public String getSqlBuscaTodos() {
        return sqlBuscaTodos;
    }

    public void setSqlBuscaTodos(String sqlBuscaTodos) {
        this.sqlBuscaTodos = sqlBuscaTodos;
    }

    @Override
    public void inserir(T t) {
        Connection c = ConexaoMySQL.getConexao();
        try {
            PreparedStatement ps = c.prepareStatement(getSqlInsercao());
            preencherInsercao(ps, t);
            ps.execute();
            ps.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrosMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void alterar(T t) {
        Connection c = ConexaoMySQL.getConexao();
        try {
            PreparedStatement ps = c.prepareStatement(getSqlAlteracao());
            preencherAlteracao(ps, t);
            ps.execute();
            ps.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrosMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void excluir(T t) {
        Connection c = ConexaoMySQL.getConexao();
        try {
            PreparedStatement ps = c.prepareStatement(getSqlExclusao());
            preencherExclusao(ps, t);
            ps.execute();
            ps.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrosMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    public Collection<T> buscar(T t) {
        Connection c = ConexaoMySQL.getConexao();
        Collection<T> registros = new ArrayList<T>();
        try {
            PreparedStatement ps = c.prepareStatement(getSqlBusca());
            preencherBusca(ps, t);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                T temp = preencher(rs);
                registros.add(temp);
            }
            rs.close();
            ps.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrosMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registros;
    }

    @Override
    public Collection<T> buscarTodos() {
        Connection c = ConexaoMySQL.getConexao();
        Collection<T> registros = new ArrayList<T>();
        try {
            PreparedStatement ps = c.prepareStatement(getSqlBuscaTodos());
            ResultSet rs = ps.executeQuery();
            registros = preencherLista(rs);
            ps.close();
            rs.close();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrosMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return registros;
    }

    protected abstract void preencherInsercao(PreparedStatement ps, T t) throws SQLException;

    protected abstract void preencherAlteracao(PreparedStatement ps, T t) throws SQLException;

    protected abstract void preencherExclusao(PreparedStatement ps, T t) throws SQLException;

    protected abstract void preencherBusca(PreparedStatement ps, T t) throws SQLException;

    protected abstract T preencher(ResultSet rs) throws SQLException;

    protected abstract Collection<T> preencherLista(ResultSet rs) throws SQLException;
    
}