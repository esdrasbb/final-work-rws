package br.com.cursorws.business;

import java.lang.reflect.InvocationTargetException;
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

import org.apache.commons.beanutils.BeanUtils;

import br.com.cursorws.business.exception.BeanNotFoundException;
import br.com.cursorws.business.exception.ValidacaoException;
import br.com.cursorws.dao.Repositorio;
import br.com.cursorws.model.Cidade;
import br.com.cursorws.model.Estado;
import br.com.cursorws.model.EstadoForm;

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
		cidade.setId(1L);
		cidade.setNome("Cidade 1");
		cidade.setPib(100.00D);
		cidade.setPopulacao(1000L);
		cidade.setData(data.getTime());
		cidade.setEstadoId(1L);

		Cidade cidade2 = new Cidade();
		cidade2.setId(2L);
		cidade2.setNome("Cidade 2");
		cidade2.setPib(200.00D);
		cidade2.setPopulacao(2000L);
		cidade2.setData(data.getTime());
		cidade2.setEstadoId(1L);

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

	public Long inserir(EstadoForm estadoForm) throws ValidacaoException{
		Estado estado = new Estado();
		try {
			BeanUtils.copyProperties(estado, estadoForm);
		} catch (IllegalAccessException | InvocationTargetException e) {
		}
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