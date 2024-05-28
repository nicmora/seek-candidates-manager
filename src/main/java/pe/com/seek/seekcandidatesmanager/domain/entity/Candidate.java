package pe.com.seek.seekcandidatesmanager.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "candidates")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    private Long id;
    private String name;
    private String email;
    private Character gender;
    private Integer salaryExpected;

}
