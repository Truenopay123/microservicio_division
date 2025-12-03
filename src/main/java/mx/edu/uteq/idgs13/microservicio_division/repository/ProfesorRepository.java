package mx.edu.uteq.idgs13.microservicio_division.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import mx.edu.uteq.idgs13.microservicio_division.entity.Profesor;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
	List<Profesor> findByDivision_Id(Long divisionId);
}