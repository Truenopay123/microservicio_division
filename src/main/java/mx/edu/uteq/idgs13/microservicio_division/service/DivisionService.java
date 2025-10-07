package mx.edu.uteq.idgs13.microservicio_division.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uteq.idgs13.microservicio_division.dto.DivisionToViewListDto;
import mx.edu.uteq.idgs13.microservicio_division.dto.ProgramaEducativoDto;
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

    // Endpoint para crear una nueva división
    public DivisionToViewListDto addDivision(DivisionToViewListDto divisionDto) {
        Division division = new Division();
        division.setNombre(divisionDto.getNombre());
        division.setActivo(true);
        List<ProgramaEducativo> programas = new ArrayList<>();
        if (divisionDto.getProgramasEducativos() != null) {
            for (String progName : divisionDto.getProgramasEducativos()) {
                ProgramaEducativo prog = new ProgramaEducativo();
                prog.setPrograma(progName);
                programas.add(prog);
            }
        }
        division.setProgramasEducativos(programas);
        Division savedDivision = divisionRepository.save(division);
        DivisionToViewListDto savedDto = new DivisionToViewListDto();
        savedDto.setDivisionId(savedDivision.getId());
        savedDto.setNombre(savedDivision.getNombre());
        List<String> savedProgramas = new ArrayList<>();
        for (ProgramaEducativo prog : savedDivision.getProgramasEducativos()) {
            savedProgramas.add(prog.getPrograma());
        }
        savedDto.setProgramasEducativos(savedProgramas);
        return savedDto;
    }

    // Endpoint para editar un programa educativo
    public ProgramaEducativoDto updateProgramaEducativo(Long programaId, ProgramaEducativoDto programaDto) {
        Optional<Division> divisionOptional = divisionRepository.findAll().stream()
                .filter(division -> division.getProgramasEducativos().stream()
                        .anyMatch(prog -> prog.getId().equals(programaId)))
                .findFirst();

        if (divisionOptional.isPresent()) {
            Division division = divisionOptional.get();
            ProgramaEducativo programaToUpdate = division.getProgramasEducativos().stream()
                    .filter(prog -> prog.getId().equals(programaId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Programa Educativo no encontrado"));

            programaToUpdate.setPrograma(programaDto.getPrograma());
            divisionRepository.save(division);

            ProgramaEducativoDto updatedDto = new ProgramaEducativoDto();
            updatedDto.setId(programaToUpdate.getId());
            updatedDto.setPrograma(programaToUpdate.getPrograma());
            return updatedDto;
        } else {
            throw new RuntimeException("División no encontrada para el Programa Educativo");
        }
    }

    // Endpoint para activar/desactivar una división
    public Division updateDivisionStatus(Long id, boolean activo) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("División no encontrada"));

        division.setActivo(activo);

        if (division.getProgramasEducativos() != null) {
            for (ProgramaEducativo programa : division.getProgramasEducativos()) {
                programa.setActivo(activo);
            }
        }
        return divisionRepository.save(division);
    }

}
