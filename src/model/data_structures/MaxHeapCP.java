package model.data_structures;

import java.util.Comparator;

public class MaxHeapCP< T extends Comparable <T>> implements IMaxHeapCP<T>{


	private int tamanoCola;
	private ArregloDinamico<T>array;
	private static final int  CAPACIDAD=10000; 
	private T inicioCola;

	public MaxHeapCP(){

		array = new ArregloDinamico<>(CAPACIDAD);
	}

	public boolean estavacia(){
		if(inicioCola==null)
			return true;
		else{
			return false;
		}
	}

	public void agregar(T multa, Comparator comparador) 
	{
			array.agregar(multa);;
			swim(array.darTamano()-1, comparador); 
			tamanoCola++;

	}

	private void swim (int k, Comparator comparador)
	{
		while (k > 1 && array.less(array.darElemento(k/2), array.darElemento(k), comparador))
		{//si es menor el padre 
			array.exch(k, k/2);
			//intercambie padre e hijo
			k = k/2; 
			//subir al siguiente nivel 
		} 

	}

	private void sink (int k, Comparator comparador)
	{ 
		while (2*k <= array.darTamano())
		{//Recorrer el arreglo hacia abajo
			int j = 2*k; 
			//j es igual al hijo izq
			if (j < array.darTamano() && array.less(array.darElemento(j), array.darElemento(j+1), comparador))
				//selecciona el hijo mayor 
				j++;
			if (!array.less(array.darElemento(k),array.darElemento(j), comparador))
				//si el padre no es menor, fin
				break; 
			array.exch(k, j); 
			//padre menor, cambiar padre por hijo mayor
			k = j; 
			// bajar al siguiente nivel
		}
	}






	public T deleteMax(Comparator<T> comparador)
	{
		T max = array.darElemento(1);
		//máximo es 1
		array.exch(1, array.darTamano()-1);
		//cambiar por último y decremente N
		sink(1, comparador); 
		array.eliminar(array.darElemento(array.darTamano()+1)); 
		tamanoCola--;
		return max; 


	}

	public int darNumElementos(){
		return tamanoCola;
	}


	public T darPrimerElemento()
	{
		return inicioCola;
	}

	public T darMax(Comparator comparador) {
		// TODO Auto-generated method stub
		int max = 0;
		for (int i = 1; i < array.darTamano(); i++)
			if (array.less(array.darElemento(max), array.darElemento(i),comparador)) 
				max = i;
		return array.darElemento(max);
	}

	public boolean esVacia() {
		// TODO Auto-generated method stub

		return tamanoCola==0;
	}
	public ArregloDinamico<T> darElementos(){
		return array;
	}



}
