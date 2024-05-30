package pe.com.seek.seekcandidatesmanager.service;

import pe.com.seek.seekcandidatesmanager.domain.dto.CandidateDTO;

import java.util.List;
import java.util.Optional;

public interface CandidateService {

    List<CandidateDTO> getAll();
    CandidateDTO getById(Long id);
    CandidateDTO create(CandidateDTO candidateDTO);
    CandidateDTO updateById(Long id, CandidateDTO candidateDTO);
    void deleteById(Long id);

}
