package mx.edu.uteq.idgs13.microservicio_division.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import mx.edu.uteq.idgs13.microservicio_division.entity.ProfesorAsignado;

public interface ProfesorAsignadoRepository extends JpaRepository<ProfesorAsignado, Long> {
    List<ProfesorAsignado> findByDivision_Id(Long divisionId);
}
