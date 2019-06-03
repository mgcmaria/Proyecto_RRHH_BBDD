package conexionSql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Consola {
	
	public static Consola pintarMenu() {
		
		System.out.println();
		System.out.println("GESTION DE PROCESOS RRHH");
		System.out.println("--------------------------------------");
		System.out.println("1. Consulta  de PROCESOS");
		System.out.println("2. Insertar PROCESO");
		System.out.println("3. Borrar PROCESO");
		System.out.println("4. Actualizar PROCESO");
		System.out.println("--------------------------------------");
		System.out.println("GESTION DE CANDIDATOS RRHH");
		System.out.println("--------------------------------------");
		System.out.println("5. Consulta  de CANDIDATOS");
		System.out.println("6. Insertar CANDIDATO");
		System.out.println("7. Borrar CANDIDATO");
		System.out.println("8. Actualizar CANDIDATO");
		System.out.println("--------------------------------------");
		System.out.println("9. TERMINAR PROGRAMA");
		System.out.println("--------------------------------------");
		System.out.println("Que opcion eliges?");
		
		return null;
	}

	public static void imprimirCandidatos(ResultSet rs) {

		try {
			System.out.print(rs.getString("IDCANDIDATO"));
			System.out.print(" - ");
			System.out.print(rs.getString("NOMBRE"));
			System.out.print(" - ");
			System.out.print(rs.getString("APELLIDOS"));
			System.out.print(" - ");
			System.out.print(rs.getString("EMAIL"));
			System.out.print(" - ");
			System.out.print(rs.getString("TELEFONO"));
			System.out.print(" - ");
			System.out.print(rs.getString("FUENTE"));
			System.out.print(" - ");
			System.out.print(rs.getString("PERFIL"));
			System.out.print(" - ");
			System.out.print(rs.getString("OBSERVACIONES"));
			System.out.print(" - ");
			System.out.print(rs.getString("CV"));			
			System.out.println(); 
		} catch (SQLException e) {
			System.out.println("Error al realizar consulta de candidatos");
			System.out.println(e.getMessage());
		}		
	}

	public static void consultaProcesos(ResultSet rs) {
		
		try {
			System.out.print(rs.getString("IDPROCESO"));
			System.out.print(" - ");
			System.out.print(rs.getString("NOMBRE"));
			System.out.print(" - ");
			System.out.print(rs.getString("CLIENTE"));
			System.out.print(" - ");
			System.out.print(rs.getString("RESPONSABLE"));
			System.out.print(" - ");
			System.out.print(rs.getString("DIRECCION"));
			System.out.print(" - ");
			System.out.print(rs.getString("DEPARTAMENTO"));
			System.out.print(" - ");
			System.out.print(rs.getDate("FECHAENTRADA"));
			System.out.print(" - ");
			System.out.print(rs.getString("ESTADOPROCESO"));
			System.out.print(" - ");
			System.out.print(rs.getString("DESCRIPCIONTRABAJO"));
			System.out.print(" - ");
			System.out.print(rs.getString("DOCUMENTACIONPROCESO"));
			System.out.print(" - ");				
			System.out.println(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public static void modificarProceso(ResultSet rs) {

		try {
			System.out.println("Fecha de entrada: "+rs.getDate("FECHAENTRADA"));
			System.out.println("---------------------------------------------------------------------- ");
			System.out.println("OPCION 1 || Nombre: "+rs.getString("NOMBRE"));
			System.out.println("OPCION 2 || Cliente: "+rs.getString("CLIENTE"));
			System.out.println("OPCION 3 || Responsable: "+rs.getString("RESPONSABLE"));
			System.out.println("OPCION 4 || Dirección: "+rs.getString("DIRECCION"));
			System.out.println("OPCION 5 || Departamento: "+rs.getString("DEPARTAMENTO"));				
			System.out.println("OPCION 6 || Estado del proceso: "+rs.getString("ESTADOPROCESO"));
			System.out.println("OPCION 7 || Descripción: "+ rs.getString("DESCRIPCIONTRABAJO"));
			System.out.println("OPCION 8 || SALIR SIN MODIFICAR");
			System.out.println("---------------------------------------------------------------------- ");
			System.out.println("¿Que campo deseas modificar?"); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public static void modificarCandidato(ResultSet rs) {

		System.out.println("---------------------------------------------------------------------- ");
		try {
			System.out.println("OPCION 1 || Nombre: "+rs.getString("NOMBRE"));
			System.out.println("OPCION 2 || Apellidos: "+rs.getString("APELLIDOS"));
			System.out.println("OPCION 3 || e-Mail: "+rs.getString("EMAIL"));
			System.out.println("OPCION 4 || Telefono: "+rs.getString("TELEFONO"));
			System.out.println("OPCION 5 || Fuente: "+rs.getString("FUENTE"));				
			System.out.println("OPCION 6 || Perfil: "+rs.getString("PERFIL"));
			System.out.println("OPCION 7 || Observaciones: "+ rs.getString("OBSERVACIONES"));
			System.out.println("OPCION 8 || SALIR SIN MODIFICAR");
			System.out.println("---------------------------------------------------------------------- ");
			System.out.println("¿Que campo deseas modificar?"); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
