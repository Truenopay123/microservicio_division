package mx.edu.uteq.idgs13.microservicio_division.dto;

import lombok.Data;

@Data
public class ProfesorDto {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private boolean activo;
    private Long divisionId;
}