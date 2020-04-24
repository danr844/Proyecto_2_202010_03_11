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
import model.data_structures.LinearProbingHT;
import model.data_structures.ListaEncadenada;
import model.data_structures.MaxPQ;
import model.data_structures.Ordenamientos;
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

	private LinearProbingHT<String ,Comparendo> datosLinearProbing;
	private SeparateChainingHT<String, Comparendo> datosSeparateChaining;
	private MaxPQ<Comparendo> maxpqTipoServicio;
	private MaxPQ<Comparendo> maxpqFecha;
	private RedBlackBST<String,Comparendo>redtree;
	private static final int numeroImpresiones = 20;


	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	public Modelo()
	{
		datosLinearProbing= new LinearProbingHT<>();
		datosSeparateChaining= new SeparateChainingHT<>();
		maxpqTipoServicio= new MaxPQ<Comparendo>(darComparador("tipoServicio"));
		maxpqFecha= new MaxPQ<Comparendo>(darComparador("fecha"));
		redtree = new RedBlackBST<String,Comparendo>();
	}



	public Comparendo cargarInfo() throws ParseException{
		Comparendo mayorID1 = null;
		try {
			////// tesing
			int mayorID= 0;
			String path = "./data/Comparendos_DEI_2018_Bogotá_D.C_small.geojson";
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


				Comparendo user = new Comparendo(id,fecha,Hora, medio, Clasevehi, tipoServicio, Infraccion, DescInfra, Localidad, Municipio );
				if(id>=mayorID)mayorID1= user;
				datosLinearProbing.put(fecha1[0], user);
				datosSeparateChaining.put(fecha1[0], user);
				redtree.put(fecha1[0], user);
				maxpqTipoServicio.insert(user);
				maxpqFecha.insert(user);
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

	public int darTamaniotablaLinear(){return datosLinearProbing.darTamaniotabla();}
	public int darNumeroElementosLinear(){return datosLinearProbing.darNumeroElementos();}
	public int darTamaniotablaSeparate(){return datosSeparateChaining.darTamaniotabla();}
	public int darNumeroElementosSeparate(){return datosSeparateChaining.darNumeroElementos();}

	public boolean existeLlaveLinearProbing(String key)
	{
		return datosLinearProbing.contains(key);
	}

	public ArrayList<Comparendo> buscarPorKeyLinearProbing(String key)
	{
		ArrayList<Comparendo> retorno= new ArrayList<>();
		LinearProbingHT<String ,Comparendo> copia=datosLinearProbing;
		Comparendo actual= copia.get(key);
		while(actual!=null)
		{
			retorno.add(actual);
			copia.delete(key);
			actual=copia.get(key);
		}	
		Ordenamientos.sortMerge(retorno, 0,retorno.size()-1);

		return retorno;
	}

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
					if(!o1.darTipoServicio().equals("Publico")&&o2.darTipoServicio().equals("Publico"))return -1;
					else if(o1.darTipoServicio().equals("Publico")&&!o2.darTipoServicio().equals("Publico"))return 1;
					else if(o1.darTipoServicio().equals(o1.darTipoServicio())){
						if(o1.darTipoServicio().compareTo(o2.darTipoServicio())<0)return -1;
						if(o1.darTipoServicio().compareTo(o2.darTipoServicio())>0)return 1;
						else 
							return 0;
					}
					else if(o1.darTipoServicio().equals("Oficial")&&!o2.darTipoServicio().equals("Publico"))return 1;
					else if(!o1.darTipoServicio().equals("Publico")&&o2.darTipoServicio().equals("Oficial"))return -1;

					else
						return 0;	
				}
			};
			return tipoServicio;

		}
		else if(caracteristicaComparable.equals("fecha")){

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
	public int dartamanioRedBLack(){return redtree.giveSize();}
	public MaxPQ<Comparendo> darMaxPQ(){return maxpqTipoServicio;}
	public LinearProbingHT<String ,Comparendo> darDatosLinearProbing(){return datosLinearProbing;}
	public SeparateChainingHT<String, Comparendo> darDatosSeparateChaining(){return datosSeparateChaining;}

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
		//		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");                                    
		//		Calendar cini = Calendar.getInstance();
		//		cini.setTime(formatter.parse("2018-"+numMes+"-01"));
		//		Calendar cfin = Calendar.getInstance();
		//		cfin.setTime(formatter.parse("2018-"+numMes+"-"+MaxDay));
		//
		//		while (!cfin.before(cini)) {
		//		    if (cini.get(Calendar.DAY_OF_WEEK)) {
		//		        queue.enqueue(cini.getTime());
		//		    }
		//		    cini.add(Calendar.DATE, 1);
		//		}
		return queue;
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