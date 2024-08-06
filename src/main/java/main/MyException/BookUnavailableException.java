package main.MyException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class BookUnavailableException extends RuntimeException{
    public BookUnavailableException(String message) {
        super(message);
    }
}
