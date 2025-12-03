package mx.edu.uteq.idgs13.microservicio_division.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.edu.uteq.idgs13.microservicio_division.entity.ProgramaEducativo;
import mx.edu.uteq.idgs13.microservicio_division.service.ProgramaEducativoService;

@RestController
@RequestMapping("/api/programas")
public class ProgramaEducativoController {
    @Autowired
    private ProgramaEducativoService programaEducativoService;

    @GetMapping
    public List<ProgramaEducativo> getAll() {
        return programaEducativoService.findAll();
    }

    @GetMapping("/division/{divisionId}")
    public List<ProgramaEducativo> getByDivision(@PathVariable Long divisionId) {
        return programaEducativoService.findByDivisionId(divisionId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<ProgramaEducativo> programa = programaEducativoService.findById(id);
        return programa.isPresent() ? ResponseEntity.ok(programa.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/division/{divisionId}")
    public ResponseEntity<?> create(@PathVariable Long divisionId, @RequestBody ProgramaEducativo programa) {
        try {
            ProgramaEducativo creado = programaEducativoService.saveWithDivision(divisionId, programa);
            return ResponseEntity.ok(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProgramaEducativo programa) {
        Optional<ProgramaEducativo> existing = programaEducativoService.findById(id);
        if (existing.isPresent()) {
            programa.setId(id);
            return ResponseEntity.ok(programaEducativoService.save(programa));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<ProgramaEducativo> existing = programaEducativoService.findById(id);
        if (existing.isPresent()) {
            programaEducativoService.deleteById(id);
            return ResponseEntity.ok().body("Programa educativo eliminado correctamente");
        } else {
            return ResponseEntity.status(404).body("Programa educativo no existe");
        }
    }
}
