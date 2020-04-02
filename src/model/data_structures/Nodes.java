
package model.data_structures;

	public class Nodes <T> {


		private Nodes<T> siguiente;

		private T valor;

		public Nodes ()
		{
			valor = null;
			siguiente=  null;	
		}
		public Nodes<T> darSiguiente()
		{
			return siguiente;
		}
		public void cambiarSiguiente(Nodes<T> pSiguiente)
		{
			siguiente = pSiguiente;
		}
		public void cambiarDato(T dato)
		{
			valor= dato;
		}
		public T darTvalor(){
			return valor;
		}
	}



