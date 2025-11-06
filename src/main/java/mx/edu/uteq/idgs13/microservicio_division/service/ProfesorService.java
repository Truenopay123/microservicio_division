package mx.edu.uteq.idgs13.microservicio_division.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.edu.uteq.idgs13.microservicio_division.dto.ProfesorDto;
import mx.edu.uteq.idgs13.microservicio_division.entity.Division;
import mx.edu.uteq.idgs13.microservicio_division.entity.Profesor;
import mx.edu.uteq.idgs13.microservicio_division.repository.DivisionRepository;
import mx.edu.uteq.idgs13.microservicio_division.repository.ProfesorRepository;

@Service
public class ProfesorService {
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private DivisionRepository divisionRepository;

    public ProfesorDto asignarDivision(Long profesorId, Long divisionId) {
        Profesor profesor = profesorRepository.findById(profesorId)
            .orElseThrow(() -> new RuntimeException("Profesor no encontrado con id: " + profesorId));
        Division division = divisionRepository.findById(divisionId)
            .orElseThrow(() -> new RuntimeException("División no encontrada con id: " + divisionId));
        profesor.setDivision(division);
        profesor = profesorRepository.save(profesor);
        ProfesorDto dto = new ProfesorDto();
        dto.setId(profesor.getId());
        dto.setNombre(profesor.getNombre());
        dto.setApellidos(profesor.getApellidos());
        dto.setEmail(profesor.getEmail());
        dto.setActivo(profesor.isActivo());
        if (profesor.getDivision() != null) {
            dto.setDivisionId(profesor.getDivision().getId());
        }
        return dto;
    }
}