package model.logic;



import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.LinearProbingHT;
import model.data_structures.ListaEncadenada;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxPQ;
import model.data_structures.Ordenamientos;
import model.data_structures.Queue;
import model.data_structures.RedBlackBST;
import model.data_structures.Comparendo;
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

	private LinearProbingHT<Date ,Comparendo> datosLinearProbing;
	private SeparateChainingHT<Date, Comparendo> datosSeparateChaining;
	private MaxColaCP<Comparendo> maxCola;
	private MaxPQ<Comparendo> maxpq;
	private RedBlackBST<Date,Comparendo>redtree;
	private static final int numeroImpresiones = 20;


	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	public Modelo()
	{
		datosLinearProbing= new LinearProbingHT<>();
		datosSeparateChaining= new SeparateChainingHT<>();
		maxCola = new MaxColaCP<Comparendo>();
		maxpq= new MaxPQ<Comparendo>(darComparador("tipoServicio"));
		redtree = new RedBlackBST<Date,Comparendo>();
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
				datosLinearProbing.put(fecha, user);
				datosSeparateChaining.put(fecha, user);
				redtree.put(fecha, user);
				maxpq.insert(user);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return mayorID1;
	}

	public ArrayList<Comparendo> darComparendosFeClaInfSeparateChaning(Date llave){
		ArrayList<Comparendo> res = new ArrayList<Comparendo>();
		ListaEncadenada<Date, Comparendo> comparendos = datosSeparateChaining.darListaEncadenadaCompleta(llave);
		Iterable<Date> iterador = comparendos.keys1();
		Iterator<Date> iter = iterador.iterator();
		while(iter.hasNext()){
			Date nodo2 = iter.next();
			res.add(datosSeparateChaining.get(nodo2));
		}
		return res;
	}
	
	public Queue<Comparendo> darComparendosGravedad(int M){
		Queue<Comparendo>queue = new Queue<Comparendo>();
		for(int i=0; i<M;i++){
			Comparendo grave = maxpq.delMax();
			queue.enqueue(grave);
		}
		return queue;
	}

	public int darTamaniotablaLinear(){return datosLinearProbing.darTamaniotabla();}
	public int darNumeroElementosLinear(){return datosLinearProbing.darNumeroElementos();}
	public int darTamaniotablaSeparate(){return datosSeparateChaining.darTamaniotabla();}
	public int darNumeroElementosSeparate(){return datosSeparateChaining.darNumeroElementos();}

	public boolean existeLlaveLinearProbing(Date key)
	{
		return datosLinearProbing.contains(key);
	}

	public ArrayList<Comparendo> buscarPorKeyLinearProbing(Date key)
	{
		ArrayList<Comparendo> retorno= new ArrayList<>();
		LinearProbingHT<Date ,Comparendo> copia=datosLinearProbing;
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
		else return null;
	}
	public Comparendo eliminarMaxCola()
	{
		return  (Comparendo) maxCola.deleteMax(darComparadorOBJECTID());
	}
	public Comparendo eliminarMaxHeap()
	{
		return  (Comparendo) maxpq.delMax();
	}

	public void agregarMaxCola(Comparendo comparendo){
		maxCola.agregar(comparendo);
	}
	public void agregarMaxHeap(Comparendo comparendo){
		maxpq.insert(comparendo);
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
	public int darTamanoCola(){return maxCola.darNumElementos();}
	public int darTamanoMaxPQ(){return maxpq.size();}
	public int dartamanioRedBLack(){return redtree.giveSize();}
	public MaxColaCP<Comparendo> darMaxCola(){return maxCola;}
	public MaxPQ<Comparendo> darMaxPQ(){return maxpq;}
	public LinearProbingHT<Date ,Comparendo> darDatosLinearProbing(){return datosLinearProbing;}
	public SeparateChainingHT<Date, Comparendo> darDatosSeparateChaining(){return datosSeparateChaining;}
}