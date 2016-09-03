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

import br.com.cursorws.business.exception.BeanNotFoundException;
import br.com.cursorws.business.exception.ValidacaoException;
import br.com.cursorws.dao.Repositorio;
import br.com.cursorws.model.Usuario;

@ApplicationScoped
public class UsuarioBC {

	@Inject
	private Repositorio repositorio;

	@PostConstruct
	public void inicializar() {
		Calendar data = Calendar.getInstance();

		Usuario usuario = new Usuario();
		usuario.setNome("Pedro de Alcantara");
		usuario.setEmail("pedro.alcantara@gmail.com");
		usuario.setSenha("teste123");
		usuario.setCpf("11111111111");
		data.set(1798, 9, 12);
		usuario.setData(data.getTime());
		repositorio.inserir(usuario);

		usuario = new Usuario();
		usuario.setNome("Santos Dumont");
		usuario.setEmail("santos.dumont@gmail.com");
		usuario.setSenha("teste123");
		usuario.setCpf("22222222222");
		data.set(1873, 6, 20);
		usuario.setData(data.getTime());
		repositorio.inserir(usuario);

		usuario = new Usuario();
		usuario.setNome("Isabel de Braganca");
		usuario.setEmail("maria@gmail.com");
		usuario.setSenha("teste123");
		usuario.setCpf("33333333333");
		data.set(1846, 6, 29);
		usuario.setData(data.getTime());
		repositorio.inserir(usuario);
	}

	public List<Usuario> selecionar() {
		return repositorio.selecionar(Usuario.class);
	}

	public Usuario selecionar(Long id) throws BeanNotFoundException {
		Usuario usuario = repositorio.selecionar(Usuario.class, id);
		if (usuario == null) {
			throw new BeanNotFoundException();
		}
		return usuario;
	}

	public Long inserir(Usuario usuario) throws ValidacaoException {
		validar(usuario);
		return repositorio.inserir(usuario);
	}

	public void atualizar(Usuario usuario) throws BeanNotFoundException, ValidacaoException {
		validar(usuario);
		if (!repositorio.atualizar(usuario)) {
			throw new BeanNotFoundException();
		}
	}

	public Usuario excluir(Long id) throws BeanNotFoundException {
		Usuario usuario = repositorio.excluir(Usuario.class, id);
		if (usuario == null) {
			throw new BeanNotFoundException();
		}
		return usuario;
	}

	private void validar(Usuario usuario) throws ValidacaoException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
		if (!violations.isEmpty()) {
			ValidacaoException validacaoException = new ValidacaoException();
			for (ConstraintViolation<Usuario> violation : violations) {
				String entidade = violation.getRootBeanClass().getSimpleName();
				String propriedade = violation.getPropertyPath().toString();
				String mensagem = violation.getMessage();
				validacaoException.adicionar(entidade, propriedade, mensagem);
			}
			throw validacaoException;
		}
	}
}