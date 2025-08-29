package org.kaven.A4_1_2024458_ToDoList.persistence.crud;

import org.kaven.A4_1_2024458_ToDoList.persistence.entity.Tareas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareasCrud extends JpaRepository<Tareas, Integer>{

}
