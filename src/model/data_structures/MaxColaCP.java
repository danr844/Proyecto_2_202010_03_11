package model.data_structures;

import java.util.Comparator;

public class MaxColaCP< T extends Comparable <T>> implements ImaxColaCP<T>{


	private int tamanoCola;
	private ArregloDinamico<T>array;
	private static final int  CAPACIDAD=10000; 
	private T inicioCola;

	public MaxColaCP(){

		array = new ArregloDinamico<>(CAPACIDAD);
	}

	public boolean estavacia(){
		if(inicioCola==null)
			return true;
		else{
			return false;
		}
	}

	public void agregar(T multa) {
		// TODO Auto-generated method stub

		if(estavacia())
		{
			inicioCola = multa;
			array.agregar(multa);
			tamanoCola++;
		}			
		else
		{
			array.agregar(multa);
			tamanoCola++;
		}
	}





	public T deleteMax(Comparator<T> comparador)
	{
		T res = null;
		int max = 0;
		for (int i = 1; i < array.darTamano(); i++)
			if (array.less(array.darElemento(max), array.darElemento(i),comparador)) 
				max = i;
		array.exch(max, array.darTamano()-1); 
		array.disminuirTamano();
		res =  array.darElemento(array.darTamano()-1);
		tamanoCola--;
		return res; 

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
