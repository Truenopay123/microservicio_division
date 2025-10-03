package mx.edu.uteq.idgs13.microservicio_division.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import mx.edu.uteq.idgs13.microservicio_division.entity.Division;

public interface DivisionRepository extends JpaRepository<Division, Long> {

}
