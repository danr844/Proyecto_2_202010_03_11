package test.data_structures;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.data_structures.Comparendo;
import model.data_structures.LinearProbingHT;
import model.data_structures.SeparateChainingHT;

import org.junit.Before;
import org.junit.Test;

public class TesthashTables {

	private Comparendo nueva;
	private Comparendo nueva2;
	private String fechaS;
	private String fechaS2;
	private LinearProbingHT<String, Comparendo> datosLinearprobing;
	private SeparateChainingHT<String, Comparendo> datosSeparateChaining;

	SimpleDateFormat parser = new SimpleDateFormat("yyyy/MM/dd");




	@Before
	public void setUp1() throws ParseException {
		datosLinearprobing = new LinearProbingHT<>();
		datosSeparateChaining = new SeparateChainingHT<>();
		fechaS = "2018/01/17";
		fechaS2 = "2018/01/18";

		Date fecha = parser.parse(fechaS);
		Date fecha2 = parser.parse(fechaS2);

		nueva = new Comparendo(1234, fecha, "hola2", "hola3", "hola4", "hola5", "hola", "hola7", "Barrios Unidos", "Chia");
		nueva2 = new Comparendo(0000, fecha2, "0009", "0008", "0007", "0006", "0005", "0004", "Fontibon", "Mosquera");
		datosLinearprobing.put(fechaS+nueva.darClaseVehiculo()+nueva.darInfraccion(), nueva);
		datosLinearprobing.put(fechaS+nueva2.darClaseVehiculo()+nueva2.darInfraccion(), nueva2);
		datosSeparateChaining.put(fechaS+nueva.darClaseVehiculo()+nueva.darInfraccion(), nueva);
		datosSeparateChaining.put(fechaS+nueva2.darClaseVehiculo()+nueva2.darInfraccion(), nueva2);


	}

	@Test
	public void testDarTamano() throws ParseException {
		// TODO
		setUp1();
		assertEquals("No tiene el tamaño esperado", 2, datosLinearprobing.darNumeroElementos() );
		assertEquals("No tiene el tamaño esperado", 2, datosSeparateChaining.darNumeroElementos() );

	}

	@Test
	public void testAgregar() throws ParseException 
	{
		// TODO Completar la prueba
		setUp1();
		String fecha1 = "2019/02/13";
		Date fecha = parser.parse(fecha1);
		Comparendo nueva3 = new Comparendo(1, fecha, "hola2", "hola3", "hola4", "hola5", "hola", "hola7", "Suba", "Cota");
		datosLinearprobing.put(fecha1+nueva3.darClaseVehiculo()+nueva3.darInfraccion(), nueva3);
		assertEquals("No tiene el tamaño esperado", 1,datosLinearprobing.get(fecha1+nueva3.darClaseVehiculo()+nueva3.darInfraccion()).darID() );
		datosSeparateChaining.put(fecha1+nueva3.darClaseVehiculo()+nueva3.darInfraccion(), nueva3);
		assertEquals("No tiene el tamaño esperado", 1,datosSeparateChaining.get(fecha1+nueva3.darClaseVehiculo()+nueva3.darInfraccion()).darID() );

		

	}

	@Test
	public void testNovacio() throws ParseException
	{
		setUp1();
		// TODO Completar la prueba
		assertNotNull("El objeto no deberia ser null1", datosLinearprobing.get(fechaS+nueva.darClaseVehiculo()+nueva.darInfraccion()));
		assertNotNull("El objeto no deberia ser null1",datosLinearprobing.get(fechaS+nueva2.darClaseVehiculo()+nueva2.darInfraccion()));
		assertNotNull("El objeto no deberia ser null", datosSeparateChaining.get(fechaS+nueva.darClaseVehiculo()+nueva.darInfraccion()));
		assertNull("El objeto deberia ser distinto de null",datosSeparateChaining.get(fechaS2+nueva2.darClaseVehiculo()+nueva2.darInfraccion()));
	}
	@Test
	public void testEliminar() throws ParseException
	{
		setUp1();
		// TODO Completar la prueba
		datosLinearprobing.delete(fechaS+nueva.darClaseVehiculo()+nueva.darInfraccion());
		datosSeparateChaining.delete(fechaS2+nueva2.darClaseVehiculo()+nueva2.darInfraccion());
		assertNull("El objeto no deberia ser null1", datosLinearprobing.get(fechaS+nueva.darClaseVehiculo()+nueva.darInfraccion()));
		assertNull("El objeto deberia ser distinto de null",datosSeparateChaining.get(fechaS2+nueva2.darClaseVehiculo()+nueva2.darInfraccion()));
	}

	

}
