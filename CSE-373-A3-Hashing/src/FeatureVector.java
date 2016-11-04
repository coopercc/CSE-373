/**
 * @author (your name goes here)
 *
 */
public class FeatureVector {

	/**
	 * FeatureVector is a class for storing the results of
	 * counting the occurrences of colors.
	 * <p>
	 * Unlike the hash table, where the information can be
	 * almost anyplace with the array(s) (due to hashing), in the FeatureVector,
	 * the colors are in their numerical order and each count
	 * is in the array position for its color.
	 * <p>
	 * Besides storing the information, the class provides methods
	 * for getting the information (getTheCounts) and for computing
	 * the similarity between two vectors (cosineSimilarity).
	 */
	long[] colorCounts;
	int bitsPerPixel;
	int keySpaceSize;

	/**
	 * Constructor of a FeatureVector.
	 * 
	 * This creates a FeatureVector instance with an array of the
	 * proper size to hold a count for every possible element in the key space.
	 * 
	 * @param bpp	Bits per pixel. This controls the size of the vector.
	 * 				The keySpace Size is 2^k where k is bpp.
	 * 
	 */
	public FeatureVector(int bpp) {
		keySpaceSize = 1 << bpp;
		bitsPerPixel = bpp;
		colorCounts = new long[keySpaceSize];
	}

	public void getTheCounts(ColorHash ch) {
		for (int i = 0; i < keySpaceSize; i++) {
			try {
				ColorKey tempKey = new ColorKey(i, bitsPerPixel);
				ResponseItem ri = ch.colorHashGet(tempKey);
				colorCounts[i] = ri.value;
			} catch (Exception e) {
				colorCounts[i] = 0;
			}
			
		}
		// You should implement this method.
		// It will go through all possible key values in order,
		// get the count from the hash table and put it into this feature vector.
	}
	public double cosineSimilarity(FeatureVector b) {

		double dotProduct = 0.0;
		double aMag = 0.0;
		double bMag = 0.0;
		for (int i = 0; i < colorCounts.length; i++) {
			dotProduct += colorCounts[i] * b.colorCounts[i];
			aMag += colorCounts[i] * colorCounts[i];
			bMag += b.colorCounts[i] * b.colorCounts[i];
		}
		System.out.println("dotProd = " + dotProduct + ", aMag = " + Math.sqrt(aMag) + ", bMag = " + Math.sqrt(bMag));
		return dotProduct / (Math.sqrt(aMag) * Math.sqrt(bMag));
	}

	/**
	 * Optional main method for your own tests of these methods.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
