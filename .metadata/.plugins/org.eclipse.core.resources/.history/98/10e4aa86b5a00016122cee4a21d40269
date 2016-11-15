/**
 * @author (your name goes here)
 *
 */
public class ComparePaintings {

	public ComparePaintings(){}; // constructor.
	
	// Load the image, construct the hash table, count the colors.
	ColorHash countColors(String filename, int bitsPerPixel) {
		ImageLoader newImg = new ImageLoader(filename);
		//get img height and width, loop through all pixels and get colorKey. Increment because it's safer
		ColorHash ch = new ColorHash(3, bitsPerPixel, "Quadradic Probing", 0.45);
		for (int i = 0; i < newImg.getHeight(); i++) {
			for (int j = 0; i < newImg.getWidth(); i++) {
				ColorKey newKey = newImg.getColorKey(j, i, bitsPerPixel);
				ch.increment(newKey);
			}
		}
		return ch; // Change this to return the real result.
	}

//	Starting with two hash tables of color counts, compute a measure of similarity based on the cosine distance of two vectors.
	double compare(ColorHash painting1, ColorHash painting2) {
		// Implement this.
		//make each a vector, compare 1 to 2 and return 
		return 1.0; // Change this to return the actual similarity value.
	}

	//	A basic test for the compare method: S(x,x) should be 1.0, so you should compute the similarity of an image with itself and print out the answer. If it comes out to be 1.0, that is a good sign for your implementation so far.
	void basicTest(String filename) {
		// Implement this.
		// countColors twice, make vector for each, compare
	}

	//		Using the three given painting images and a variety of bits-per-pixel values, compute and print out a table of collision counts in the following format:
	void CollisionTests() {
		// Implement this.				
	}
	
	void fullSimilarityTests() {
		ImageLoader mona = new ImageLoader("MonaLisa.jpg");
		ImageLoader starry = new ImageLoader("StarryNight.jpg");
		ImageLoader christina = new ImageLoader("ChristinasWorld.jpg");
	}
		
// This simply checks that the images can be loaded, so you don't have 
// an issue with missing files or bad paths.
	void imageLoadingTest() {
		ImageLoader mona = new ImageLoader("MonaLisa.jpg");
		ImageLoader starry = new ImageLoader("StarryNight.jpg");
		ImageLoader christina = new ImageLoader("ChristinasWorld.jpg");
		System.out.println("It looks like we have successfully loaded all three test images.");

	}
	/**
	 * This is a basic testing function, and can be changed.
	 */
	public static void main(String[] args) {
		ComparePaintings cp = new ComparePaintings();
		cp.imageLoadingTest();
	}

}
