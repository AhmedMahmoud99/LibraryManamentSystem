package main.MyException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateDFound extends RuntimeException {
    public DuplicateDFound(String message) {
        super(message);
    }
}
