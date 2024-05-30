package pe.com.seek.seekcandidatesmanager.mapper;

import org.springframework.stereotype.Component;
import pe.com.seek.seekcandidatesmanager.domain.dto.CandidateDTO;
import pe.com.seek.seekcandidatesmanager.domain.entity.Candidate;

import java.util.function.Function;

@Component
public class CandidateDTOMapper implements Function<Candidate, CandidateDTO> {

    @Override
    public CandidateDTO apply(Candidate candidate) {
        return CandidateDTO.builder()
                .name(candidate.getName())
                .email(candidate.getEmail())
                .gender(candidate.getGender())
                .salaryExpected(candidate.getSalaryExpected())
                .build();
    }

}
