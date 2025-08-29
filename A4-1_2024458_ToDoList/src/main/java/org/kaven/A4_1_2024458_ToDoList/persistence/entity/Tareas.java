package org.kaven.A4_1_2024458_ToDoList.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity(name = "Tareas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tareas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idTarea;
    private String materia;
    private String descripcion;
    private LocalDate fechaEntrega;
    private String estado;
}
