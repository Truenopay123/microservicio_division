package mx.edu.uteq.idgs13.microservicio_division.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.uteq.idgs13.microservicio_division.dto.DivisionToViewListDto;
import mx.edu.uteq.idgs13.microservicio_division.entity.Division;
import mx.edu.uteq.idgs13.microservicio_division.repository.DivisionRepository;
import mx.edu.uteq.idgs13.microservicio_division.service.DivisionService;
import org.springframework.web.bind.annotation.PostMapping;
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
        // Lógica para obtener todas las divisiones
        return divisionRepository.findAll(); // Reemplaza con la lista real de divisiones
    }

    // Endpoint para crear una nueva división
    @PostMapping
    public ResponseEntity<DivisionToViewListDto> createDivision(@RequestBody DivisionToViewListDto divisionDto) {
        DivisionToViewListDto savedDivision = divisionService.addDivision(divisionDto);
        
        return new ResponseEntity<>(savedDivision, HttpStatus.CREATED);
    }

}
