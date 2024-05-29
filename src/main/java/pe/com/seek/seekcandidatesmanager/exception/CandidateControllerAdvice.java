package pe.com.seek.seekcandidatesmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.com.seek.seekcandidatesmanager.controller.CandidateController;

import java.util.Map;

@RestControllerAdvice(assignableTypes = CandidateController.class)
public class CandidateControllerAdvice {

    @ExceptionHandler(CandidateAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleCandidateAlreadyExistsException(CandidateAlreadyExistsException ex) {
        Map<String, Object> errorResponse = Map.of("status", "Conflict",
                "code", HttpStatus.CONFLICT.value(),
                "message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCandidateNotFoundException(CandidateNotFoundException ex) {
        Map<String, Object> errorResponse = Map.of("status", "Not Found",
                "code", HttpStatus.NOT_FOUND.value(),
                "message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        Map<String, Object> errorResponse = Map.of("status", "Internal Server Error",
                "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
