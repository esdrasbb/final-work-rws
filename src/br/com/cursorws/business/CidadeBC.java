package br.com.cursorws.business;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.beanutils.BeanUtils;

import br.com.cursorws.business.exception.BeanNotFoundException;
import br.com.cursorws.business.exception.ValidacaoException;
import br.com.cursorws.dao.Repositorio;
import br.com.cursorws.model.BaseModel;
import br.com.cursorws.model.Cidade;
import br.com.cursorws.model.Estado;

@ApplicationScoped
public class CidadeBC {

	@Inject
	private Repositorio repositorio;

	public Cidade selecionar(Long id, Long estadoId) throws BeanNotFoundException {
		Estado estado = repositorio.selecionar(Estado.class, estadoId);
		Cidade cidade = estado.getCidades().stream().filter(c -> c.getId().equals(id)).findFirst().get();

		if (cidade == null) {
			throw new BeanNotFoundException();
		}
		return cidade;
	}

	public Long inserir(Cidade cidade) throws ValidacaoException {
		validar(cidade);
		Estado estado = repositorio.selecionar(Estado.class, cidade.getEstadoId());
		cidade.setId(this.proximoId(estado.getCidades()));
		if (estado.getCidades() != null && estado.getCidades().size() > 0) {
			estado.getCidades().add(cidade);
		} else {
			List<Cidade> cidades = new ArrayList<>();
			cidades.add(cidade);
			estado.setCidades(cidades);
		}
		return repositorio.atualizar(estado) ? cidade.getId() : 0L;
	}

	public void atualizar(Cidade cidade) throws BeanNotFoundException, ValidacaoException {
		validar(cidade);
		Estado estado = repositorio.selecionar(Estado.class, cidade.getEstadoId());
		Optional<Cidade> olderCidade = estado.getCidades().stream().filter(c -> c.getId().equals(cidade.getId()))
				.findFirst();

		if (!olderCidade.isPresent()) {
			throw new BeanNotFoundException();
		}

		boolean removed = removeCity(olderCidade.get(), estado);

		if (!removed) {
			throw new BeanNotFoundException();
		}

		this.inserir(cidade);
	}

	private boolean removeCity(Cidade cidade, Estado estado) {
		boolean removed = false;
		Iterator<Cidade> it = estado.getCidades().iterator();
		while (it.hasNext()) {
			if (it.next().getId().equals(cidade.getId())) {
				it.remove();
				removed = true;
			}
		}
		return removed;
	}

	public Cidade excluir(Long id, Long estadoId) throws BeanNotFoundException {
		Estado estado = repositorio.selecionar(Estado.class, estadoId);
		Optional<Cidade> cidade = estado.getCidades().stream().filter(c -> c.getId().equals(id)).findFirst();

		if (!cidade.isPresent()) {
			throw new BeanNotFoundException();
		}

		Cidade olderCity = new Cidade();
		try {
			BeanUtils.copyProperties(olderCity, cidade.get());
		} catch (IllegalAccessException | InvocationTargetException e) {
		}

		boolean removed = removeCity(cidade.get(), estado);

		if (!removed) {
			throw new BeanNotFoundException();
		}

		return olderCity;
	}

	private void validar(Cidade cidade) throws ValidacaoException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Cidade>> violations = validator.validate(cidade);
		if (!violations.isEmpty()) {
			ValidacaoException validacaoException = new ValidacaoException();
			for (ConstraintViolation<Cidade> violation : violations) {
				String entidade = violation.getRootBeanClass().getSimpleName();
				String propriedade = violation.getPropertyPath().toString();
				String mensagem = violation.getMessage();
				validacaoException.adicionar(entidade, propriedade, mensagem);
			}
			throw validacaoException;
		}
	}

	private Long proximoId(List<Cidade> lista) {
		Long proximo = 1L;
		if (lista != null && lista.size() > 0) {
			for (BaseModel dado : lista) {
				if (dado.getId().compareTo(proximo) >= 0) {
					proximo = dado.getId() + 1;
				}
			}
		}
		return proximo;
	}
}