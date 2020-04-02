package model.logic;



import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.LinearProbingHT;
import model.data_structures.ListaEncadenada;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
import model.data_structures.Ordenamientos;
import model.data_structures.ArregloDinamico;
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

	private LinearProbingHT<String ,Comparendo> datosLinearProbing;
	private SeparateChainingHT<String, Comparendo> datosSeparateChaining;
	private MaxColaCP<Comparendo> maxCola;
	private MaxHeapCP<Comparendo> heap;
	private ArregloDinamico<Comparendo>array;


	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	public Modelo()
	{
		datosLinearProbing= new LinearProbingHT<>();
		datosSeparateChaining= new SeparateChainingHT<>();
		maxCola = new MaxColaCP<Comparendo>();
		heap= new MaxHeapCP<Comparendo>();
		array = new ArregloDinamico<Comparendo>(10000);
	}



	public Comparendo[] cargarInfo() throws ParseException{
		String llaveUltimoelemento="";
		Comparendo []res = new Comparendo[2]; 

		try {
			////// tesing
			Gson gson = new Gson();

			String path = "./data/Comparendos_DEI_2018_Bogotá_D.C_small.geojson";
			JsonReader reader;

			List<String> lista = new ArrayList<String>();

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
				datosLinearProbing.put(fecha1[0]+Clasevehi+Infraccion, user);
				datosSeparateChaining.put(fecha1[0]+Clasevehi+Infraccion, user);

				llaveUltimoelemento = fecha+Clasevehi+Infraccion;
				array.agregar(user);

			}
			res[0] = datosLinearProbing.get(darLlavePrimerComparendo());
			res[1] = datosLinearProbing.get(llaveUltimoelemento);
			int k=0;
			Long start = System.currentTimeMillis();
			while(k<20000){
				int index = (int) Math.random();
				Comparendo user = array.darElemento(index);
				agregarMaxCola(user);
				agregarMaxHeap(user);
				k++;
			}
			Long finish = System.currentTimeMillis();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}
	public String darLlavePrimerComparendo(){
		String res ="";
		try {
			////// tesing
			Gson gson = new Gson();

			String path = "./data/comparendos_dei_2018_small.geojson";
			JsonReader reader;

			List<String> lista = new ArrayList<String>();

			reader = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(reader);
			JsonElement e = elem.getAsJsonObject().get("features").getAsJsonArray().get(0);

			String fecha= e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();
			String Clasevehi= e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHI").getAsString();
			String Infraccion =e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
			res = fecha+Clasevehi+Infraccion;

			System.out.println(Arrays.toString(lista.toArray()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;

	}
	public ArrayList<Comparendo> darComparendosFeClaInfSeparateChaning(String llave){
		ArrayList<Comparendo> res = new ArrayList<Comparendo>();
		ListaEncadenada<String, Comparendo> comparendos = datosSeparateChaining.darListaEncadenadaCompleta(llave);
		Iterable<String> iterador = comparendos.keys1();
		Iterator<String> iter = iterador.iterator();
		while(iter.hasNext()){
			String nodo2 = iter.next();
			res.add(datosSeparateChaining.get(nodo2));
		}
		if( res == null)
			System.out.println("puto");
		return res;
	}

	public int darTamaniotablaLinear(){return datosLinearProbing.darTamaniotabla();}
	public int darNumeroElementosLinear(){return datosLinearProbing.darNumeroElementos();}
	public int darTamaniotablaSeparate(){return datosSeparateChaining.darTamaniotabla();}
	public int darTamanioArregloDinamico(){return array.darTamano();}
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
		else return null;
	}
	public Comparendo eliminarMaxCola()
	{
		return  (Comparendo) maxCola.deleteMax(darComparadorOBJECTID());
	}
	public Comparendo eliminarMaxHeap(Comparendo comparendo)
	{
		return  (Comparendo) heap.deleteMax(comparendo, darComparadorOBJECTID());
	}

	public void agregarMaxCola(Comparendo comparendo){
		maxCola.agregar(comparendo);
	}
	public void agregarMaxHeap(Comparendo comparendo){
		heap.agregar(comparendo, darComparadorOBJECTID());
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
	public int darTamanoCola()
	{
		return maxCola.darNumElementos();
	}
	public int darTamanoHeap()
	{
		return heap.darNumElementos();
	}
	public MaxColaCP<Comparendo> darMaxCola(){
		return maxCola;
	}
	public MaxHeapCP<Comparendo> darMaxHeap(){
		return heap;
	}
	public ArregloDinamico<Comparendo> darArreglo(){
		return array;
	}
	public void agregarArregloDinamico(Comparendo multa){
		array.agregar(multa);
	}
	public LinearProbingHT<String ,Comparendo> darDatosLinearProbing(){return datosLinearProbing;}
	public SeparateChainingHT<String, Comparendo> darDatosSeparateChaining(){return datosSeparateChaining;}
}