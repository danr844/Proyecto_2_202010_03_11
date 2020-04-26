package model.logic;



import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import model.data_structures.Comparendo;
import model.data_structures.Haversine;
import model.data_structures.ListaEncadenada;
import model.data_structures.MaxPQ;
import model.data_structures.Queue;
import model.data_structures.RedBlackBST;
import model.data_structures.SeparateChainingHT;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo
{
	/**
	 * Atributos del modelo del mundo
	 */

	private SeparateChainingHT<String, Comparendo> datosSeparateChaining;
	private SeparateChainingHT<String , Comparendo> HashMCTLoc;
	private MaxPQ<Comparendo> maxpqTipoServicio;
	private MaxPQ<Comparendo> maxpqFecha;
	private RedBlackBST<Double,Comparendo>redtreeFechaLatitud;
	private static final int numeroImpresiones = 20;
	private static final double latitudTombos = 4.647586;
	private static final double longitudTombos =-74.078122;



	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	public Modelo()
	{
		datosSeparateChaining= new SeparateChainingHT<>();
		maxpqTipoServicio= new MaxPQ<Comparendo>(darComparador("tipoServicio"));
		maxpqFecha= new MaxPQ<Comparendo>(darComparador("fecha"));
		redtreeFechaLatitud = new RedBlackBST<Double,Comparendo>();
		HashMCTLoc= new SeparateChainingHT<>();
	}



	public Comparendo cargarInfo() throws ParseException{
		Comparendo mayorID1 = null;
		try {
			////// tesing
			int mayorID= 0;
			String path = "./data/Comparendos_DEI_2018_Bogot�_D.C_small.geojson";
			JsonReader reader;
			reader = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonArray ja = elem.getAsJsonObject().get("features").getAsJsonArray();
			SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
			for(JsonElement e: ja)
			{
				String []fecha1= e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString().split("T");
				int id = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
				Date fecha = parser.parse(fecha1[0]);
				String Hora = fecha1[1];
				String medio = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETECCION").getAsString();
				String Clasevehi= e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHICULO").getAsString();
				String tipoServicio = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVICIO").getAsString();
				String Infraccion =e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				String DescInfra=e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRACCION").getAsString();
				String Localidad = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();
				String Municipio = e.getAsJsonObject().get("properties").getAsJsonObject().get("MUNICIPIO").getAsString();
				double latitud = 0;
				double longitud=0;
				double distancia =0;
				if(e.getAsJsonObject().has("geometry") && !e.getAsJsonObject().get("geometry").isJsonNull())
				{
					JsonArray geoElem = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray();
					latitud=geoElem.get(0).getAsDouble();
					longitud=geoElem.get(1).getAsDouble();
					distancia = Haversine.distance(latitud, longitud, latitudTombos,longitudTombos);
				}

				Comparendo user = new Comparendo(id,fecha,Hora, medio, Clasevehi, tipoServicio, Infraccion, DescInfra, Localidad, Municipio, latitud, longitud, distancia );
				if(id>=mayorID)mayorID1= user;
				datosSeparateChaining.put(fecha1[0], user);
				redtreeFechaLatitud.put(latitud, user);
				maxpqTipoServicio.insert(user);
				maxpqFecha.insert(user);
				HashMCTLoc.put(medio+Clasevehi+tipoServicio+Localidad,user);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return mayorID1;
	}

	public ArrayList<Comparendo> darComparendosFeClaInfSeparateChaning(String llave){
		ArrayList<Comparendo> res = new ArrayList<Comparendo>();
		ListaEncadenada<String, Comparendo> comparendos = datosSeparateChaining.darListaEncadenadaCompleta(llave);
		Iterable<Comparendo> iterador = comparendos.keys1();
		Iterator<Comparendo> iter = iterador.iterator();
		while(iter.hasNext()){
			Comparendo nodo2 = iter.next();
			res.add(nodo2);
		}
		return res;
	}

	public Queue<Comparendo> darComparendosGravedad(int M){
		Queue<Comparendo>queue = new Queue<Comparendo>();
		for(int i=0; i<M;i++){
			Comparendo grave = maxpqTipoServicio.delMax();
			queue.enqueue(grave);
		}
		return queue;
	}

	public int darTamaniotablaSeparate(){return datosSeparateChaining.darTamaniotabla();}
	public int darNumeroElementosSeparate(){return datosSeparateChaining.darNumeroElementos();}
	public Comparator<Comparendo> darComparador(String caracteristicaComparable){

		if(caracteristicaComparable.equals("ID"))
		{

			Comparator<Comparendo> ID = new Comparator<Comparendo>()
			{
				@Override
				public int compare(Comparendo o1, Comparendo o2) 
				{
					if(o1.darID()<o2.darID())return -1;
					else if (o1.darID()>o2.darID())
						return 1;
					return 0;	
				}
			};
			return ID;

		}
		else if(caracteristicaComparable.equals("tipoServicio"))
		{

			Comparator<Comparendo> tipoServicio = new Comparator<Comparendo>()
			{
				@Override
				public int compare(Comparendo o1, Comparendo o2) 
				{
					if(!o1.darTipoServicio().equals("Público")&&o2.darTipoServicio().equals("Público"))return -1;
					else if(o1.darTipoServicio().equals("Público")&&!o2.darTipoServicio().equals("Público"))return 1;
					else if(o1.darTipoServicio().equals("Oficial")&&!o2.darTipoServicio().equals("Público"))return 1;
					else if(!o1.darTipoServicio().equals("Público")&&o2.darTipoServicio().equals("Oficial"))return -1;
					else if(o1.darTipoServicio().equals(o2.darTipoServicio())){
						if(o1.darInfraccion().compareTo(o2.darInfraccion())<0)return 1;
						if(o1.darInfraccion().compareTo(o2.darInfraccion())>0)return -1;
						else 
							return 0;
					}
					else
						return 0;	
				}
			};
			return tipoServicio;

		}
		else if(caracteristicaComparable.equals("fecha"))
		{

			Comparator<Comparendo> fecha = new Comparator<Comparendo>()
			{
				@Override
				public int compare(Comparendo o1, Comparendo o2) 
				{
					if(o1.darFecha().compareTo(o2.darFecha())<0)return -1;
					else if (o1.darFecha().compareTo(o2.darFecha())>0)
						return 1;
					return 0;	
				}
			};
			return fecha;
		}
		else if(caracteristicaComparable.equals("distancia"))
		{

			Comparator<Comparendo> distancia = new Comparator<Comparendo>()
			{
				@Override
				public int compare(Comparendo o1, Comparendo o2) 
				{
					if(o1.darDistancia()<o2.darDistancia())return -1;
					else if (o1.darDistancia()>o2.darDistancia())
						return 1;
					return 0;
				}
			};
			return distancia;

		}
		else return null;
	}

	public Comparendo eliminarMaxHeap()
	{
		return  (Comparendo) maxpqTipoServicio.delMax();
	}
	public void agregarMaxHeap(Comparendo comparendo){
		maxpqTipoServicio.insert(comparendo);
	}
	public Comparator<Comparendo> darComparadorOBJECTID(){


		Comparator<Comparendo> ID = new Comparator<Comparendo>()
		{
			@Override
			public int compare(Comparendo o1, Comparendo o2) 
			{
				if(o1.darID()<o2.darID())return -1;
				else if (o1.darID()>o2.darID())
					return 1;
				return 0;	
			}
		};
		return ID;
	}
	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamanoMaxPQ(){return maxpqTipoServicio.size();}
	public int dartamanioRedBLack(){return redtreeFechaLatitud.giveSize();}
	public MaxPQ<Comparendo> darMaxPQ(){return maxpqTipoServicio;}
	public SeparateChainingHT<String, Comparendo> darDatosSeparateChaining(){return datosSeparateChaining;}


	public MaxPQ<Comparendo> darMasCercano(int numImpresiones){
		MaxPQ<Comparendo> nueva =new MaxPQ <Comparendo> (darComparador("distancia"));
		for(int i=0; i<maxpqFecha.size()&&i<numImpresiones;i++){
			nueva.insert(maxpqFecha.delMax());;
		}
		return nueva ;
	}


	public Queue<String> darComparendosMesDia(int numMes, String Dia) throws ParseException{
		Queue<String> queue = new Queue<String>();
		Calendar calendar = Calendar.getInstance();

		int  month = numMes-1; 
		int year = 2018;
		int day =0;
		if(Dia.equals("L"))
			day= Calendar.MONDAY;
		else if(Dia.equals("M"))
			day= Calendar.TUESDAY;
		else if(Dia.equals("I"))
			day= Calendar.WEDNESDAY;
		else if(Dia.equals("J"))
			day= Calendar.THURSDAY;
		else if(Dia.equals("V"))
			day= Calendar.FRIDAY;
		else if(Dia.equals("S"))
			day= Calendar.SATURDAY;
		else if(Dia.equals("D"))
			day= Calendar.SUNDAY;

		calendar.set(calendar.YEAR, year);
		calendar.set(calendar.MONTH,month);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		int MaxDay = calendar.getActualMaximum(calendar.DAY_OF_MONTH);

		for(int i = 1 ; i < MaxDay ; i++)
		{        
			calendar.set(Calendar.DAY_OF_MONTH, i);
			if (calendar.get(Calendar.DAY_OF_WEEK) == day&&calendar.get(Calendar.MONTH) == month) {
				String date1 = formatter.format(calendar.getTime());
				queue.enqueue(date1);

			}
		}
		return queue;
	}
	public Queue<Comparendo> darComparendosMedioClaseTipoLoca(String medio, String clase, String tipo, String Localidad ){
		return  HashMCTLoc.darListaEncadenadaCompleta(medio+clase+tipo+Localidad).getAll(medio+clase+tipo+Localidad);
	}
	public Iterable<Comparendo> darComparendosRangoLatitud(double lo, double hi){
		return redtreeFechaLatitud.keysValue(lo, hi);
	}

	public Queue<Comparendo> darComparendosDosfechas(Date fecha2, Date fecha3) {
		Queue<Comparendo> queue = new Queue<Comparendo>();
		Iterable<Comparendo>queue2 = maxpqFecha;
		Iterator<Comparendo> iter = queue2.iterator();
		while(iter.hasNext()){
			Comparendo nodo2 = iter.next();
			if(nodo2.darFecha().compareTo(fecha2)>0&&nodo2.darFecha().compareTo(fecha3)<0){
				queue.enqueue(nodo2);
			}
		}

		return queue;
	}
}