package br.com.cursorws.model;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Estado extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3068736789529495952L;

	@NotNull
	@Size(min = 2, max = 2)
	private String sigla;

	@NotNull
	@Size(min = 3, max = 100)
	private String nome;

	private List<Cidade> cidades;

	public Estado() {
		super();
	}

	public Estado(Long id) {
		this();
		setId(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

}