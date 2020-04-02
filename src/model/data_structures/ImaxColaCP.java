package model.data_structures;

import java.util.Comparator;

public interface ImaxColaCP<T> {
	 
	int darNumElementos();
	
	void agregar(T elemento);
	
	T deleteMax(Comparator<T> comparador);
	
	T darMax(Comparator<T> comparador);
	
	boolean esVacia();
	
	
	

}
