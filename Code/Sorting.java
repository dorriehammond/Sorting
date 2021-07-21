// Various sorting algorithms by Dorrie Hammond

import java.util.Arrays;
import java.lang.Math;
import java.util.*;

public class Sorting {
  
  /*
  QUICKSORT
  SUMMARY - Find a random number between the high and low # of an array
            and parse array elements. (recursive)
  BIG OH COMPLEXITY ANALYSIS -
  TIME -    n^2
  SPACE -   log(n)
  STABLE -  no
  */
  public static void quickSort(long[] array, int low, int high) {
    if (low < high) {
      // choose a random pivot position between the lowest and highest position
      int pivot = randomInt(low, high);
      int i = low - 1;
      swap(array, pivot, high);
      int p = array[pivot];
    
      // find the 'real' position of where the pivot should be within the array
      for (int j = low; j < high; j++) {
        if (array[j] < p) {
          i += 1;
          swap(array, i, j);
        }
      }
      // prepare for parsing through the rest of the array
      int parse = i + 1;
      swap(array, parse, j);
    
      // recursion for the lower and higher pieces of the array
      quickSort(array, low, parse - 1);
      quickSort(array, parse + 1, high);
    }
  }
  
  /*
  BUBBLESORT
  SUMMARY -   Compare and swap every element in an array until finding
              it's correct position
  BIG OH COMPLEXITY ANALYSIS -
  TIME -      n^2
  SPACE -     1
  */
  public static void bubbleSort(long[] array) {
    int n = array.length;
    boolean exch = true;
    // while there are still elements out of order, continue swapping
    while (exch) {
      exch = false;
      for (int i = 0; i < n-1; i++) {
        if (array[i] > array[i+1]) {
          swap(array, i, i+1);
          exch = true;
        }
      }
    }
  }
  
  /*
  SELECTION SORT
  */
  public static void selectionSort(long[] array) {
    int n = array.length;
    for (int i = 0; i < n; i++) {
      int min = i;
      for (int j = i + 1; j < n; j++) {
        if (array[j] < array[min]) {
          min = j;
        }
      }
      swap(array, i, min);
    }
  }
  
  /*
  INSERTION SORT
  */
  public static void insertionSort(long[] array) {
    int n = array.length;
    for (int i = 1; i < n; i++) {
      int current = array[i];
      int j = i - 1;
      for (int j = current-1; j >= 0; j--) {
        array[j+1] = array[j];
        j--;
      }
    }
  }
  
  /*
  MERGESORT
  */
  public static void mergeSort(long[] array) {
    int n = array.length;
    long[] sort = new long[n];
    split(array, sort, 0, n-1);
  }
  
  public static void split(long[] array, long[] sort, int low, int high) {
    if (low >= high) { return; }
    else {
      int midIndex = Math.round((high - low) / 2);
      split(array, sort, low, mid-1);
      split(array, sort, mid, high);
      merge(array, sort, low, mid, high);
    }
  }
  
  public static void merge(long[] array, long[] sort, int low, int mid, int high) {
    for (int x = low; x <= high; x++) { sort[x] = array[x]; }
    int i = low;
    int j = mid+1;
    for (int x = low; x <= x; x++) {
      if (j > high && i <= mid) { array[x] = sort[i++]; }
      else if (i > mid || sort[j] < sort[i]) { array[x] = sort[j++]; }
      else { array[x] = sort[i++]; }
    }
  }
  
  /*
  HEAPSORT
  */
  public static void HeapSort(long[] array) {
    int n = array.length;
    PriorityQueue<Long> pq = new PriorityQueue<>();
    for (int i = 0; i < n; i++) {
      pq.add(array[i]);
    }
    int i = 0;
    while(!pq.isEmpty()) {
      array[i++] = pq.remove();
    }
  }
  
  /*
  TREE SORT
  add later, needs a Tree class
  */
  
  /*
  SHELLSORT
  */
  public static void shellSort(long[] array) {
    int n = array.length;
    int gap = n/2;
    long key; int j;
    while (gap > 0) {
      for (int i = gap; i < n; i++) {
        key = array[i]; j = i;
        while ( j >= gap && array[j-gap] > key ) {
          array[j] = array[j-gap];
          j = j - gap;
        }
        array[j] = key;
      }
      if (gap==1) { break; }
    }
  }
  
  /*
  BUCKET SORT
  add later, don't have base code for it yet
  */
  
  /*
  RADIX SORT
  */ 
  public static void radixSort(long[] array) {
    int n = array.length;
    int sig = String.valueOf(maxElement(a)).length();
    int k = 9;
    int[] count = new int[k+1];
    long[] b = new long[n];
    for (int i = 0; i < sig; i++) {
      count[i] = 0;
    }
    double place = Math.pow(10,i);
    for (int j = 0; j <= n-1; j++) {
      int aj = (int) (array[j]/(place)) % 10;
      count[aj] = count[aj] + 1;
    }
    for (int i = 1; i <= k; i++) {
      count[i] = count[i] + count[i-1];
    }
    for (int j = n-1; j >= 0; j--) {
      int aj = (int) (a[j]/place) % 10;
      b[count[aj]-1] = array[j];
      count[aj] = count[aj] - 1;
    }
    System.arraycopy(b, 0, array, 0, n);
  }
  
  /*
  COUNTING SORT
  */ 
  public static void radixSort(long[] array) {
    int k = maxElement(array);
    int n = array.length
    int[] count = new int[k+1];
    long[] b = new long[n];
    for (int j = 0; j <= n-1; j++) {
      count[ ((int) array[j]) ] = count[ ((int) array[j]) ] + 1;
    }
    for (int i = 1; i <= k; i++) {
      count[i] = count[i] + count[i-1];
    }
    for (int j = n-1; j >= 0; j--) {
      b[ count[ ((int) array[j]) ] - 1 ] = array[j];
      count[ ((int) array[j]) ] = count[ ((int) array[j]) ] - 1;
    }
    System.arraycopy(b, 0, array, 0, n);
  }
  
  /*
  HELPER ALGORITHMS
  SWAP -  swap two compared elements
  RANDOM INT - choose a random parser between a high and low # of an array
  RAN ARRAY - create a random array for sorting
  */
  
  public static void swap(long[] a, int x, int y) {
    long temp = a[x];
    a[x] = a[y];
    a[y] = temp;
  }
  
  public static int randomInt(int low, int high) {
    return Math.round(low + (Math.random() * (high - low)));
  }
  
  public static long[] randomArray(int n) {
    long[] array = new long[n];
    for(int i = 0; i < n; i++) {
      array[i] = Math.round(Math.random() * n * 10);
    }
    return array;
  }
  
  public static void main(String[] args) {
    // test sorting algorithms
  }
}
