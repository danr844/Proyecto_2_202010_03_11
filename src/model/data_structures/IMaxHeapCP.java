package model.data_structures;

import java.util.Comparator;

public interface IMaxHeapCP<T> {

	int darNumElementos();

	void agregar(T elemento, Comparator<T> comparador);


	T darMax(Comparator<T> comparador);

	boolean esVacia();


}
