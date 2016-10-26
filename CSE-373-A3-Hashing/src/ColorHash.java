

/**
 * @author (put your name here)
 *
 */
public class ColorHash {
	// Implement this class, including whatever data members you need and all
	// of the public methods below.  You may create any number of private methods
	// if you find them to be helpful.
	
	ColorKey[] keyArr;
	long[] valueArr;
	String resMethod; //"Linear Probing" and "Quadratic Probing"
	int bpp;
	double rehashFactor;
	int currElements;
	int collisions;
	
	
	public ColorHash(int tableSize, int bitsPerPixel, String collisionResolutionMethod, double rehashLoadFactor){
		keyArr = new ColorKey[tableSize];
		valueArr = new long[tableSize];
		bpp = bitsPerPixel;
		resMethod = collisionResolutionMethod;
		rehashFactor = rehashLoadFactor;
		currElements = 0;
	}
	
	public ResponseItem colorHashPut(ColorKey key, long value) {
		//place at key.HashCode % tableSize
		//increment currElements if you don't switch to increment if exists, increment, else do thing and c
		long pos = key.hashCode() % getTableSize();
		return null;
	}
	
	public ResponseItem increment(ColorKey key) {
		return null;
		//getCount(key)
	}
	public ResponseItem colorHashGet(ColorKey key)  throws Exception{return null;}
	
	public long getCount(ColorKey key) {
		return getValueAt(Arrays.asList(keyArr).indexOf(key));
	}
	
	public ColorKey getKeyAt(long tableIndex) {
		return keyArr[tableIndex];
	}
	
	public long getValueAt(long tableIndex) {
		return valueArr[tableIndex];
	}
	
	public double getLoadFactor() {
		return (double)currElements / getTableSize();
	}
	
	public int getTableSize() {
		return keyArr.length;
	}
	public void resize() {
		
	}


}
