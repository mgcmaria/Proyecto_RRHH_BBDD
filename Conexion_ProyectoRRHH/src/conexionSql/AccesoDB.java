package conexionSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Calendar;
import java.util.Scanner;

public class AccesoDB {
	
	static Scanner lector = new Scanner(System.in);

	public static Connection conexion() {
		
		Connection con = null;

		// Paso 1: Cargar el driver
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//return null;
		}

		System.out.println("Se ha cargado el Driver de Oracle");

		// Paso 2: Establecer conexion con la base de datos

		String cadenaConexion = "jdbc:oracle:thin:@10.2.0.11:1521:GESTION";
		String user = "RAPIDSITE";
		String pass = "sixtina";

		try {
			con = DriverManager.getConnection(cadenaConexion, user, pass);
		} catch (SQLException e) {
			System.out.println("No se ha podido establecer la conexion con la BD");
			System.out.println(e.getMessage());
			//return null;
		}
		
		System.out.println("Se ha establecido la conexion con la Base de datos");
		
		return  con;
		
	}

	public static void cerrarConexion(Connection conexion) {
	
		try {
			conexion.close();
		} catch (SQLException e) {
			System.out.println("No se ha podido cerrar la conexion con la BD");
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Se ha cerrado la base de datos");
		
	}

	public static void consultarProcesos(Connection conexion) {
	
		try {
			
			Statement sentencia = conexion.createStatement(); // Creamos sentencia con Statement
			//Consulta SQL con resulset
			ResultSet rs = sentencia.executeQuery("SELECT * FROM RS_PROCESOS_SELECCION"); 
			
			while (rs.next()) { //Mientras haya registros imprimimos
				
				Consola.consultaProcesos(rs);
				
				}
			} catch (SQLException e) {
				System.out.println("Error al realizar el listado de productos");
				System.out.println(e.getMessage());
			}
	}

	public static void insertarProceso(Connection conexion) {
		
		try {
			//Almacenamos en un Strong la Sentencia SQL
			String sql = "INSERT INTO RS_PROCESOS_SELECCION (NOMBRE, CLIENTE, RESPONSABLE, "
					+ "DIRECCION, DEPARTAMENTO, ESTADOPROCESO, DESCRIPCIONTRABAJO) " 
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			//Leemos por teclado los valores del nuevo PROCESO y los guardamos en variables
			System.out.println("Introduce el nombre del nuevo proceso");
			String nombre_proceso = lector.nextLine();
			System.out.println("Introduce el cliente de la solicitud del proceso: ");
			String cliente_proceso = lector.nextLine();
			System.out.println("Introduce responsable del proceso: ");
			String responsable_proceso = lector.nextLine();
			System.out.println("Introduce la Dirección: ");
			String direccion_proceso = lector.nextLine();
			System.out.println("Introduce el departamento: ");
			String dep_proceso = lector.nextLine();
			System.out.println("Introduce el estado del proceso: ");
			String estado_proceso = lector.nextLine();
			System.out.println("Introduce la descripción del proceso: ");
			String descripcion_proceso = lector.nextLine();
			
			/*
			 * //Vamos a obtener la fecha del día de la insercción del Proceso Calendar c =
			 * Calendar.getInstance();
			 * 
			 * int dia = c.get(Calendar.DAY_OF_MONTH); int mes = c.get(Calendar.MONTH); int
			 * año = c.get(Calendar.YEAR);
			 * 
			 * String fecha = dia+"/"+(mes+1)+"/"+año;
			 */
			
			//Con PreparedStatement recogemos los valores introducidos			
			PreparedStatement sentencia;
			sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, nombre_proceso);
			sentencia.setString(2, cliente_proceso);
			sentencia.setString(3, responsable_proceso);
			sentencia.setString(4, direccion_proceso);
			sentencia.setString(5, dep_proceso);
			//sentencia.setString(6, fecha);
			sentencia.setString(6, estado_proceso);
			sentencia.setString(7, descripcion_proceso);
			
			int afectados = sentencia.executeUpdate(); //Ejecutamos la inserción
			
			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);
			
		} catch (SQLException e) {
			System.out.println("Error al añadir nuevo proceso");
			System.out.println(e.getMessage());
		}
	}

	public static void borrarProceso(Connection conexion) {
		
		try {
		
			//Preguntamos al usuario que proceso quiere eliminar
			System.out.println("Introduce el numero de proceso que desea eliminar");
			int numProceso = lector.nextInt();
			
			//Sentencia SQL
			String sql = "DELETE FROM rs_procesos_seleccion WHERE idproceso=?";
			
			//Con PreparedStatement recogemos los valores introducidos			
			PreparedStatement sentencia;
			sentencia = conexion.prepareStatement(sql);
			
			sentencia.setInt(1, numProceso);
						
			int afectados = sentencia.executeUpdate(); //Ejecutamos el borrado
			
			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);
			
		} catch (SQLException e) {
			System.out.println("Error al eliminar proceso");
			System.out.println(e.getMessage());
		}
	}

	public static void actualizarProceso(Connection conexion) {
		
		System.out.println("¿Que proceso deseas modificar?");
		int proceso = lector.nextInt();
		
		Statement sentencia;
		
		try {
			sentencia = conexion.createStatement();
			//Consulta SQL con Resulset
			ResultSet rs = sentencia.executeQuery("SELECT * FROM RS_PROCESOS_SELECCION "
					+ "WHERE idproceso LIKE '%" + proceso +"%'"); 
			
			while (rs.next()) {
				
				System.out.println("Campos actuales del proceso número "+ proceso +" a modificar:");
				Consola.modificarProceso(rs);
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		int opcion = lector.nextInt();
		lector.nextLine();//Recogemos retorno de carro
		
		if (opcion == 1) {
			actualizarNombre(conexion, proceso);
		}		
		if (opcion == 2) {
			actualizarCliente(conexion, proceso);
		}
		if (opcion == 3) {
			actualizarResponsable(conexion, proceso);
		}
		if (opcion == 4) {
			actualizarDireccion(conexion, proceso);
		}
		if (opcion == 5) {
			actualizarDepartamento(conexion, proceso);
		}
		if (opcion == 6) {
			actualizarEstado(conexion, proceso);
		}
		if (opcion == 7) {
			actualizarDescripcion(conexion, proceso);
		}
	}

	private static void actualizarDescripcion(Connection conexion, int proceso) {
		
		System.out.println("Indica la nueva descripcion: ");
		String nombre_nuevo = lector.nextLine();

		System.out.println(nombre_nuevo);

		// Sentencia SQL
		String sql = "UPDATE rs_procesos_seleccion SET descripciontrabajo=?"
				+ " WHERE idproceso LIKE '%" + proceso + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, nombre_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void actualizarEstado(Connection conexion, int proceso) {
		
		System.out.println("Indica el estado del proceso actual: ");
		String nombre_nuevo = lector.nextLine();

		System.out.println(nombre_nuevo);

		// Sentencia SQL
		String sql = "UPDATE rs_procesos_seleccion SET estadoproceso=? "
				+ "WHERE idproceso LIKE '%" + proceso + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, nombre_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void actualizarDepartamento(Connection conexion, int proceso) {
		
		System.out.println("Nuevo departamento: ");
		String nombre_nuevo = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_procesos_seleccion SET departamento=? "
				+ "WHERE idproceso LIKE '%" + proceso + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, nombre_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void actualizarDireccion(Connection conexion, int proceso) {
		
		System.out.println("Nueva dirección: ");
		String nombre_nuevo = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_procesos_seleccion SET direccion=? "
				+ "WHERE idproceso LIKE '%" + proceso + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, nombre_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void actualizarResponsable(Connection conexion, int proceso) {
		
		System.out.println("Nuevo responsable: ");
		String nombre_nuevo = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_procesos_seleccion SET responsable=? "
				+ "WHERE idproceso LIKE '%" + proceso + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, nombre_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void actualizarCliente(Connection conexion, int proceso) {
		
		System.out.println("Nuevo nombre del cliente: ");
		String nombre_nuevo = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_procesos_seleccion SET cliente=? "
				+ "WHERE idproceso LIKE '%" + proceso + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, nombre_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void actualizarNombre(Connection conexion, int proceso) {
		
		System.out.println("Nuevo nombre del proceso: ");
		String nombre_nuevo = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_procesos_seleccion SET nombre=? "
				+ "WHERE idproceso LIKE '%" + proceso + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, nombre_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void consultaCandidatos(Connection conexion) {
		
		try {
			
			Statement sentencia = conexion.createStatement(); // Creamos sentencia con Statement
			ResultSet rs = sentencia.executeQuery("SELECT * FROM RS_CANDIDATOS"); //Consulta SQL 
			
			while (rs.next()) { //Mientras haya registros imprimimos
				
				Consola.imprimirCandidatos(rs);
				
				}
			} catch (SQLException e) {
				System.out.println("Error al realizar consulta de candidatos");
				System.out.println(e.getMessage());
			}
	}

	public static void insertarCandidato(Connection conexion) {
		
		//Leemos por teclado los valores del nuevo CANDIDATO y los guardamos en variables
		System.out.println("Introduce el nombre del candidato");
		String nombre_candidato = lector.nextLine();
		System.out.println("Introduce los apellidos del candidato: ");
		String apellidos_candidato = lector.nextLine();
		System.out.println("Introduce el email: ");
		String email_candidato = lector.nextLine();
		System.out.println("Introduce el telefóno: ");
		String telefono_candidato = lector.nextLine();
		System.out.println("Introduce la fuente de origen de solicitud: ");
		String fuente_candidato = lector.nextLine();
		System.out.println("Introduce el perfil del candidato: ");
		String perfil_candidato = lector.nextLine();
		System.out.println("Observaciones: ");
		String observaciones_candidato = lector.nextLine();		 
		
		//Sentencia SQL
		String sql = "INSERT INTO RS_CANDIDATOS (NOMBRE, APELLIDOS, EMAIL, TELEFONO, "
				+ "FUENTE, PERFIL, OBSERVACIONES) VALUES (?, ?, ?, ?, ?, ?, ?)";		
				
		//Con PreparedStatement recogemos los valores introducidos			
		PreparedStatement sentencia;
		int afectados;
		
		try {				
			sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, nombre_candidato);
			sentencia.setString(2, apellidos_candidato);
			sentencia.setString(3, email_candidato);
			sentencia.setString(4, telefono_candidato);
			sentencia.setString(5, fuente_candidato);
			sentencia.setString(6, perfil_candidato);
			sentencia.setString(7, observaciones_candidato);
						
			afectados = sentencia.executeUpdate(); //Ejecutamos la inserción
			
			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);			
		} catch (SQLException e) {
			System.out.println("Error al añadir nuevo candidato");
			System.out.println(e.getMessage());
		}		
	}

	public static void borrarCandidato(Connection conexion) {
		
		try {
			
			//Preguntamos al usuario que proceso quiere eliminar
			System.out.println("Introduce el id del candidato que desea eliminar");
			int idCandidato = lector.nextInt();
			
			//Sentencia SQL
			String sql = "DELETE FROM rs_candidatos WHERE idcandidato=?";
			
			//Con PreparedStatement recogemos los valores introducidos			
			PreparedStatement sentencia;
			sentencia = conexion.prepareStatement(sql);
			
			sentencia.setInt(1, idCandidato);
						
			int afectados = sentencia.executeUpdate(); //Ejecutamos el borrado
			
			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);
			
		} catch (SQLException e) {
			System.out.println("Error al eliminar proceso");
			System.out.println(e.getMessage());
		}
		
	}

	public static void actualizarCandidato(Connection conexion) {
		
		System.out.println("¿Que candidato deseas modificar?");
		int candidato = lector.nextInt();
		
		Statement sentencia;
		try {
			sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT * FROM RS_CANDIDATOS WHERE"
					+ " idCANDIDATO LIKE '%" + candidato +"%'"); //Consulta SQL
			
			while (rs.next()) {
		
				System.out.println("Campos actuales del candidato número "
				+ candidato +" a modificar:");				
				Consola.modificarCandidato(rs);
				
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		int opcion = lector.nextInt();
		lector.nextLine();//Recogemos retorno de carro
		
		if (opcion == 1) {
			actualizarNombreCandidato(conexion, candidato);
		}		
		if (opcion == 2) {
			actualizarApellidosCandidato(conexion, candidato);
		}
		if (opcion == 3) {
			actualizarEmailCandidato(conexion, candidato);
		}
		if (opcion == 4) {
			actualizarTelefonoCandidato(conexion, candidato);
		}
		if (opcion == 5) {
			actualizarFuenteCandidato(conexion, candidato);
		}
		if (opcion == 6) {
			actualizarPerfilCandidato(conexion, candidato);
		}
		if (opcion == 7) {
			actualizarObservacionesCandidato(conexion, candidato);
		}		
	}

	private static void actualizarObservacionesCandidato(Connection conexion, int candidato) {

		System.out.println("Modifique las observaciones: ");
		String obser = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_candidatos SET observaciones=? "
				+ "WHERE idcandidato LIKE '%" + candidato + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, obser);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static void actualizarPerfilCandidato(Connection conexion, int candidato) {
		
		System.out.println("Nuevo perfil del candidato: ");
		String perfil = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_candidatos SET perfil=? "
				+ "WHERE idcandidato LIKE '%" + candidato + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, perfil);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void actualizarFuenteCandidato(Connection conexion, int candidato) {

		System.out.println("Indique la fuente correcta: ");
		String fuente_nueva = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_candidatos SET fuente=? "
				+ "WHERE idcandidato LIKE '%" + candidato + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, fuente_nueva);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void actualizarTelefonoCandidato(Connection conexion, int candidato) {

		System.out.println("Nuevos telefono del candidato: ");
		String telefono = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_candidatos SET telefono=? "
				+ "WHERE idcandidato LIKE '%" + candidato + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, telefono);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static void actualizarEmailCandidato(Connection conexion, int candidato) {

		System.out.println("Nuevo email del candidato: ");
		String email_nuevo = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_candidatos SET email=? "
				+ "WHERE idcandidato LIKE '%" + candidato + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, email_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static void actualizarApellidosCandidato(Connection conexion, int candidato) {
		
		System.out.println("Nuevos apellidos del candidato: ");
		String apellidos_nuevo = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_candidatos SET apellidos=? "
				+ "WHERE idcandidato LIKE '%" + candidato + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, apellidos_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

	private static void actualizarNombreCandidato(Connection conexion, int candidato) {

		System.out.println("Nuevo nombre del candidato: ");
		String nombre_nuevo = lector.nextLine();

		// Sentencia SQL
		String sql = "UPDATE rs_candidatos SET nombre=? "
				+ "WHERE idcandidato LIKE '%" + candidato + "%'";

		// Con PreparedStatement ejecutamos la sentencia SQL con los valores introducidos
		PreparedStatement sentencia1;
		int afectados;

		try { 
			sentencia1 = conexion.prepareStatement(sql);
			sentencia1.setString(1, nombre_nuevo);

			afectados = sentencia1.executeUpdate(); // Ejecutamos

			System.out.println("Sentencia SQL ejecutada con éxito");
			System.out.println("Registros afectados: " + afectados);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
	}	
}
