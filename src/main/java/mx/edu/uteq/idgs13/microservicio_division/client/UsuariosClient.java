package mx.edu.uteq.idgs13.microservicio_division.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "microservicio-administrador")
public interface UsuariosClient {

    @GetMapping("/api/usuarios/profesores")
    List<ProfesorView> getProfesores();

    class ProfesorView {
        public Long id;
        public String nombre;
        public String email;
    }
}
