package br.usp.pcs.coop8.ssms.protocol;
/**
 * 
 * @author rodrigo
 *
 */
public class InvalidMessageException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6028742895931156159L;

	public InvalidMessageException(String message){
		super(message);
	}
}
