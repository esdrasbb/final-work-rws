package br.com.cursorws.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.cursorws.model.xml.DateAdapter;

public class Cidade extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 236061016858867654L;

	@NotNull
	@Size(min = 3, max = 100)
	private String nome;

	@Past
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date data;

	@NotNull
	private BigInteger populacao;

	@NotNull
	private BigInteger pib;

	@NotNull
	private Long estadoId;

	public Cidade() {
		super();
	}

	public Cidade(Long id) {
		this();
		setId(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigInteger getPopulacao() {
		return populacao;
	}

	public void setPopulacao(BigInteger populacao) {
		this.populacao = populacao;
	}

	public BigInteger getPib() {
		return pib;
	}

	public void setPib(BigInteger pib) {
		this.pib = pib;
	}

	public Long getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Long estadoId) {
		this.estadoId = estadoId;
	}

}