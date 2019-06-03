package conexionSql;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner lector; //Creamos objeto lector para lectura por teclado
		
		//Abrimos la conexion con la BBDD
		Connection conexion = AccesoDB.conexion();				
		if (conexion == null)
		return;

		lector = new Scanner(System.in); //Creamos objeto lector para lectura por teclado

		int opc; //Variable para opciones del menu

		do { // do...while mientras la opcion no sea 9

			Consola.pintarMenu(); // Metodo para mostrar el menu de opciones
			opc = lector.nextInt(); // Leemos opciOn
			lector.nextLine(); // Recogemos el retorno de carro.
			System.out.println();

			switch (opc) {
			
				case 1:
					AccesoDB.consultarProcesos(conexion); break;
				case 2:
					AccesoDB.insertarProceso(conexion); break;
				case 3:
					AccesoDB.borrarProceso(conexion); break;
				case 4:
					AccesoDB.actualizarProceso(conexion); break;
				case 5:
					AccesoDB.consultaCandidatos(conexion); break;
				case 6:
					AccesoDB.insertarCandidato(conexion); break;
				case 7:
					AccesoDB.borrarCandidato(conexion); break;
				case 8:
					AccesoDB.actualizarCandidato(conexion); break;
				case 9:
					System.out.println("Hasta pronto"); break;
				default:
					System.out.println("Opcion incorrecta");
			}

		} while (opc != 9);

		AccesoDB.cerrarConexion(conexion); // Pasamos como argumento la conexion a cerrar.
		lector.close(); // Cerramos lector
		
	}

}


