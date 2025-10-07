package mx.edu.uteq.idgs13.microservicio_division.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uteq.idgs13.microservicio_division.dto.DivisionToViewListDto;
import mx.edu.uteq.idgs13.microservicio_division.entity.Division;
import mx.edu.uteq.idgs13.microservicio_division.entity.ProgramaEducativo;
import mx.edu.uteq.idgs13.microservicio_division.repository.DivisionRepository;

@Service
public class DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;

    public List<DivisionToViewListDto> findAll() {
        List<Division> divisiones = divisionRepository.findAll();
        List<DivisionToViewListDto> resultado = new ArrayList<>();
        for (Division division : divisiones) {
            DivisionToViewListDto dto = new DivisionToViewListDto();
            dto.setDivisionId(division.getId());
            dto.setNombre(division.getNombre());
            if (division.getProgramasEducativos() != null) {
                List<String> programas = new ArrayList<>();
                for (ProgramaEducativo prog : division.getProgramasEducativos()) {
                    programas.add(prog.getPrograma());
                }
                dto.setProgramasEducativos(programas);
            } else {
                dto.setProgramasEducativos(new ArrayList<>());
            }
            resultado.add(dto);
        }
        return resultado;
    }
  
    public Division findById(Long id) throws Exception {
        return divisionRepository.findById(id)
            .orElseThrow(() -> new Exception("División no encontrada con id: " + id));
    }

    public Division updateDivision(Long id, String nombre, boolean activo) throws Exception {
        Division division = divisionRepository.findById(id)
            .orElseThrow(() -> new Exception("División no encontrada con id: " + id));
        
        division.setNombre(nombre);
        division.setActivo(activo);
        
        return divisionRepository.save(division);
    }
  }

