

package model.data_structures;


public class Node1<Key, V>
{ // linked-list node
	public Key key;
	public V val;
	public Node1<Key, V> next;

	public Node1(Key key, V val, Node1<Key, V> next)
	{
		this.key = key;
		this.val = val;
		this.next = next;
	}
}




