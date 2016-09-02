package br.com.cursorws.business.exception;

/**
 * Classe de excecao disparada pela camada de negocio.
 * 
 * @author Esdras Barreto
 */
public class VeiculoInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6041687506330378437L;

	public VeiculoInvalidoException() {
		super("Veiculo invalido!");
	}
}
