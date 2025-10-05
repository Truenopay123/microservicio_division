package mx.edu.uteq.idgs13.microservicio_division.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.uteq.idgs13.microservicio_division.dto.DivisionToViewListDto;
import mx.edu.uteq.idgs13.microservicio_division.entity.Division;
import mx.edu.uteq.idgs13.microservicio_division.repository.DivisionRepository;
import mx.edu.uteq.idgs13.microservicio_division.service.DivisionService;



@RestController
@RequestMapping("/api/divisiones")
public class DivisionController {
    @Autowired
	private  DivisionService divisionService;

    @Autowired
    public DivisionRepository divisionRepository;

	@GetMapping
	public List<DivisionToViewListDto> getAllDivisiones() {
		return divisionService.findAll();
	}

    @GetMapping("/all")
    public List<Division> getAll() {
        // Lógica para obtener todas las divisiones
        return divisionRepository.findAll(); // Reemplaza con la lista real de divisiones
    }
    
        @org.springframework.web.bind.annotation.PostMapping("/{divisionId}/programa-educativo")
        public org.springframework.http.ResponseEntity<?> agregarProgramaEducativo(
                @org.springframework.web.bind.annotation.PathVariable Long divisionId,
                @org.springframework.web.bind.annotation.RequestBody mx.edu.uteq.idgs13.microservicio_division.entity.ProgramaEducativo programa) {
            try {
                Division result = divisionService.agregarProgramaEducativo(divisionId, programa);
                return org.springframework.http.ResponseEntity.ok(result);
            } catch (IllegalArgumentException e) {
                return org.springframework.http.ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        @org.springframework.web.bind.annotation.DeleteMapping("/{divisionId}/programa-educativo/{programaId}")
        public org.springframework.http.ResponseEntity<?> borrarProgramaEducativo(
                @org.springframework.web.bind.annotation.PathVariable Long divisionId,
                @org.springframework.web.bind.annotation.PathVariable Long programaId) {
            try {
                Division result = divisionService.borrarProgramaEducativo(divisionId, programaId);
                return org.springframework.http.ResponseEntity.ok(result);
            } catch (IllegalArgumentException e) {
                return org.springframework.http.ResponseEntity.badRequest().body(e.getMessage());
            }
        }


}
