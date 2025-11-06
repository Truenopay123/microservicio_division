package mx.edu.uteq.idgs13.microservicio_division.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import mx.edu.uteq.idgs13.microservicio_division.dto.ProfesorDto;
import mx.edu.uteq.idgs13.microservicio_division.service.ProfesorService;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @PutMapping("/{profesorId}/division/{divisionId}")
    public ProfesorDto asignarDivision(@PathVariable Long profesorId, @PathVariable Long divisionId) {
        return profesorService.asignarDivision(profesorId, divisionId);
    }
}