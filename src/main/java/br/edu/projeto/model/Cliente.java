package br.edu.projeto.model;

public class Cliente {
	private String nome;
	private String nome_social;
	private String cpf;
	private String genero;
	private int telefone;
	private double altura;
	private double massa;
	private short  idade;
	private String email;
	private String endereco;
	private int id_cliente;
	
	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome_social() {
		return nome_social;
	}
	
	public void setNome_social(String nome_social) {
		this.nome_social = nome_social;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public short getIdade() {
		return idade;
	}

	public void setIdade(short idade) {
		this.idade = idade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getMassa() {
		return massa;
	}

	public void setMassa(double massa) {
		this.massa = massa;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", nome_social=" + nome_social + ", cpf=" + cpf + ", genero=" + genero
				+ ", telefone=" + telefone + ", altura=" + altura + ", massa=" + massa + ", idade=" + idade + ", email="
				+ email + ", endereco=" + endereco + ", id_cliente=" + id_cliente + "]";
	}
	
}
