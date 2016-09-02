package br.com.cursorws.business;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import br.com.cursorws.business.exception.ValidacaoException;
import br.com.cursorws.business.exception.VeiculoNaoEncontradoException;
import br.com.cursorws.dao.Repositorio;
import br.com.cursorws.model.Veiculo;

@ApplicationScoped
public class VeiculoBC {

	@Inject
	private Repositorio repositorio;

	@PostConstruct
	public void inicializar() {
		Calendar data = Calendar.getInstance();

		Veiculo veiculo = new Veiculo();
		veiculo.setNome("Fulano");
		veiculo.setPlaca("AAA1234");
		veiculo.setValor(1000.00D);
		veiculo.setData(data.getTime());
		repositorio.inserir(veiculo);

		Veiculo veiculo1 = new Veiculo();
		veiculo1.setNome("João");
		veiculo1.setPlaca("AAA9999");
		veiculo1.setValor(500.00D);
		veiculo1.setData(data.getTime());
		repositorio.inserir(veiculo1);

		Veiculo veiculo2 = new Veiculo();
		veiculo2.setNome("Maria");
		veiculo2.setPlaca("ZZZ9999");
		veiculo2.setValor(100.00D);
		veiculo2.setData(data.getTime());
		repositorio.inserir(veiculo2);

	}

	public List<Veiculo> selecionar() {
		return repositorio.selecionar(Veiculo.class);
	}

	public Veiculo selecionar(Long id) throws VeiculoNaoEncontradoException {
		Veiculo veiculo = repositorio.selecionar(Veiculo.class, id);
		if (veiculo == null) {
			throw new VeiculoNaoEncontradoException();
		}
		return veiculo;
	}

	public Long inserir(Veiculo veiculo) throws ValidacaoException {
		validar(veiculo);
		return repositorio.inserir(veiculo);
	}

	public void atualizar(Veiculo veiculo) throws VeiculoNaoEncontradoException, ValidacaoException {
		validar(veiculo);
		if (!repositorio.atualizar(veiculo)) {
			throw new VeiculoNaoEncontradoException();
		}
	}

	public Veiculo excluir(Long id) throws VeiculoNaoEncontradoException {
		Veiculo veiculo = repositorio.excluir(Veiculo.class, id);
		if (veiculo == null) {
			throw new VeiculoNaoEncontradoException();
		}
		return veiculo;
	}

	private void validar(Veiculo veiculo) throws ValidacaoException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Veiculo>> violations = validator.validate(veiculo);
		if (!violations.isEmpty()) {
			ValidacaoException validacaoException = new ValidacaoException();
			for (ConstraintViolation<Veiculo> violation : violations) {
				String entidade = violation.getRootBeanClass().getSimpleName();
				String propriedade = violation.getPropertyPath().toString();
				String mensagem = violation.getMessage();
				validacaoException.adicionar(entidade, propriedade, mensagem);
			}
			throw validacaoException;
		}
	}
}