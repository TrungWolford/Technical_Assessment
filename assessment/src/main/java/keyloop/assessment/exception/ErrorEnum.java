package keyloop.assessment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorEnum {

	VALIDATION_ERROR(1000, "Validation error", HttpStatus.BAD_REQUEST),
	NOT_FOUND(1001, "Resource not found", HttpStatus.NOT_FOUND),
	INTERNAL_SERVER_ERROR(1002, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
	BAD_REQUEST(1003, "Bad request", HttpStatus.BAD_REQUEST),
	UNAUTHORIZED(1004, "Unauthorized", HttpStatus.UNAUTHORIZED),
	FORBIDDEN(1005, "Forbidden", HttpStatus.FORBIDDEN),
	CONFLICT(1006, "Conflict", HttpStatus.CONFLICT);

	private final int code;
	private final String message;
	private final HttpStatus httpStatus;

	ErrorEnum(int code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}

}
