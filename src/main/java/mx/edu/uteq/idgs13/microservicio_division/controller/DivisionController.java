package mx.edu.uteq.idgs13.microservicio_division.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import mx.edu.uteq.idgs13.microservicio_division.dto.DivisionStatusDto;
import mx.edu.uteq.idgs13.microservicio_division.dto.DivisionToViewListDto;
import mx.edu.uteq.idgs13.microservicio_division.dto.ProgramaEducativoDto;
import mx.edu.uteq.idgs13.microservicio_division.entity.Division;
import mx.edu.uteq.idgs13.microservicio_division.repository.DivisionRepository;
import mx.edu.uteq.idgs13.microservicio_division.service.DivisionService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/divisiones")
public class DivisionController {
    @Autowired
    private DivisionService divisionService;

    @Autowired
    public DivisionRepository divisionRepository;

    @GetMapping
    public List<DivisionToViewListDto> getAllDivisiones() {
        return divisionService.findAll();
    }

    @GetMapping("/all")
    public List<Division> getAll() {
        return divisionRepository.findAll();
    }
    // Endpoint para crear una nueva división
    @PostMapping
    public ResponseEntity<DivisionToViewListDto> createDivision(@RequestBody DivisionToViewListDto divisionDto) {
        DivisionToViewListDto savedDivision = divisionService.addDivision(divisionDto);

        return new ResponseEntity<>(savedDivision, HttpStatus.CREATED);
    }

    // Endpoint para editar un programa educativo
    @PutMapping("/programas/{programaId}")
    public ResponseEntity<ProgramaEducativoDto> updateProgramaEducativo(@PathVariable Long programaId,
            @RequestBody ProgramaEducativoDto programaDto) {
        ProgramaEducativoDto updatedPrograma = divisionService.updateProgramaEducativo(programaId, programaDto);
        return new ResponseEntity<>(updatedPrograma, HttpStatus.OK);
    }

    // Endpoint para activar o desactivar una división
    @PutMapping("/{id}/updateStatus")
    public Division updateDivisionStatus(@PathVariable Long id, @RequestBody DivisionStatusDto statusDto) {
        return divisionService.updateDivisionStatus(id, statusDto.isActivo());
  
  @GetMapping("/{id}")
    public Division getDivisionById(@PathVariable Long id) throws Exception {
        return divisionService.findById(id);
    }

    @PutMapping("/{id}")
    public Division editDivision(@PathVariable Long id, @RequestBody DivisionEditDto dto) throws Exception {
        return divisionService.updateDivision(id, dto.getNombre(), dto.isActivo());
    }
    
  @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDivision(@PathVariable Long id) {
    try {
        divisionService.deleteDivision(id);
        return ResponseEntity.ok("División eliminada físicamente con id: " + id);
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage()); // 🔹 Devuelve 404 en lugar de 500
    }
}