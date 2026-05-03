package keyloop.assessment.exception;

public class ResourceNotFoundException extends AppException {

    public ResourceNotFoundException() {
        super(ErrorEnum.NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(ErrorEnum.NOT_FOUND, message);
    }

}
