package rentcar.rentcar.exception;

import java.util.NoSuchElementException;

public class NotFoundException extends NoSuchElementException {
    public NotFoundException(String massage) {
        super(massage);
    }
}
