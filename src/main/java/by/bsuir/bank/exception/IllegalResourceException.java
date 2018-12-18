package by.bsuir.bank.exception;


public class IllegalResourceException extends Exception {
    public IllegalResourceException() {
        super("Undefined requested resource");
    }

    /**
     * @param message overwrites the default value
     */
    public IllegalResourceException(String message) {
        super(message);
    }
}
