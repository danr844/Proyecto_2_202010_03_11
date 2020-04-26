package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import com.sun.glass.events.ViewEvent;

import model.data_structures.Comparendo;
import model.data_structures.ListaEncadenada;
import model.data_structures.MaxPQ;
import model.data_structures.Queue;
import model.data_structures.SeparateChainingHT;
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
				view.printMessage("Se cargaron"+ modelo.dartamanioRedBLack());
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

				view.printMessage("------------------------------------------------------------------------\n Ingrese el numero de comparendos que desea ver: \n------------------------------------------------------------------------");
				int  numImpresiones = lector.nextInt();
				MaxPQ<Comparendo> cercanos = modelo.darMasCercano(numImpresiones);
				Iterator<Comparendo> iterador = cercanos.iterator();
				if(!iterador.hasNext())
					view.printMessage(" no existen comparendos con la localidad dada.");
				else
				{
					while(iterador.hasNext()){
						Comparendo e = iterador.next();
						view.printMessage("El primer Comparendo es: "+ e.darID() +" " + e.darFecha()+ " "+e.darInfraccion()+ " "+ " "+ e.darClaseVehiculo()+" "+e.darTipoServicio()+" "+e.darLocalidad()+ " "+ e.darDistancia()+"\n---------------------------");
					}
				}

				break;

			case 6:
				view.printMessage("------------------------------------------------------------------------\n Ingrese el medio de deteccion que desea buscar: \n------------------------------------------------------------------------");
				String medio = lector.next();
				view.printMessage("------------------------------------------------------------------------\n Ingrese la clase  de vehiculo que desea buscar: \n------------------------------------------------------------------------");
				String clase = lector.next();
				if(clase.equals("Automovil")||clase.equals("Automóvil")||clase.equals("AUTOMÓVIL")||clase.equals("AUTOMOVIL")) clase ="AUTOMÃ“VIL";
				view.printMessage("------------------------------------------------------------------------\n Ingrese el tipo de vehiculo que desea buscar: \n------------------------------------------------------------------------");
				String tipo = lector.next();
				if(tipo.equals("Publico")||tipo.equals("Público")||tipo.equals("PUBLICO")||tipo.equals("PÚBLICO"))tipo= "PÃºblico";
				view.printMessage("------------------------------------------------------------------------\n Ingrese la localidad que desea buscar: \n------------------------------------------------------------------------");
				String loca = lector.next();

				Queue<Comparendo> nuevo2 = modelo.darComparendosMedioClaseTipoLoca(medio, clase, tipo, loca);
				Iterator<Comparendo> iter5 = nuevo2.iterator();
				while(iter5.hasNext()){
					Comparendo comp = iter5.next();
					view.printMessage("------------------------------------------------------------------------\n"+comp.darID()+" " +comp.darFecha()+" " +comp.darInfraccion()+" " +comp.darClaseVehiculo()+" " +comp.darTipoServicio()+" " +comp.darLocalidad()+" " + " Latitud:" + comp.darlatitud()+"    Longitud:"+comp.darLongitud()+"\n------------------------------------------------------------------------");
				}				
				break;

			case 7:
				view.printMessage("------------------------------------------------------------------------\n Ingrese la latitud minima que desea buscar: \n------------------------------------------------------------------------");
				String lo = lector.next();
				view.printMessage("------------------------------------------------------------------------\n Ingrese la latitud maxima que desea buscar: \n------------------------------------------------------------------------");
				String hi = lector.next();

				Iterable<Comparendo>queue2 = modelo.darComparendosRangoLatitud(Double.parseDouble(lo),Double.parseDouble(hi));
				Iterator<Comparendo>iter6= queue2.iterator();
				while(iter6.hasNext()){
					Comparendo comp = iter6.next();
					view.printMessage("------------------------------------------------------------------------\n"+comp.darID()+" " +comp.darFecha()+" " +comp.darInfraccion()+" " +comp.darClaseVehiculo()+" " +comp.darTipoServicio()+" " +comp.darLocalidad()+" " + " Latitud:" + comp.darlatitud()+"    Longitud:"+comp.darLongitud()+"\n------------------------------------------------------------------------");
				}	
				break;
			case 8:
				view.printMessage("------------------------------------------------------------------------\n Ingrese los dias que desea usar, cuando desee terminar, escriba *a* : \n------------------------------------------------------------------------");
				boolean fin2 = false;
				Queue<String> queue6= new Queue<String>();
				while(!fin2){
					String fechar8 = lector.next();
					switch(fechar8)
					{
					case "a":
						fin2= true;
						break;
					default:
						queue6.enqueue(fechar8);
					}

				}
				view.printMessage("                          RANGO DE FECHAS                          |                          COMPARENDOS DURANTE EL AÑO                          \n");
				view.printMessage("----------------------------------------------------------------------------------------------------------------------------------------------------");

				for(int i=0; i<queue6.size();i++){
					String key = "2018-05-"+queue6.dequeue();
					parser2 = new SimpleDateFormat("yyy-MM-dd");
					Date fecha1 = parser2.parse(key);
					String key2 = "2018-05-"+queue6.dequeue();
					Date fecha10 = parser2.parse(key2);
					String somenum ="";
					Queue<Comparendo>queue20 = modelo.darComparendosDosfechas(fecha1, fecha10);
					somenum += String.format("%0" + queue20.size()+ "d", 0);
					somenum.replace("0", "*");

					view.printMessage("                      "+key+"/"+key2+"                         |"+somenum);
					
				}


			

			break;

			
			case 9:
				SeparateChainingHT<String, Comparendo> separate = modelo.darDatosSeparateChaining();
				view.printMessage("Convenciones --->  1= 500 procesados, 0= 500 atrasados por procesar \n");

				view.printMessage("                          RANGO DE FECHAS                          |                          COMPARENDOS DURANTE EL AÑO                          \n");
				view.printMessage("----------------------------------------------------------------------------------------------------------------------------------------------------");
				for(int i=0; i<separate.darNumeroElementos()||i<20;i++){
					Iterable<String> keys = separate.keys();
					Iterator<String>iter30 = keys.iterator();
					while(iter30.hasNext()){
						String key = iter30.next();
						ListaEncadenada<String, Comparendo>list = separate.darListaEncadenadaCompleta(key);
						String procesados = "";
						String enEspera="";
						if(list.size()>1500){
							procesados="***";
							int process =+ (list.size()-1500*i)/500;
							enEspera+= String.format("%0" + process+ "d", 0);
						}
						else{
							int process = list.size()/500;
							procesados+= String.format("%1" + process+ "d", 1);
						}
						view.printMessage("                      "+key +"                         |"+procesados+"\n"+enEspera);

					}			
					view.printMessage("");

				}


			

			break;

		case 10: 
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






