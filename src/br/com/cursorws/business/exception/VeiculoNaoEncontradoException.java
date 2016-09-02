package br.com.cursorws.business.exception;

/**
 * Classe de excecao disparada pela camada de negocio.
 * 
 * @author Esdras Barreto
 */
public class VeiculoNaoEncontradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2156824931405751091L;

	public VeiculoNaoEncontradoException() {
		super("Veiculo não encontrado!");
	}
}
