package br.com.cursorws.business.exception;

/**
 * Classe de excecao disparada pela camada de negocio.
 * @author Fabio Barros
 */
public class UsuarioNaoEncontradoException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1071896537277884578L;

	public UsuarioNaoEncontradoException() {
		super("Login ou senha invalidos!");
	}
}
