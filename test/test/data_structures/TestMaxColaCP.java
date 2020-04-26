package test.data_structures;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import model.data_structures.Comparendo;
import model.data_structures.MaxPQ;

public class TestMaxColaCP 
{
	private MaxPQ<Comparendo> Cola;
	private String fechaS;
	private String fechaS2;
	private Comparendo nueva;
	private Comparendo nueva2;
	SimpleDateFormat parser = new SimpleDateFormat("yyyy/MM/dd");



	@Before
	public void setUp1() throws ParseException {
		Cola = new 	MaxPQ<Comparendo>(darComparadorOBJECTID());
		fechaS = "2018/01/17";
		fechaS2 = "2018/01/18";


		Date fecha = parser.parse(fechaS);
		Date fecha2 = parser.parse(fechaS2);

		nueva = new Comparendo(1234, fecha, "hola2", "hola3", "hola4", "hola5", "hola", "hola7", "Barrios Unidos", "Chia",0,0, 0 );
		nueva2 = new Comparendo(0000, fecha2, "0009", "0008", "0007", "0006", "0005", "0004", "Fontibon", "Mosquera",0,0, 0);
		Cola.insert(nueva);
		Cola.insert(nueva2);
	}

	public void setUp2() {

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

	@Test
	public void testDarElemento() throws ParseException 
	{
		setUp1();
		// TODO
		assertEquals("No es el elemento esperado",1234 , Cola.delMax().darID());

	}
	@Test
	public void borrarElemento() throws ParseException{
		setUp1();
		Cola.delMax();
		assertEquals("No se encontro el elemento esperado", 0000, Cola.delMax().darID());
	}
	@Test
	public void agregarElemento() throws ParseException{
		setUp1();
		Date fecha3 = parser.parse(fechaS);
		Comparendo nueva3 = new Comparendo(9999, fecha3, "hola2", "hola3", "hola4", "hola5", "hola", "hola7", "Barrios Unidos", "Chia", 0, 0, 0);
		Cola.insert(nueva3);
		assertEquals(9999, Cola.delMax().darID());
				
	}


}
