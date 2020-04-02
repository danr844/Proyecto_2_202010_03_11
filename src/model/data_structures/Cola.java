package model.data_structures;
import java.util.Iterator;



public class Cola<T> implements Iterable<T>,ICola<T>
{
	private Nodes<T> inicioCola;
	private Nodes<T> finCola;
	private int tamanoCola;

	public Cola(){
		inicioCola = null;
		finCola = null;
	}


	public boolean estavacia(){
		if(inicioCola==null)
			return true;
		else{
			return false;
		}
	}


	public void enqueue(T multa)
	{
		Nodes<T> nuevo = new Nodes<>();
		nuevo.cambiarDato(multa);
		if(estavacia())
		{
			inicioCola = nuevo;
			finCola = nuevo;
			tamanoCola++;
		}
		else if(inicioCola.darSiguiente()==null)
		{
			inicioCola.cambiarSiguiente(nuevo);
			tamanoCola++;
			finCola=nuevo;
		}
		else
		{
			finCola.cambiarSiguiente(nuevo);
			finCola = nuevo;
			tamanoCola++;
		}
	}
	public T dequeue()
	{
		if(!estavacia())
		{
			Nodes<T> valorEliminado = inicioCola;
			if(inicioCola.darSiguiente()!=null)
			{
				inicioCola = inicioCola.darSiguiente();
				tamanoCola--;
			}
			else
			{
				inicioCola= null;
				tamanoCola = 0;
			}
			return valorEliminado.darTvalor();
		}
		else
		{
			return null;
		}
	}

	public int dartamanoCola(){
		return tamanoCola;
	}


	public Nodes<T> darPrimerElemento()
	{
		return inicioCola;
	}
	
	 public Iterator<T> iterator()  {
	        return new LinkedIterator(inicioCola);  
	    }

	    // an iterator, doesn't implement remove() since it's optional
	    private class LinkedIterator implements Iterator<T> {
	        private Nodes<T> current;

	        public LinkedIterator(Nodes<T> first) {
	            current = first;
	        }

	        public boolean hasNext()  { return current != null;                     }
	        public void remove()      { throw new UnsupportedOperationException();  }

	        public T next() {
	            T item = current.darTvalor();
	            current = current.darSiguiente(); 
	            return item;
	        }
	    }

}