package api.file;

public class FileEncodingException extends RuntimeException {

    public FileEncodingException(String message, Throwable cause) {
        super(String.format("%s: %s", message, cause.getMessage()), cause);
    }
}
