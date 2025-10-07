package mx.edu.uteq.idgs13.microservicio_division.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uteq.idgs13.microservicio_division.entity.ProgramaEducativo;
import mx.edu.uteq.idgs13.microservicio_division.repository.ProgramaEducativoRepository;

@Service
public class ProgramaEducativoService {
    @Autowired
    private mx.edu.uteq.idgs13.microservicio_division.repository.DivisionRepository divisionRepository;

    public ProgramaEducativo saveWithDivision(Long divisionId, ProgramaEducativo programa) {
        var divisionOpt = divisionRepository.findById(divisionId);
        if (divisionOpt.isEmpty()) {
            throw new IllegalArgumentException("División no encontrada");
        }
        var division = divisionOpt.get();
        programa = programaEducativoRepository.save(programa);
        division.getProgramasEducativos().add(programa);
        divisionRepository.save(division);
        return programa;
    }
    @Autowired
    private ProgramaEducativoRepository programaEducativoRepository;

    public List<ProgramaEducativo> findAll() {
        return programaEducativoRepository.findAll();
    }

    public Optional<ProgramaEducativo> findById(Long id) {
        return programaEducativoRepository.findById(id);
    }

    public ProgramaEducativo save(ProgramaEducativo programa) {
        return programaEducativoRepository.save(programa);
    }

    public void deleteById(Long id) {
        programaEducativoRepository.deleteById(id);
    }
}
