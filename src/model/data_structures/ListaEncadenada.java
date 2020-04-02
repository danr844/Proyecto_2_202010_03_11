package model.data_structures;

public class ListaEncadenada <Key extends Comparable<Key>,V> implements ISymbolTable<Key,V> 
{

	private int n;
	
	private Node1<Key, V> first;
	
	
	
	public ListaEncadenada(){		
	}

	public int size()
	{
		return n;
	}
	
	public boolean isEmpty()
	{
		return size()==0;
	}
	
	 public boolean contains(Key key) {
	        return get(key) != null;
	    } 

	
	public V get(Key k)
	{
		for(Node1<Key, V> x=first; x!=null; x=x.next)
		{
			if(k.equals(x.key))
			{
				return x.val;
			}
		}
		return null;
	}
	
	public void put(Key k,V v)
	{
		
		for(Node1<Key, V> x=first; x!=null; x=x.next)
		{
			if(k.equals(x.key))
			{
				x.val=v;
				return;
			}
		}
		first= new Node1<Key, V>(k,v,first);
		n++;
	}
	
	  public V delete(Key key) 
	    {
		  	V retorno= get(key);
	        first = delete(first, key);
	        return retorno;
	    }

	    // delete key in linked list beginning at Node x
	  private Node1<Key, V> delete(Node1<Key, V> x, Key key) 
	  {
	        if (x == null) return null;
	        if (key.equals(x.key)) {
	            n--;
	            return x.next;
	        }
	        x.next = delete(x.next, key);
	        return x;
	    }
	    
	    public Iterable<Key> keys1()  {
	        Cola<Key> queue = new Cola<Key>();
	        for (Node1<Key, V> x = first; x != null; x = x.next)
	            queue.enqueue( x.key);
	        return queue;
	    }

		@Override
		public Iterable<Key> keys() {
			// TODO Auto-generated method stub
			return null;
		}


}
