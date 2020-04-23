package model.data_structures;

public class LinearProbingHT <K ,V > implements IHashTable<K,V>   
{
	private static final int INIT_CAPACITY = 53;

	private int n;
	private int m;
	private K[] keys;
	private V[] values;

	public LinearProbingHT() {
		this(INIT_CAPACITY);
	}
	public LinearProbingHT(int capacity) {
		m = capacity;
		n = 0;
		keys = (K[])   new Object[m];
		values = (V[]) new Object[m];
	}
	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean contains(K key) {
		return get(key) != null;
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

	private void resize(int capacity) {
		LinearProbingHT<K, V> temp = new LinearProbingHT<K, V>(capacity);
		for (int i = 0; i < m; i++) {
			if (keys[i] != null) {
				temp.put(keys[i], values[i]);
			}
		}
		keys = temp.keys;
		values = temp.values;
		m    = temp.m;
	}

	public void put(K key, V val) {

		// double table size if 
		if (n/m >= 0.75) resize(2*m);

		int i;
		for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
			if (keys[i].equals(key)) {
				values[i] = val;
				return;
			}
		}
		keys[i] = key;
		values[i] = val;
		n++;
	}

	public V get(K key) {

		for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
			if (keys[i].equals(key))
				return values[i];
		return null;
	}

	public V delete(K key) {
		if (!contains(key)) return null;

		V retorno= get(key);
		// find position i of key
		int i = hash(key);
		while (!key.equals(keys[i])) {
			i = (i + 1) % m;
		}

		// delete key and associated value
		keys[i] = null;
		values[i] = null;

		// rehash all keys in same cluster
		i = (i + 1) % m;
		while (keys[i] != null) {
			// delete keys[i] an vals[i] and reinsert
			K keyToRehash = keys[i];
			V valToRehash = values[i];
			keys[i] = null;
			values[i] = null;
			n--;
			put(keyToRehash, valToRehash);
			i = (i + 1) % m;
		}

		n--;

		// halves size of array if it's 12.5% full or less
		if (n > 0 && n <= m/8) resize(m/2);

		return retorno;
	}

	public Iterable<K> keys() {
		Queue<K> queue = new Queue<K>();
		for (int i = 0; i < m; i++)
			if (keys[i] != null) queue.enqueue(keys[i]);
		return queue;
	}
	public int darTamaniotabla(){return m;}
	public int darNumeroElementos(){return n;}
}
