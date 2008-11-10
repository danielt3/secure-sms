package br.usp.pcs.coop8.ssms.protocol.exception;
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
        private String failedMessage;
        
        private InvalidMessageException(String message) {
            
        }

	public InvalidMessageException(String message, String failedMessage){
		super(message);
                this.failedMessage = failedMessage;
	}
        
        public String getFailedMessage() {
            return this.failedMessage;
        }
}
