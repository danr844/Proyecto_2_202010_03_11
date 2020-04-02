package model.data_structures;

public class SeparateChainingHT <K extends Comparable<K>,V extends Comparable<V>> implements IHashTable<K,V>
{
	private static final int INIT_CAPACITY = 53;

	private int n;          						// number of key-value pairs

	private int m;                                	// hash table size

	private ListaEncadenada<K, V>[] st; // array of ST objects

	public SeparateChainingHT()
	{
		this(INIT_CAPACITY);
	}

	public SeparateChainingHT(int m)
	{
		this.m=m;

		st=(ListaEncadenada<K, V>[]) new ListaEncadenada[m];
		for (int i = 0; i < m; i++)
			st[i] = new ListaEncadenada();
	}

	private void resize(int chains) {
		SeparateChainingHT<K, V> temp = new SeparateChainingHT<K, V>(chains);
		for (int i = 0; i < m; i++) {
			for (K key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.m  = temp.m;
		this.n  = temp.n;
		this.st = temp.st;
	}

	private int hash(K key)
	{
		return (key.hashCode() & 0x7fffffff) % m; 
	}

	public boolean contains(K key) 
	{
		return get(key) != null;
	} 

	public V get(K key)
	{
		return  st[hash(key)].get(key); 
	}

	public void put(K key, V val)
	{
		if(key==null)
		{
			delete(key);
			return;
		}

		if (n/m>=5.0)
		{
			resize(2*m);
		}

		if (!st[hash(key)].contains(key))
			n++;
		st[hash(key)].put(key, val);
	}
	
	public V delete(K key) 
	{
		if (m > INIT_CAPACITY && n <= m/8) 
			resize(m/2);
		
        if (st[hash(key)].contains(key))
        	n--;
        return st[hash(key)].delete(key);

        // halve table size if average length of list <= 2
    } 
	
	public Iterable<K> keys() 
	{
        Cola<K> queue = new Cola<K>();
        for (int i = 0; i < m; i++) {
            for (K key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    } 
	public ListaEncadenada<K, V> darListaEncadenadaCompleta(K Key){
		return st[hash(Key)];
	}
	public int darTamaniotabla(){return m;}
	public int darNumeroElementos(){return n;}

	
}


