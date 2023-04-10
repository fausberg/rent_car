package rentcar.rentcar.exception;

public class ValidationException extends Exception {
    String massage;

    public ValidationException(String massage) {
        this.massage = massage;
    }
}
