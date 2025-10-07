package mx.edu.uteq.idgs13.microservicio_division.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.uteq.idgs13.microservicio_division.dto.DivisionEditDto;
import mx.edu.uteq.idgs13.microservicio_division.dto.DivisionToViewListDto;
import mx.edu.uteq.idgs13.microservicio_division.entity.Division;
import mx.edu.uteq.idgs13.microservicio_division.repository.DivisionRepository;
import mx.edu.uteq.idgs13.microservicio_division.service.DivisionService;

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
    

    @GetMapping("/{id}")
    public Division getDivisionById(@PathVariable Long id) throws Exception {
        return divisionService.findById(id);
    }

    @PutMapping("/{id}")
    public Division editDivision(@PathVariable Long id, @RequestBody DivisionEditDto dto) throws Exception {
        return divisionService.updateDivision(id, dto.getNombre(), dto.isActivo());
    }
}