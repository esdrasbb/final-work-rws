package br.com.cursorws.business;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import br.com.cursorws.business.exception.BeanNotFoundException;
import br.com.cursorws.business.exception.ValidacaoException;
import br.com.cursorws.dao.Repositorio;
import br.com.cursorws.model.Cidade;
import br.com.cursorws.model.Estado;

@ApplicationScoped
public class EstadoBC {

	@Inject
	private Repositorio repositorio;

	@PostConstruct
	public void inicializar() {
		Estado estado = new Estado();
		estado.setNome("Estado 1");
		estado.setSigla("ET");
		estado.setCidades(createFakeCity());
		repositorio.inserir(estado);
	}

	private List<Cidade> createFakeCity() {
		List<Cidade> cidades = new ArrayList<>();
		Calendar data = Calendar.getInstance();

		Cidade cidade = new Cidade();
		cidade.setNome("Cidade 1");
		cidade.setPib(new BigInteger("100,00"));
		cidade.setPopulacao(new BigInteger("1000"));
		cidade.setData(data.getTime());

		Cidade cidade2 = new Cidade();
		cidade2.setNome("Cidade 2");
		cidade2.setPib(new BigInteger("200,00"));
		cidade2.setPopulacao(new BigInteger("2000"));
		cidade2.setData(data.getTime());

		cidades.add(cidade);
		cidades.add(cidade2);

		return cidades;
	}

	public List<Estado> selecionar() {
		return repositorio.selecionar(Estado.class);
	}

	public Estado selecionar(Long id) throws BeanNotFoundException {
		Estado veiculo = repositorio.selecionar(Estado.class, id);
		if (veiculo == null) {
			throw new BeanNotFoundException();
		}
		return veiculo;
	}

	public Long inserir(Estado estado) throws ValidacaoException {
		validar(estado);
		return repositorio.inserir(estado);
	}

	public void atualizar(Estado estado) throws BeanNotFoundException, ValidacaoException {
		validar(estado);
		if (!repositorio.atualizar(estado)) {
			throw new BeanNotFoundException();
		}
	}

	public Estado excluir(Long id) throws BeanNotFoundException {
		Estado estado = repositorio.excluir(Estado.class, id);
		if (estado == null) {
			throw new BeanNotFoundException();
		}
		return estado;
	}

	private void validar(Estado estado) throws ValidacaoException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Estado>> violations = validator.validate(estado);
		if (!violations.isEmpty()) {
			ValidacaoException validacaoException = new ValidacaoException();
			for (ConstraintViolation<Estado> violation : violations) {
				String entidade = violation.getRootBeanClass().getSimpleName();
				String propriedade = violation.getPropertyPath().toString();
				String mensagem = violation.getMessage();
				validacaoException.adicionar(entidade, propriedade, mensagem);
			}
			throw validacaoException;
		}
	}
}