package pe.com.seek.seekcandidatesmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.seek.seekcandidatesmanager.domain.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Boolean existsByEmail(String email);

}
