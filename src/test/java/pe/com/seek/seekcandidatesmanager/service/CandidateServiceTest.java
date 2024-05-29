package pe.com.seek.seekcandidatesmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.com.seek.seekcandidatesmanager.domain.dto.CandidateDTO;
import pe.com.seek.seekcandidatesmanager.domain.entity.Candidate;
import pe.com.seek.seekcandidatesmanager.exception.CandidateAlreadyExistsException;
import pe.com.seek.seekcandidatesmanager.exception.CandidateNotFoundException;
import pe.com.seek.seekcandidatesmanager.mapper.CandidateMapper;
import pe.com.seek.seekcandidatesmanager.repository.CandidateRepository;
import pe.com.seek.seekcandidatesmanager.service.impl.CandidateServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Spy
    private CandidateMapper candidateMapper;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    private List<Candidate> candidates;

    @BeforeEach
    void setUp() {
        Candidate candidate1 = Candidate.builder().id(1L).name("John Doe").email("john@example.com").gender('M').salaryExpected(50000).build();
        Candidate candidate2 = Candidate.builder().id(2L).name("Jane Smith").email("jane@example.com").gender('F').salaryExpected(60000).build();
        Candidate candidate3 = Candidate.builder().id(3L).name("Michael Johnson").email("michael@example.com").gender('M').salaryExpected(55000).build();
        Candidate candidate4 = Candidate.builder().id(4L).name("Emily Davis").email("emily@example.com").gender('F').salaryExpected(58000).build();
        Candidate candidate5 = Candidate.builder().id(5L).name("William Brown").email("william@example.com").gender('M').salaryExpected(52000).build();

        candidates = List.of(candidate1, candidate2, candidate3, candidate4, candidate5);
    }

    @Test
    void when_getAllCandidates_thenReturnListOfCandidateDTO() {
        // Arrange
        when(candidateRepository.findAll()).thenReturn(candidates);

        // Act
        List<CandidateDTO> result = candidateService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(5, result.size());
        verify(candidateRepository).findAll();
    }

    @Test
    void when_getCandidateById_thenReturnCandidateDTO() {
        // Arrange
        Candidate candidate = Candidate.builder().id(1L).name("John Doe").email("john@example.com").gender('M').salaryExpected(50000).build();
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));

        // Act
        Optional<CandidateDTO> result = candidateService.getById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        assertEquals("john@example.com", result.get().getEmail());
        assertEquals('M', result.get().getGender());
        assertEquals(50000, result.get().getSalaryExpected());
        verify(candidateRepository).findById(1L);
    }

    @Test
    void when_getCandidateById_andNotExists_thenReturnOptionalEmpty() {
        // Arrange
        when(candidateRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        Optional<CandidateDTO> result = candidateService.getById(2L);

        // Assert
        assertTrue(result.isEmpty());
        verify(candidateRepository).findById(2L);
    }

    @Test
    void when_createCandidate_thenReturnCandidateDTO() {
        // Arrange
        CandidateDTO candidateDTO = CandidateDTO.builder().name("Obi Wan Kenobi").email("obiwan@example.com").gender('M').salaryExpected(50000).build();
        Candidate savedCandidate = Candidate.builder().id(6L).name("Obi Wan Kenobi").email("obiwan@example.com").gender('M').salaryExpected(50000).build();

        when(candidateRepository.save(any(Candidate.class))).thenReturn(savedCandidate);

        // Act
        CandidateDTO result = candidateService.create(candidateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Obi Wan Kenobi", result.getName());
        assertEquals("obiwan@example.com", result.getEmail());
        assertEquals('M', result.getGender());
        assertEquals(50000, result.getSalaryExpected());

        verify(candidateRepository).save(any(Candidate.class));
    }

    @Test
    void when_createExistingCandidate_thenThrowException() {
        // Arrange
        CandidateDTO candidateDTO = CandidateDTO.builder().name("John Doe").email("john@example.com").gender('M').salaryExpected(50000).build();
        when(candidateRepository.save(any(Candidate.class))).thenThrow(CandidateAlreadyExistsException.class);

        // Act & Assert
        assertThrows(CandidateAlreadyExistsException.class, () -> candidateService.create(candidateDTO));
        verify(candidateRepository).save(any(Candidate.class));
    }

    @Test
    void when_updateCandidate_thenReturnCandidateDTO() {
        // Arrange
        Long id = 1L;
        CandidateDTO candidateDTO = CandidateDTO.builder().name("John Connor").email("johnconnor@example.com").gender('M').salaryExpected(60000).build();
        Candidate existingCandidate = Candidate.builder().id(1L).name("John Doe").email("john@example.com").gender('M').salaryExpected(50000).build();
        Candidate updatedCandidate = Candidate.builder().id(1L).name("John Connor").email("johnconnor@example.com").gender('M').salaryExpected(60000).build();

        when(candidateRepository.findById(id)).thenReturn(Optional.of(existingCandidate));
        when(candidateRepository.save(any(Candidate.class))).thenReturn(updatedCandidate);

        // Act
        CandidateDTO result = candidateService.updateById(id, candidateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("John Connor", result.getName());
        assertEquals("johnconnor@example.com", result.getEmail());
        assertEquals('M', result.getGender());
        assertEquals(60000, result.getSalaryExpected());

        verify(candidateRepository).findById(id);
        verify(candidateRepository).save(any(Candidate.class));
    }

    @Test
    void when_updateCandidateNotExists_thenThrowException() {
        // Arrange
        CandidateDTO candidateDTO = CandidateDTO.builder().name("John Connor").email("johnconnor@example.com").gender('M').salaryExpected(60000).build();
        when(candidateRepository.findById(6L)).thenThrow(CandidateNotFoundException.class);

        // Act & Assert
        assertThrows(CandidateNotFoundException.class, () -> candidateService.updateById(6L, candidateDTO));
    }

}