package br.com.cursorws.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.cursorws.model.xml.DateAdapter;

public class Veiculo extends BaseModel implements Serializable {

	private static final long serialVersionUID = -4062422424991417157L;

	@NotNull
	@Size(min = 7, max = 7)
	private String placa;

	@NotNull
	@Size(min = 3, max = 100)
	private String nome;

	@Past
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date data;

	@Digits(fraction = 2, integer = 999999)
	@NotNull
	private Double valor;

	public Veiculo() {
		super();
	}

	public Veiculo(Long id) {
		this();
		setId(id);
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}