package br.com.cursorws.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EstadoForm extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4780102674090663686L;

	@NotNull
	@Size(min = 2, max = 2)
	private String sigla;

	@NotNull
	@Size(min = 3, max = 100)
	private String nome;

	public EstadoForm() {
		super();
	}

	public EstadoForm(Long id) {
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

}