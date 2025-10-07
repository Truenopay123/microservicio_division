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

    // 👇 nuevo método: borrar físicamente
    public void deleteDivision(Long id) {
        if (!divisionRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la división con id: " + id);
        }
        divisionRepository.deleteById(id);
    }
}
