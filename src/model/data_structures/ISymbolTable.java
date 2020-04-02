package model.data_structures;

public interface ISymbolTable <Key,Value> 
{
	void put(Key key, Value val);
	
	Value  get(Key key);

	Value delete(Key key);

	boolean contains(Key key);
	
	boolean isEmpty();
	
	int size();
	
	Iterable<Key> keys();
}
