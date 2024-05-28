package pe.com.seek.seekcandidatesmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.com.seek.seekcandidatesmanager.controller.CandidateController;

@RestControllerAdvice(assignableTypes = CandidateController.class)
public class CandidateControllerAdvice {

    @ExceptionHandler(CandidateAlreadyExistsException.class)
    public ResponseEntity<String> handleCandidateAlreadyExistsException(CandidateAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<String> handleCandidateNotFoundException(CandidateNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
