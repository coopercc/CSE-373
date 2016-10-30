

/**
 * @author Cooper Cain
 * @desc ColorHash holds the color count for each color key
 */

public class ColorHash {
	
	HashEntry[] ColorArr; //
	String resMethod; //How to resolve collisions
	double rehashFactor; //the max factor before hash table needs to be rehashed
	int currElements; //number of elements in table currently
	int collisions; //number of collisions in a get/put
	
	/*
	 * Takes in a table size, bitsPerPixel, how to resolve collisions, and the rehash factor 
	 * and sets them in the class
	 */
	public ColorHash(int tableSize, int bitsPerPixel, String collisionResolutionMethod, double rehashLoadFactor){
		ColorArr = new HashEntry[tableSize];
		resMethod = collisionResolutionMethod;
		rehashFactor = rehashLoadFactor;
		currElements = 0;
		collisions = 0;
	}
	
	/*
	 * takes in a key and a value and inserts or replaces the key and rehashes 
	 * the table as needed. Returns a ResponseItem that has the value, collisions, and whether
	 * or not the key value was updated and if the table was rehashed
	 */

	public ResponseItem colorHashPut(ColorKey key, long value) {
		collisions = 0;

		FindObject temp = findIndex(key, ColorArr);
		boolean inTable = temp.inTable;
		int insertPos = temp.index;
		if (inTable) {
			System.out.println("Replacing @ " + ColorArr[insertPos]);
			ColorArr[insertPos].value = value;
			currElements++;
			return new ResponseItem(value, collisions, false, true);
		} else {
			boolean didRehash = false;
			double currentFactor = ((double)currElements + 1) / getTableSize();
			if (currentFactor >= rehashFactor || currentFactor >= 1) {
				didRehash = true;
				resize();
			}
			if (didRehash) {
				//find calculation  again
				FindObject rehashTemp = findIndex(key, ColorArr);
				insertPos = rehashTemp.index;
			} 
			System.out.println(insertPos);
			ColorArr[insertPos] = new HashEntry(key, value);
			currElements++;
			return new ResponseItem(value, collisions, didRehash, false);
		}
		
	}

	/*
	 * Finds the index of the key in the hash table using the specified probing method
	 * and returns the index of the key or the empty space and if the index has a key in it
	 */
	private FindObject findIndex(ColorKey key, HashEntry[] arr) {
		int startIndex = key.hashCode() % arr.length;
		boolean newKey = false;
		boolean inTable = false;
		int count = 0;
		int insertPos = 0;
		while (!newKey && !inTable && count < arr.length) {
			int tempPos;
			if (resMethod == "Linear Probing") {
				tempPos = (startIndex + count) % arr.length;
			} else {
				tempPos = (startIndex + count*count) % arr.length;
			}
			
			if (arr[tempPos] == null) {
				insertPos = tempPos;
				newKey = true;
			} else if (arr[tempPos].key.equals(key)) {
				inTable = true;
				insertPos = tempPos;
			} else {
				count++;
				collisions++;
			}
		}
		return new FindObject(insertPos, inTable);
	}
	
	/*
	 * Takes in a key, adds 1 to the value if it is already there, and if it isn't
	 * then adds the key to the table with value of 1. Also rehashes if it needs to when
	 * adding the key. Returns a ResponseItem with value, collisions, and if rehashed or if
	 * updated
	 */
	
	public ResponseItem increment(ColorKey key) {
		collisions = 0;
		FindObject temp = findIndex(key, ColorArr);
		if (temp.inTable) {
			long count = ColorArr[temp.index].value;
			ColorArr[temp.index] = new HashEntry(key, count + 1);
			return new ResponseItem(count + 1, collisions, false, true);
		} else {
			boolean didRehash = false;
			double currentFactor = (double)currElements / getTableSize();
			if (currentFactor >= rehashFactor || currentFactor >= 1) {
				didRehash = true;
				resize();
			}
			temp = findIndex(key, ColorArr);
			ColorArr[temp.index] = new HashEntry(key, 1);
			return new ResponseItem(1, collisions, didRehash, false);
		}
	}
	
	/*
	 * Takes a key and returns the ResponseItem with the count, # of collisions, and if 
	 * rehashed or if updated value. IF the table doesn't have the key, then throws
	 * missing colorKey exception
	 */
	public ResponseItem colorHashGet(ColorKey key)  throws Exception {
		collisions = 0;
		long count = getCount(key);
		if (count == 0) {
			throw new Exception("Missing ColorKey Exception");
		} else {
			return new ResponseItem(count, collisions, false, false);
		}
	}
	
	//returns the value of the given key. If the key doesn't exist, returns 0
	public long getCount(ColorKey key) {
		FindObject temp = findIndex(key, ColorArr);
		long count =  getValueAt(temp.index);
		if (count == -1) {
			return 0;
		} else {
			return count;
		}

	}
	
	//returns the key at the given index
	public ColorKey getKeyAt(int tableIndex) {
		return ColorArr[tableIndex].key;		
	}
	
	//returns the value at the given index
	public long getValueAt(int tableIndex) {
		if (ColorArr[tableIndex] == null) {
			return -1L;
		} else {
			return ColorArr[tableIndex].value;
		}
	}
	
	//returns the current Load Factor
	public double getLoadFactor() {
		return (double)currElements / getTableSize();
	}
	
	//returns the size of the hash table
	public int getTableSize() {
		return ColorArr.length;
	}
	
	/*
	 * rehashes the table by increasing the size to the next prime number
	 * at least 2x as big as the current table, then puts the key/value pairs from the 
	 * current array into the new array at their new index.
	 */
	public void resize() {
		System.out.println("Rehashing");
		int currSize = getTableSize();
		boolean isPrime = false;
		int newSize = currSize * 2;
		while (!isPrime) {
			if (IsPrime.isPrime(newSize)) {
				isPrime = true;
			} else {
				newSize++;
			}
		}

		HashEntry[] newArr = new HashEntry[newSize];
		System.out.println("new size is: " + newArr.length);
		for (int i = 0; i < getTableSize() ; i++) {
			if (ColorArr[i] != null) {
				HashEntry tempEntry = ColorArr[i];
				ColorKey tempKey = tempEntry.key;
				FindObject temp = findIndex(tempKey, newArr);
				System.out.println("Placing new Key "+ tempKey +" @ " + temp.index);
				newArr[temp.index] = new HashEntry(tempKey, tempEntry.value);
			}
		}
		ColorArr = newArr;
	}
}
