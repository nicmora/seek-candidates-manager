package pe.com.seek.seekcandidatesmanager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.seek.seekcandidatesmanager.domain.dto.CandidateDTO;
import pe.com.seek.seekcandidatesmanager.service.CandidateService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getAll() {
        return ResponseEntity.ok(candidateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getById(@PathVariable Long id) {
        return candidateService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CandidateDTO> create(@RequestBody CandidateDTO candidateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(candidateService.create(candidateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDTO> updateById(@PathVariable Long id, @RequestBody CandidateDTO candidateDTO) {
        return ResponseEntity.ok(candidateService.updateById(id, candidateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        candidateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
