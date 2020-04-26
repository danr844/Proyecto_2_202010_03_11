package view;

import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    public void printMenu()
		{
			System.out.println("1. Carga de datos.");
			System.out.println("2. Comparendos por gravedad.");
			System.out.println("3. Comparendos en dos fechas con Localidad.");
			System.out.println("4. Comparendos en un mes según día definido.");
			System.out.println("5. Comparendos segun cercanía.");
			System.out.println("6. Comparendos segun medio de deteccion, clase de vehiculo, tipo de vehiculo y Localidad.");
			System.out.println("7. Comparendos segun rango de latitud.");
			System.out.println("8. Comparendos segun dia que desee ver.");
			System.out.println("9. Comparendos segun procesamiento oficial de policia.");
			System.out.println("10. Exit.");

			System.out.println("------------------------------------------------------------------------");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
			System.out.println("------------------------------------------------------------------------");

		}
		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			// TODO implementar
			for( int i = 0; i<modelo.dartamanioRedBLack(); i++){
				System.out.println("");
				
			}
		}
}
