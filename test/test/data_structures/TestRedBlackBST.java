package test.data_structures;

import model.data_structures.Comparendo;
import model.data_structures.RedBlackBST;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;


public class TestRedBlackBST {
	private Comparendo nueva;
	private Comparendo nueva2;
	private String fechaS;
	private String fechaS2;

	SimpleDateFormat parser = new SimpleDateFormat("yyyy/MM/dd");
	RedBlackBST<Integer,Comparendo> tree = new RedBlackBST<>();





	@Before
	public void setUp1() throws ParseException {

		fechaS = "2018/01/17";
		fechaS2 = "2018/01/18";
		Date fecha = parser.parse(fechaS);
		Date fecha2 = parser.parse(fechaS2);

		nueva = new Comparendo(1234, fecha, "hola2", "hola3", "hola4", "hola5", "hola", "hola7", "Barrios Unidos", "Chia");
		nueva2 = new Comparendo(0000, fecha2, "0009", "0008", "0007", "0006", "0005", "0004", "Fontibon", "Mosquera");
		tree.put(nueva.darID(),nueva);
		tree.put(nueva2.darID(),nueva2);

	}


	@Test
	public void testModelo() throws ParseException {
		setUp1();
		assertTrue(tree!=null);
	}

	@Test
	public void testDarTamano() throws ParseException {
		// TODO
		setUp1();
		assertEquals("No tiene el tamaño esperado", 2, tree.size(tree.giveRoot()));

	}

	@Test
	public void testAgregar() throws ParseException 
	{
		// TODO Completar la prueba
		setUp1();
		assertEquals("No tiene el tamaño esperado", 2, tree.size(tree.giveRoot()));
		String fecha1 = "2019/02/13";
		Date fecha = parser.parse(fecha1);
		Comparendo nueva3 = new Comparendo(1, fecha, "hola2", "hola3", "hola4", "hola5", "hola", "hola7", "Suba", "Cota");
		tree.put(nueva3.darID(), nueva3);
		assertEquals("No tiene el tamaño esperado", 3, tree.size(tree.giveRoot()));


	}

	@Test
	public void testNovacio() throws ParseException
	{
		setUp1();
		// TODO Completar la prueba
		assertNotNull("El objeto no deberia ser null1", tree.get(nueva.darID()));
		assertNotNull("El objeto no deberia ser null1", tree.get(nueva2.darID()));
		assertNull("El objeto no deberia ser distinto de null", tree.get(nueva.darID()+1));
	}

	@Test
	public void testDarObjeto() throws ParseException
	{
		setUp1();
		// TODO Completar la prueba
		assertEquals("El objeto no es el que corresponde", 1234,tree.get(nueva.darID()).darID());
		assertEquals("El objeto no es el que corresponde", 0000,tree.get(nueva2.darID()).darID());
		assertNull("El objeto no deberia ser distinto de null", tree.get(nueva.darID()+1));
	}
	@Test

	public void testDarmaximo() throws ParseException
	{
		setUp1();
		// TODO Completar la prueba
		assertEquals("El objeto no es el que corresponde", 1234,tree.getMaxValue().darID());
		assertNotNull("El objeto no deberia ser distinto de null", tree.getMaxValue().darID());
	}
	@Test

	public void testDarMinimo() throws ParseException
	{
		setUp1();
		// TODO Completar la prueba
		assertEquals("El objeto no es el que corresponde", 0000,tree.getMin().darID());
		assertNotNull("El objeto no deberia ser distinto de null", tree.getMin().darID());
	}
	@Test

	public void testDarAltura() throws ParseException
	{
		setUp1();
		// TODO Completar la prueba
		assertEquals("la altura no es la que corresponde", 1,tree.height());
		assertNotNull("El objeto no deberia ser distinto de null", tree.getMin().darID());
	}
	@Test

	public void testContiene() throws ParseException
	{
		setUp1();
		// TODO Completar la prueba
		assertTrue(tree.contains(nueva.darID()));
		assertTrue(tree.contains(nueva2.darID()));
		assertFalse(tree.contains(nueva.darID()+1));
	}
	@Test

	public void testDarRaiz() throws ParseException
	{
		setUp1();
		// TODO Completar la prueba
		assertEquals("El objeto no es el esperado",1234 ,tree.giveRoot().val.darID());
	}




}
