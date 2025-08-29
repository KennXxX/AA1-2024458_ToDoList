package org.kaven.A4_1_2024458_ToDoList;

import org.kaven.A4_1_2024458_ToDoList.dominio.service.ITareasService;
import org.kaven.A4_1_2024458_ToDoList.persistence.entity.Tareas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ToDoListApplication implements CommandLineRunner {

	@Autowired
	private ITareasService tareasService;

	private static final Logger logger = LoggerFactory.getLogger(ToDoListApplication.class);
	String sl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("INICIO DE LA APLICACIÓN DE TAREAS");
		SpringApplication.run(ToDoListApplication.class, args);
		logger.info("FIN DE LA APLICACIÓN DE TAREAS");
	}

	@Override
	public void run(String... args) {
		ejecutarAppTareas();
	}

	private void ejecutarAppTareas() {
		logger.info("++++++ APLICACIÓN DE REGISTRO DE TAREAS ++++++");
		var salir = false;
		var consola = new Scanner(System.in);

		while (!salir) {
			int opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(sl);
		}
	}

	private int mostrarMenu(Scanner consola) {
		logger.info("""
                \n*** Menú de Tareas ***
                1. Listar todas las tareas.
                2. Buscar tarea por ID.
                3. Agregar nueva tarea.
                4. Modificar tarea.
                5. Eliminar tarea.
                6. Salir.
                Elija una opción: """);
		return Integer.parseInt(consola.nextLine());
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion) {
		boolean salir = false;

		switch (opcion) {
			case 1 -> {
				logger.info(sl + "*** Listado de todas las Tareas ***" + sl);
				List<Tareas> tareas = tareasService.listarTareas();
				tareas.forEach(t -> logger.info(t + sl));
			}
			case 2 -> {
				logger.info(sl + "Buscar Tarea por ID" + sl);
				logger.info("Ingrese el ID de la tarea:");
				int id = Integer.parseInt(consola.nextLine());
				Tareas tarea = tareasService.buscarTareaPorId(id);
				if (tarea != null) {
					logger.info("Tarea encontrada:" + sl + tarea + sl);
				} else {
					logger.info("Tarea no encontrada." + sl);
				}
			}
			case 3 -> {
				logger.info(sl + "Agregar nueva tarea" + sl);
				logger.info("Ingrese la materia:");
				String materia = consola.nextLine();
				logger.info("Ingrese la descripción:");
				String descripcion = consola.nextLine();
				logger.info("Ingrese la fecha de entrega (YYYY-MM-DD):");
				LocalDate fechaEntrega = LocalDate.parse(consola.nextLine());
				logger.info("Ingrese el estado (Pendiente o Completa):");
				String estado = consola.nextLine();

				Tareas nuevaTarea = new Tareas(null, materia, descripcion, fechaEntrega, estado);
				tareasService.guardarTarea(nuevaTarea);
				logger.info("Tarea agregada:" + sl + nuevaTarea + sl);
			}
			case 4 -> {
				logger.info(sl + "Modificar Tarea" + sl);
				logger.info("Ingrese el ID de la tarea a modificar:");
				int id = Integer.parseInt(consola.nextLine());
				Tareas tarea = tareasService.buscarTareaPorId(id);
				if (tarea != null) {
					logger.info("Ingrese la nueva materia:");
					tarea.setMateria(consola.nextLine());
					logger.info("Ingrese la nueva descripción:");
					tarea.setDescripcion(consola.nextLine());
					logger.info("Ingrese la nueva fecha de entrega (YYYY-MM-DD):");
					tarea.setFechaEntrega(LocalDate.parse(consola.nextLine()));
					logger.info("Ingrese el nuevo estado (Pendiente o Completa):");
					tarea.setEstado(consola.nextLine());

					tareasService.guardarTarea(tarea);
					logger.info("Tarea modificada:" + sl + tarea + sl);
				} else {
					logger.info("Tarea no encontrada." + sl);
				}
			}
			case 5 -> {
				logger.info(sl + "Eliminar Tarea" + sl);
				logger.info("Ingrese el ID de la tarea a eliminar:");
				int id = Integer.parseInt(consola.nextLine());
				Tareas tarea = tareasService.buscarTareaPorId(id);
				if (tarea != null) {
					tareasService.eliminarTarea(tarea);
					logger.info("Tarea eliminada:" + sl + tarea + sl);
				} else {
					logger.info("Tarea no encontrada." + sl);
				}
			}
			case 6 -> {
				logger.info("Saliendo de la aplicación..." + sl);
				salir = true;
			}
			default -> logger.info("Opción no válida. Intente de nuevo.");
		}

		return salir;
	}
}