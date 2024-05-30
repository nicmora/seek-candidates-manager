package pe.com.seek.seekcandidatesmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.seek.seekcandidatesmanager.domain.dto.CandidateDTO;
import pe.com.seek.seekcandidatesmanager.domain.entity.Candidate;
import pe.com.seek.seekcandidatesmanager.exception.CandidateAlreadyExistsException;
import pe.com.seek.seekcandidatesmanager.exception.CandidateNotFoundException;
import pe.com.seek.seekcandidatesmanager.mapper.CandidateMapper;
import pe.com.seek.seekcandidatesmanager.repository.CandidateRepository;
import pe.com.seek.seekcandidatesmanager.service.CandidateService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Override
    public List<CandidateDTO> getAll() {
        return candidateRepository.findAll()
                .stream()
                .map(candidateMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CandidateDTO> getById(Long id) {
        return candidateRepository.findById(id)
                .map(candidateMapper);
    }

    @Override
    public CandidateDTO create(CandidateDTO candidateDTO) {
        String email = candidateDTO.getEmail();

        return Optional.of(candidateRepository.existsByEmail(email))
                .filter(exist -> !exist)
                .map(notExists -> Candidate.builder()
                        .name(candidateDTO.getName())
                        .email(candidateDTO.getEmail())
                        .gender(candidateDTO.getGender())
                        .salaryExpected(candidateDTO.getSalaryExpected())
                        .build()
                )
                .map(candidateRepository::save)
                .map(candidateMapper)
                .orElseThrow(() -> new CandidateAlreadyExistsException("Candidate already exists"));
    }

    @Override
    public CandidateDTO updateById(Long id, CandidateDTO candidateDTO) {
        return candidateRepository.findById(id)
                .map(candidate -> candidate.toBuilder()
                        .name(candidateDTO.getName())
                        .gender(candidateDTO.getGender())
                        .salaryExpected(candidateDTO.getSalaryExpected())
                        .build()
                )
                .map(candidateRepository::save)
                .map(candidateMapper)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found"));
    }

    @Override
    public void deleteById(Long id) {
        candidateRepository.deleteById(id);
    }

}
