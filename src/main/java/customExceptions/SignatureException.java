package customExceptions;

public class SignatureException extends Exception {
    private final String errorCode;

    public SignatureException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
