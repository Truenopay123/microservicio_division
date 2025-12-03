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
import mx.edu.uteq.idgs13.microservicio_division.repository.ProfesorAsignadoRepository;
import mx.edu.uteq.idgs13.microservicio_division.client.UsuariosClient;
import mx.edu.uteq.idgs13.microservicio_division.entity.ProfesorAsignado;

@Service
public class DivisionService {

    @Autowired
    private DivisionRepository divisionRepository;
    @Autowired
    private ProfesorAsignadoRepository profesorAsignadoRepository;

    @Autowired
    private UsuariosClient usuariosClient;

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
            // Profesores asignados a la división
            var asignaciones = profesorAsignadoRepository.findByDivision_Id(division.getId());
            List<Long> ids = new ArrayList<>();
            for (var asg : asignaciones) ids.add(asg.getProfesorId());
            List<String> nombresProfes = new ArrayList<>();
            try {
                List<UsuariosClient.ProfesorView> all = usuariosClient.getProfesores();
                for (var p : all) {
                    if (ids.contains(p.id)) nombresProfes.add(p.nombre);
                }
            } catch (Exception e) {
                // si falla, devolver lista vacía
            }
            dto.setProfesores(nombresProfes);
            resultado.add(dto);
        }
        return resultado;
    }
  
    public DivisionToViewListDto addDivision(DivisionToViewListDto divisionDto) {
        Division division = new Division();
        division.setNombre(divisionDto.getNombre());
        division.setActivo(true);
        List<ProgramaEducativo> programas = new ArrayList<>();
        if (divisionDto.getProgramasEducativos() != null) {
            for (String progName : divisionDto.getProgramasEducativos()) {
                ProgramaEducativo prog = new ProgramaEducativo();
                prog.setPrograma(progName);
                prog.setActivo(true);
                prog.setDivision(division);
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
  
    public Division findById(Long id) throws Exception {
        return divisionRepository.findById(id)
            .orElseThrow(() -> new Exception("División no encontrada con id: " + id));
    }

    public Division updateDivision(Long id, String nombre, boolean activo) throws Exception {
        Division division = findById(id);
        division.setNombre(nombre);
        division.setActivo(activo);
        return divisionRepository.save(division);
    }

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

    public void deleteDivision(Long id) {
        if (!divisionRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la división con id: " + id);
        }
        divisionRepository.deleteById(id);
    }

    public boolean assignDivisionToProfesor(Long divisionId, Long profesorId) {
        var divisionOpt = divisionRepository.findById(divisionId);
        if (divisionOpt.isEmpty()) return false;
        // Validar existencia remota
        try {
            boolean existe = usuariosClient.getProfesores().stream().anyMatch(p -> profesorId.equals(p.id));
            if (!existe) return false;
        } catch (Exception e) {
            return false;
        }
        ProfesorAsignado asg = new ProfesorAsignado();
        asg.setDivision(divisionOpt.get());
        asg.setProfesorId(profesorId);
        profesorAsignadoRepository.save(asg);
        return true;
    }
}
