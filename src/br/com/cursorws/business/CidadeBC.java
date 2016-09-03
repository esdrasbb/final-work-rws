package br.com.cursorws.business;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import br.com.cursorws.business.exception.BeanNotFoundException;
import br.com.cursorws.business.exception.ValidacaoException;
import br.com.cursorws.dao.Repositorio;
import br.com.cursorws.model.Cidade;

@ApplicationScoped
public class CidadeBC {

	@Inject
	private Repositorio repositorio;

	public Cidade selecionar(Long id) throws BeanNotFoundException {
		
		recuperar o estado pelo id
		criar um outro metodo para recuperar pelo id
		adicionar a cidade na lista de cidades do estado
		
		Cidade cidade = repositorio.selecionar(Cidade.class, id);
		if (cidade == null) {
			throw new BeanNotFoundException();
		}
		return cidade;
	}

	public Long inserir(Cidade cidade) throws ValidacaoException {
		validar(cidade);
		
		recuperar o estado pelo id
		adicionar a cidade na lista de cidades do estado
		
		
		return repositorio.inserir(cidade);
	}

	public void atualizar(Cidade cidade) throws BeanNotFoundException, ValidacaoException {
		validar(cidade);
		
		recuperar o estado pelo id
		alterar a cidade na lista de cidades do estado
		
		if (!repositorio.atualizar(cidade)) {
			throw new BeanNotFoundException();
		}
	}

	public Cidade excluir(Long id) throws BeanNotFoundException {
		Cidade cidade = repositorio.excluir(Cidade.class, id);
		
		recuperar o estado pelo id
		adicionar a cidade na lista de cidades do estado
		
		if (cidade == null) {
			throw new BeanNotFoundException();
		}
		return cidade;
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
}