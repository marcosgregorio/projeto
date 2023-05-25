package br.edu.projeto.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import br.edu.projeto.model.Cliente;
import br.edu.projeto.util.DbUtil;

public class ClienteDAO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private Connection con = null;
	
    private PreparedStatement preparedStatement = null;
	    
	private ResultSet result = null;
	
	private String sql = "";
    
    @Inject
    private DataSource ds;
    
    public Cliente findById(Integer id) {
    	Cliente cliente = new Cliente();
    	this.sql = "SELECT nome, nome_social, idade FROM clientes WHERE id_cliente = ?";
    	try {
    		this.prepareQuery();
			this.preparedStatement.setInt(1, id);
			this.result = this.preparedStatement.executeQuery();
			if (result.next()) {
				cliente.setId_cliente(id);
				cliente.setNome(this.result.getString("nome"));
				cliente.setNome_social(this.result.getString("nome_social"));
				cliente.setIdade(this.result.getShort("idade"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeDbConnection();
		}
    	return cliente;
    }
    
    public Cliente findByName(String name) {
    	Cliente cliente = new Cliente();
    	this.sql = "SELECT id_cliente, nome, nome_social, idade FROM Cliente WHERE nome = ?";
    	try {
    		this.prepareQuery();
			this.preparedStatement.setString(1, name);
			this.result = this.preparedStatement.executeQuery();
			if (this.result.next()) {
				cliente.setId_cliente(this.result.getInt("id_cliente"));
				cliente.setNome(name);
				cliente.setNome_social(this.result.getString("nome_social"));
				cliente.setIdade(this.result.getShort("idade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeDbConnection();
		}
        return cliente;
    }

    public List<Cliente> listAll() {
    	List<Cliente> clientes = new ArrayList<Cliente>();
    	this.sql = "SELECT * FROM clientes";
    	try {
    		this.prepareQuery();
			this.result = this.preparedStatement.executeQuery();
			while (result.next()) {
				Cliente cliente = new Cliente();
				cliente.setEmail(this.result.getString("email"));
				cliente.setId_cliente(this.result.getInt("id_cliente"));
				cliente.setNome(this.result.getString("nome"));
				cliente.setNome_social(this.result.getString("nome_social"));
				cliente.setCpf(this.result.getString("cpf"));
				cliente.setAltura(this.result.getDouble("altura"));
				cliente.setMassa(this.result.getDouble("massa"));
				cliente.setGenero(this.result.getString("genero"));
				cliente.setIdade(this.result.getShort("idade"));
				cliente.setEmail(this.result.getString("email"));
				cliente.setTelefone(this.result.getInt("telefone"));
				cliente.setEndereco(this.result.getString("endereco"));
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeDbConnection();
		}
        return clientes;
    }
    
    public List<Cliente> listaFiltrado(String filtro1) {
    	List<Cliente> clientes = new ArrayList<Cliente>();
    	this.sql = "SELECT * FROM clientes";
    	if (!filtro1.equals(""))
    			this.sql += "WHERE nome ilike '%" + filtro1 + "%'";
    	try {
    		this.prepareQuery();
			this.result = this.preparedStatement.executeQuery();
			while (result.next()) {
				Cliente cliente = new Cliente();
				cliente.setEmail(this.result.getString("email"));
				cliente.setId_cliente(this.result.getInt("id_cliente"));
				cliente.setNome(this.result.getString("nome"));
				cliente.setNome_social(this.result.getString("nome_social"));
				cliente.setCpf(this.result.getString("cpf"));
				cliente.setAltura(this.result.getDouble("altura"));
				cliente.setMassa(this.result.getDouble("massa"));
				cliente.setGenero(this.result.getString("genero"));
				cliente.setIdade(this.result.getShort("idade"));
				cliente.setEmail(this.result.getString("email"));
				cliente.setTelefone(this.result.getInt("telefone"));
				cliente.setEndereco(this.result.getString("endereco"));
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeDbConnection();
		}
        return clientes;
    }
    
    public Boolean insert(Cliente cliente) {
    	this.sql = "INSERT INTO clientes (nome, "
	    			+ "email, "
	    			+ "cpf, "
	    			+ "nome_social, "
	    			+ "genero, "
	    			+ "idade, "
	    			+ "altura, "
	    			+ "massa, "
	    			+ "telefone, "
	    			+ "endereco) "
    			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
    			+ "RETURNING id_cliente";
    	Boolean resultado = false;
    	PreparedStatement ps2 = null;
    	try {
    		this.prepareQuery();
	    	this.con.setAutoCommit(false);
	    	try {				
				this.preparedStatement.setString(1, cliente.getNome());
				this.preparedStatement.setString(2, cliente.getEmail());
				this.preparedStatement.setString(3, cliente.getCpf());
				this.preparedStatement.setString(4, cliente.getNome_social());
				this.preparedStatement.setString(5, cliente.getGenero());
				this.preparedStatement.setDouble(6, cliente.getAltura());
				this.preparedStatement.setDouble(7, cliente.getMassa());
				this.preparedStatement.setInt(8, cliente.getIdade());
				this.preparedStatement.setInt(9, cliente.getTelefone());
				this.preparedStatement.setString(10, cliente.getEndereco());
				this.result = this.preparedStatement.executeQuery();
				this.result.next();
				cliente.setId_cliente(result.getInt("id_cliente"));
				resultado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
			this.closeDbConnection();
		}
    	return resultado;
    }
    
    public Boolean update(Cliente cliente) {
    	Boolean resultado = false;
    	this.sql = "UPDATE clientes SET "
	    			+ "nome = ?,  "
	    			+ "email = ?, "
	    			+ "cpf = ?,"
	    			+ "nome_social = ?,"
	    			+ "altura = ?,"
	    			+ "massa = ?,"
	    			+ "genero = ?,"
	    			+ "idade = ?,"
	    			+ "telefone = ?,"
	    			+ "endereco = ?"
    			+ " WHERE id_cliente = ?";
    	try {
    		this.prepareQuery();
	    	this.con.setAutoCommit(false);
	    	try {				
				this.preparedStatement.setString(1, cliente.getNome());
				this.preparedStatement.setString(2, cliente.getEmail());
				this.preparedStatement.setString(3, cliente.getCpf());
				this.preparedStatement.setString(4, cliente.getNome_social());
				this.preparedStatement.setDouble(5, cliente.getMassa());
				this.preparedStatement.setDouble(6, cliente.getAltura());
				this.preparedStatement.setString(7, cliente.getGenero());
				this.preparedStatement.setInt(8, cliente.getIdade());
				this.preparedStatement.setInt(9, cliente.getTelefone());
				this.preparedStatement.setString(10, cliente.getEndereco());
				this.preparedStatement.setInt(11, cliente.getId_cliente());
				this.preparedStatement.execute();	
				con.commit();
				resultado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		this.closeDbConnection();
    	}
    	return resultado;
    }
    
    public Boolean delete(Cliente cliente) {
    	Boolean resultado = false;
    	this.sql = "DELETE FROM clientes WHERE id_cliente = ?";
    	try {
    		this.prepareQuery();
	    	con.setAutoCommit(false);
	    	try {				
				this.preparedStatement.setInt(1, cliente.getId_cliente());
				this.preparedStatement.execute();
				con.commit();
				resultado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} finally {
    		this.closeDbConnection();
		}
    	return resultado;
    }
    
    /*
     * Estabelece a conexão com o banco de dados 
     * e já prepara o sql da consulta.
    */
    private void prepareQuery() throws SQLException {
    	this.con = this.ds.getConnection();
		this.preparedStatement = this.con.prepareStatement(this.sql);

    }
    
    private void closeDbConnection() {
    	DbUtil.closeResultSet(this.result);
		DbUtil.closePreparedStatement(this.preparedStatement);
		DbUtil.closeConnection(this.con);
    }
}
