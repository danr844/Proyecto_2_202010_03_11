package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import model.data_structures.Comparendo;
import model.data_structures.Queue;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() throws ParseException 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;

		while( !fin ){
			view.printMenu();
			int option = lector.nextInt();
			switch(option)
			{
			case 1:
				Comparendo mayorID = null;

				view.printMessage("------------------------------------------------------------------------\n Se esta cargando la informacion \n------------------------------------------------------------------------");
				try 
				{
					mayorID = modelo.cargarInfo();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				view.printMessage("Se cargaron"+ modelo.darNumeroElementosLinear());
				if(mayorID!=null){
					view.printMessage("El comparendo con mayor ID es:" + mayorID.darID()+ "\n "+ mayorID.darFecha() + "\n "+ mayorID.darInfraccion()+ "\n "+ mayorID.darClaseVehiculo()+  "\n "+ mayorID.darTipoServicio()+  "\n "+ mayorID.darLocalidad() );
					view.printMessage("El numero de comparendos cargados es de:"+ modelo.dartamanioRedBLack());
				}
				break;
			case 2:
				view.printMessage("------------------------------------------------------------------------\n Ingrese el numero de comparendos que desea ver: \n------------------------------------------------------------------------");
				int M = lector.nextInt();
				Iterable<Comparendo> queue = modelo.darComparendosGravedad(M);
				Iterator<Comparendo>iter = queue.iterator();
				while(iter.hasNext()){
					Comparendo grave = iter.next();
					view.printMessage("ID: "+grave.darID() +"  Infraccion:  "+ grave.darInfraccion()+"    Clase Vehiculo: "+ grave.darClaseVehiculo()+"    Tipo Servicio: "+grave.darTipoServicio()+"  Fecha:   "+grave.darFecha());
				}
				break;

			case 3:
				view.printMessage("------------------------------------------------------------------------\n Ingrese las fechas que desea usar en el formato yyyy-MM-dd: \n------------------------------------------------------------------------");
				String fechaS2 = lector.next();
				SimpleDateFormat parser2 = new SimpleDateFormat("yyyy-MM-dd");
				Date fecha2 = parser2.parse(fechaS2);
				String fechaS3 = lector.next();
				SimpleDateFormat parser3 = new SimpleDateFormat("yyyy-MM-dd");
				Date fecha3 = parser3.parse(fechaS3);
				view.printMessage("------------------------------------------------------------------------\n Ingrese la localidad que desea buscar: \n------------------------------------------------------------------------");
				String localidad = lector.next();
				Queue<Comparendo>queue1 = modelo.darComparendosDosfechas(fecha2, fecha3);
				int x =0;
				while(x<queue1.size()){
					Comparendo comConFecha = queue1.dequeue();
					if(localidad.equalsIgnoreCase(comConFecha.darLocalidad()))
						view.printMessage("ID: "+comConFecha.darID() +"  Infraccion:  "+ comConFecha.darInfraccion()+"    Clase Vehiculo: "+ comConFecha.darClaseVehiculo()+"    Tipo Servicio: "+comConFecha.darTipoServicio()+"  Fecha:   "+comConFecha.darFecha());
					x++;
				}

				break;
			case 4:
				view.printMessage("------------------------------------------------------------------------\n Ingrese el numero del mes que desea buscar: \n------------------------------------------------------------------------");
				int mes = lector.nextInt();
				view.printMessage("------------------------------------------------------------------------\n Ingrese el dia que desea buscar: \n------------------------------------------------------------------------");
				String dia = lector.next();
				Iterable<String>queue3=modelo.darComparendosMesDia(mes, dia);
				Iterator<String>iter3= queue3.iterator();
				while(iter3.hasNext())
				{
					String fechaBuscada = iter3.next();
					Iterable<Comparendo>queue4=modelo.darDatosSeparateChaining().darListaEncadenadaCompleta(fechaBuscada).getAll(fechaBuscada);
					Iterator<Comparendo>iter4= queue4.iterator();
					while(iter4.hasNext())
					{
						Comparendo buscado = iter4.next();
						view.printMessage("ID: "+buscado.darID() +"  Infraccion:  "+ buscado.darInfraccion()+"    Clase Vehiculo: "+ buscado.darClaseVehiculo()+"    Tipo Servicio: "+buscado.darTipoServicio()+"  Fecha:   "+buscado.darFecha());
					}
				}




				break;
			case 5:
				//				view.printMessage("------------------------------------------------------------------------\n Fecha, Clase Vehiculo e Infraccion en el formato: FechaClaseVehiculoInfraccion: \n------------------------------------------------------------------------");
				//				String fechavehiculoInfraccion = lector.next();
				//				ArrayList<Comparendo> res = modelo.darComparendosFeClaInfSeparateChaning(fechavehiculoInfraccion);
				//				if(res.get(0)==null)
				//					view.printMessage(" no existen comparendos con la localidad dada.");
				//				else
				//				{
				//					for(Comparendo e: res){
				//						view.printMessage("El primer Comparendo es: "+ e.darID() +" " + e.darFecha()+ " "+e.darInfraccion()+ " "+ " "+ e.darClaseVehiculo()+" "+e.darTipoServicio()+" "+e.darLocalidad()+ "\n---------------------------");
				//
				//					}
				//				}

				break;
				//			case 6:
				//				view.printMessage("------------------------------------------------------------------------\n Ingrese la infraccion que desea buscar: \n------------------------------------------------------------------------");
				//				String ifraccion2 = lector.next();
				//				ArregloDinamico<Comparendo> nuevo2 = modelo.comparendosConInfraccion(ifraccion2);
				//				int w =0;
				//				while(w<nuevo2.darTamano()){
				//					view.printMessage("------------------------------------------------------------------------\n"+nuevo2.darElemento(w).darID()+" " +nuevo2.darElemento(w).darFecha()+" " +nuevo2.darElemento(w).darInfraccion()+" " +nuevo2.darElemento(w).darClaseVehiculo()+" " +nuevo2.darElemento(w).darTipoServicio()+" " +nuevo2.darElemento(w).darLocalidad()+" " +"\n------------------------------------------------------------------------");
				//					w++;
				//				}
				//				view.printMessage("el numero total de comparendos para esta fecha es: "+ nuevo2.darTamano()  );
				//
				//
				//				break;
				//
				//			case 7:
				//				String[]tabla = modelo.darComparendosInfraccionesPublico().split(";");
				//				int y =0;
				//				while(y<tabla.length){
				//					view.printMessage(tabla[y]);
				//					y++;
				//				}
				//				break;
				//
			case 8: 
				view.printMessage("------------------------------------------------------------------------\n Cerrando el programa: \n------------------------------------------------------------------------");
				lector.close();
				fin = true;

			default: 
				view.printMessage("--------------------------------------------------------------- \n Opcion Invalida !! \n---------------------------------------------------------------");
				break;
			}
		}
	}
}
		





