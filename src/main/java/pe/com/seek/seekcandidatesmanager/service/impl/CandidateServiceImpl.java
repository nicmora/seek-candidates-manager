package pe.com.seek.seekcandidatesmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.seek.seekcandidatesmanager.domain.dto.CandidateDTO;
import pe.com.seek.seekcandidatesmanager.domain.entity.Candidate;
import pe.com.seek.seekcandidatesmanager.exception.CandidateAlreadyExistsException;
import pe.com.seek.seekcandidatesmanager.exception.CandidateNotFoundException;
import pe.com.seek.seekcandidatesmanager.mapper.CandidateDTOMapper;
import pe.com.seek.seekcandidatesmanager.repository.CandidateRepository;
import pe.com.seek.seekcandidatesmanager.service.CandidateService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateDTOMapper candidateDTOMapper;

    @Override
    public List<CandidateDTO> getAll() {
        return candidateRepository.findAll()
                .stream()
                .map(candidateDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDTO getById(Long id) {
        return candidateRepository.findById(id)
                .map(candidateDTOMapper)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id=" + id));
    }

    @Override
    public CandidateDTO create(CandidateDTO candidateDTO) {
        String email = candidateDTO.getEmail();

        return Optional.of(candidateRepository.existsByEmail(email))
                .filter(exist -> !exist)
                .map(notExists -> this.toEntity(candidateDTO))
                .map(candidateRepository::save)
                .map(candidateDTOMapper)
                .orElseThrow(() -> new CandidateAlreadyExistsException("Candidate already exists"));
    }

    @Override
    public CandidateDTO updateById(Long id, CandidateDTO candidateDTO) {
        return candidateRepository.findById(id)
                .map(candidate -> this.updateEntity(candidate, candidateDTO))
                .map(candidateRepository::save)
                .map(candidateDTOMapper)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id=" + id));
    }

    @Override
    public void deleteById(Long id) {
        candidateRepository.findById(id)
                .ifPresentOrElse(candidateRepository::delete, () -> {
                    throw new CandidateNotFoundException("Candidate not found with id=" + id);
                });
    }

    private Candidate toEntity(CandidateDTO candidateDTO) {
        return Candidate.builder()
                .name(candidateDTO.getName())
                .email(candidateDTO.getEmail())
                .gender(candidateDTO.getGender())
                .salaryExpected(candidateDTO.getSalaryExpected())
                .build();
    }

    private Candidate updateEntity(Candidate candidate, CandidateDTO candidateDTO) {
        return candidate.toBuilder()
                .name(candidateDTO.getName())
                .gender(candidateDTO.getGender())
                .salaryExpected(candidateDTO.getSalaryExpected())
                .build();
    }

}
