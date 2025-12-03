package mx.edu.uteq.idgs13.microservicio_division.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "profesores_divisiones")
public class ProfesorAsignado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    // ID del profesor en microservicio administrador (tabla usuarios)
    private Long profesorId;
}
