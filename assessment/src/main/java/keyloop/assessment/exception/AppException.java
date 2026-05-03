package keyloop.assessment.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

	private final ErrorEnum errorEnum;

	public AppException(ErrorEnum errorEnum) {
		super(errorEnum.getMessage());
		this.errorEnum = errorEnum;
	}

	public AppException(ErrorEnum errorEnum, String message) {
		super(message);
		this.errorEnum = errorEnum;
	}

	public AppException(ErrorEnum errorEnum, Throwable cause) {
		super(errorEnum.getMessage(), cause);
		this.errorEnum = errorEnum;
	}

	public AppException(ErrorEnum errorEnum, String message, Throwable cause) {
		super(message, cause);
		this.errorEnum = errorEnum;
	}

}
