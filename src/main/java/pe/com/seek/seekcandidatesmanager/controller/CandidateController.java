package pe.com.seek.seekcandidatesmanager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.seek.seekcandidatesmanager.domain.dto.CandidateDTO;
import pe.com.seek.seekcandidatesmanager.service.CandidateService;
import pe.com.seek.seekcandidatesmanager.util.JSONHandler;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final String LOG_KEY = "> " + this.getClass().getSimpleName() + " >";

    private final CandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getAll() {
        log.info("{} GET all candidates", LOG_KEY);

        return ResponseEntity.ok(candidateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getById(@PathVariable Long id) {
        log.info("{} GET candidate by id with id={}", LOG_KEY, id);

        return ResponseEntity.ok(candidateService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CandidateDTO> create(@RequestBody CandidateDTO candidateDTO) {
        log.info("{} POST create candidate with body={}", LOG_KEY, JSONHandler.getInstance().toJson(candidateDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(candidateService.create(candidateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDTO> updateById(@PathVariable Long id, @RequestBody CandidateDTO candidateDTO) {
        log.info("{} PUT update candidate by id with id={} and body={}", LOG_KEY, id, JSONHandler.getInstance().toJson(candidateDTO));

        return ResponseEntity.ok(candidateService.updateById(id, candidateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("{} DELETE candidate by id with id={}", LOG_KEY, id);

        candidateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
