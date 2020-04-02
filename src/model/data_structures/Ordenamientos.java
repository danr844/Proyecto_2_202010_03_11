package model.data_structures;

import java.util.ArrayList;

public class Ordenamientos {

	public   static int partition(ArrayList<Comparendo> res, int lo, int hi)
	{  // Partition into a[lo..i-1], a[i], a[i+1..hi].    
		int i = lo, j = hi+1;            
		// left and right scan indices   
		Comparable v = res.get(lo);            
		// partitioning item   
		while (true)   {  
			// Scan right, scan left, check for scan complete, and exchange.       
			while (less(res.get(i++), v)) 
				if (i == hi) break;      
			while (less(v, res.get(--j))) 
				if (j == lo) break;      
			if (i >= j) break;      
			exch(res, i, j);   
		}   
		exch(res, lo, j);       
		// Put v = a[j] into position    
		return j;             
		// with a[lo..j-1] <= a[j] <= a[j+1..hi]. }
	}

	public static boolean IsNull(Object x) {
		if (x == null) 
			return true;  
		else 
			return false;
	}

	public  static boolean   less(Comparable v, Comparable w)  
	{  
		return v.compareTo(w) < 0;  
	}   
	public  static void exch(ArrayList<Comparendo> res, int i, int j)   
	{  
		Comparendo t = res.get(i); 
		res.set(i, res.get(j)) ;
		res.set(j, t);  
	} 
	public  static void shuffle(Comparable[] a) {
		if(!IsNull(a)){
			int n = a.length;
			for (int i = 0; i < n; i++) {
				int r = (int)   Math.random()*n;     // between i and n-1
				Comparable temp = a[i];
				a[i] = a[r];
				a[r] = temp;
			}
		}
	}
//	public static void ShellSort(Comparable[] a)  
//	{  
//		// Sort a[] into increasing order.      
//		int N = a.length;      
//		int h = 1;      
//		while (h < N/3) 
//			h = 3*h + 1; 
//		// 1, 4, 13, 40, 121, 364, 1093, ...      
//		while (h >= 1)      
//		{  // h-sort the array.         
//			for (int i = h; i < N; i++)         
//			{  // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .            
//				for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)               
//					exch(a, j, j-h);         
//			}
//			h = h/3;      
//		}   
//	}   // See page 245 
//	public static void Quicksort(Comparable[] a)  
//	{     
//		shuffle(a);       
//		// Eliminate dependence on input.   
//		sortQuick(a, 0, a.length - 1);   
//	}   
	private static void sortQuick(ArrayList<Comparendo> res, int lo, int hi)   
	{      
		if (hi <= lo) 
			return;      
		int j = partition(res, lo, hi);  
		// Partition (see page 291).      
		sortQuick(res, lo, j-1);              
		// Sort left part a[lo .. j-1].      
		sortQuick(res, j+1, hi);              
		// Sort right part a[j+1 .. hi].   

	}
	public static void sortMerge(ArrayList<Comparendo> res, int lo, int hi)   
	{      
		// Sort a[lo..hi].
		 if (hi <= lo) return;
		 int mid = lo + (hi - lo)/2;
		 sortQuick(res, lo, mid); // Sort left half.
		 sortQuick(res, mid+1, hi); // Sort right half.
		 merge(res, lo, mid, hi); // Merge results   

	}
	

	private static  void merge(ArrayList<Comparendo> res, int lo, int mid, int hi){
		// Fusionar a[lo..mid] con a[mid+1..hi].
		Comparable[] aux;
		aux = new Comparable[res.size()];
		int i = lo, j = mid+1;
		//Copiar a[lo..hi] en aux[lo..hi].
		for (int k = lo; k <= hi; k++)
			aux[k] = res.get(k);
		// Fusionar de regreso a a[lo..hi].
		for (int k = lo; k <= hi; k++)
			if (i > mid) res.set(k, (Comparendo) aux[j++]);//Agotado izquierdo
			else if (j > hi ) res.set(k, (Comparendo) aux[i++]); //Agotado derecho
			else if (less(aux[j], aux[i]))res.set(k, (Comparendo) aux[j++]); //Insertar de derecho
			else
				res.set(k, (Comparendo) aux[i++]); //Insertar de izquierdo
	}


}




