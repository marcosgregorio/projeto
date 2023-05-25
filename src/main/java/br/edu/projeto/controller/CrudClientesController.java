package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.ClienteDAO;
import br.edu.projeto.model.Camiseta;
import br.edu.projeto.model.Cliente;

//Escopo do objeto da classe (Bean)
//ApplicationScoped é usado quando o objeto é único na aplicação (compartilhado entre usuários), permanece ativo enquanto a aplicação estiver ativa
//SessionScoped é usado quando o objeto é único por usuário, permanece ativo enquanto a sessão for ativa
//ViewScoped é usado quando o objeto permanece ativo enquanto não houver um redirect (acesso a nova página)
//RequestScoped é usado quando o objeto só existe naquela requisição específica
//Quanto maior o escopo, maior o uso de memória no lado do servidor (objeto permanece ativo por mais tempo)
//Escopos maiores que Request exigem que classes sejam serializáveis assim como todos os seus atributos (recurso de segurança)
@ViewScoped
//Torna classe disponível na camada de visão (html) - são chamados de Beans ou ManagedBeans (gerenciados pelo JSF/EJB)
@Named
public class CrudClientesController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Anotação que marca atributo para ser gerenciado pelo CDI
		//O CDI criará uma instância do objeto automaticamente quando necessário
		@Inject
		private FacesContext facesContext;
		
		@Inject
	    private ClienteDAO clienteDAO;
		
		private Cliente cliente;
		
		private List<Cliente> listaClientes;
		
		private Boolean rendNovoCadastro;
		
		private String inputSearchNome;
		
		//Anotação que força execução do método após o construtor da classe ser executado
	    @PostConstruct
	    public void init() {
	    	//Inicializa elementos importantes
	    	this.setListaClientes(clienteDAO.listAll());
	    }
	    
	    //Chamado pelo botão novo
		public void novoCadastro() {
	        this.setCliente(new Cliente());
	        this.setRendNovoCadastro(true);
	    }
		
		//Chamado pelo botão alterar
		public void alterarCadastro() {
	        this.setRendNovoCadastro(false);
	    }
		
		//Chamado pelo botão remover da tabela
		public void remover() {
			if (this.clienteDAO.delete(this.cliente)) {
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Removida", null));
				this.listaClientes.remove(this.cliente);
			} else 
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Remover Cliente", null));
			//Após excluir usuário é necessário recarregar lista que popula tabela com os novos dados
			//this.listaCamiseta = camisetaDAO.listAll();
	        //Limpa seleção de usuário
			this.cliente = null;
	        PrimeFaces.current().ajax().update("form:messages", "form:dt-clientes");
		}	
		
		//Chamado ao salvar cadastro de usuário (novo)
		public void salvarNovo() {
			if (this.cliente.getGenero().equals("M") || this.cliente.getGenero().equals("F") || this.cliente.getGenero().equals("O")) {
				if (this.clienteDAO.insert(this.cliente)) {
					this.getListaClientes().add(this.cliente);
					PrimeFaces.current().executeScript("PF('clienteDialog').hide()");
					PrimeFaces.current().ajax().update("form:dt-clientes");
					this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ClienteCriada", null));
				} else
	        		this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Criar Cliente", null));
			} else {
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Gênero inválido, deve ser M, F ou O!", null));
	    	}   
			PrimeFaces.current().ajax().update("form:messages");
		}
		
		//Chamado ao salvar cadastro de usuário (alteracao)
		public void salvarAlteracao() {
			if (this.cliente.getGenero().equals("M") || this.cliente.getGenero().equals("F") || this.cliente.getGenero().equals("O")) {
				if (this.clienteDAO.update(this.cliente)) {
					PrimeFaces.current().executeScript("PF('clienteDialog').hide()");
					PrimeFaces.current().ajax().update("form:dt-clientes");
					this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente Atualizado", null));
				} else
	        		this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Atualizar Cliente", null));
			} else {
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Gênero inválido, deve ser M, F ou O!", null));
	    	}
			this.setListaClientes(clienteDAO.listAll());
			PrimeFaces.current().ajax().update("form:messages");
		}
		
		public void filtrar() {
			this.setListaClientes(this.clienteDAO.listaFiltrado(this.getInputSearchNome()));
		}
		

		public void setListaClientes(List<Cliente> listaClientes) {
			this.listaClientes = listaClientes;
		}
		
		public FacesContext getFacesContext() {
			return facesContext;
		}

		public void setFacesContext(FacesContext facesContext) {
			this.facesContext = facesContext;
		}

		public ClienteDAO getClienteDAO() {
			return clienteDAO;
		}

		public void setClienteDAO(ClienteDAO clienteDAO) {
			this.clienteDAO = clienteDAO;
		}

		public Cliente getCliente() {
			return cliente;
		}

		public void setCliente(Cliente cliente) {
			this.cliente = cliente;
		}

		public Boolean getRendNovoCadastro() {
			return rendNovoCadastro;
		}

		public void setRendNovoCadastro(Boolean rendNovoCadastro) {
			this.rendNovoCadastro = rendNovoCadastro;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public List<Cliente> getListaClientes() {
			return listaClientes;
		}

		public String getInputSearchNome() {
			return inputSearchNome;
		}

		public void setInputSearchNome(String inputSearchNome) {
			this.inputSearchNome = inputSearchNome;
		}
}
