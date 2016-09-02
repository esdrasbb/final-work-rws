package br.com.cursorws.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.cursorws.model.xml.DateAdapter;

public class Usuario extends BaseModel implements Serializable {
	private static final long serialVersionUID = -3064306490724801147L;
	@NotNull
	@Size(min = 11, max = 11)
	private String cpf;
	@NotNull
	@Size(min = 3, max = 100)
	private String nome;
	@NotNull
	@Size(min = 1, max = 300)
	private String email;
	@NotNull
	@Size(min = 6, max = 10)
	private String senha;
	@Past
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date data;

	public Usuario() {
		super();
	}

	public Usuario(Long id) {
		this();
		setId(id);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}