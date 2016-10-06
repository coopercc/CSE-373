import java.awt.image.BufferedImage;
import java.util.EmptyStackException;
import java.lang.IndexOutOfBoundsException;

public class BufferedImageStack
{
	private int size; //number of contents in the array, also acts as the point where to push/pop
	private BufferedImage[] arr; //array holding the images
	
	public BufferedImageStack() 
	{
		arr = new BufferedImage[2];
		size = 0;
	}
	
	/*
	 * Adds the BufferedImage param passed in to the stack
	 * if the array is full then it doubles the size then adds the param to the array
	 */
	public void push(BufferedImage img) 
	{
		System.out.println(size);
		if (size == arr.length) 
		{
			BufferedImage[] temp = new BufferedImage[arr.length * 2];
			System.arraycopy(arr, 0, temp, 0, arr.length);
			arr = temp;
		}
		arr[size] = img;
		size++;
		System.out.println(size);
	}
	
	/*
	 * Returns the last item pushed into the array
	 * throws EmptyStackException if there are no images in the stack
	 */
	public BufferedImage pop() 
	{
		if (size == 0) 
		{
			throw new EmptyStackException();
		} else 
		{
			BufferedImage temp =  arr[size - 1];
			arr[size - 1] = null;
			size--;
			return temp;
			//return last item in array and remove
		}
	}
	
	/*
	 * returns true if the number of images in the stack is 0,
	 * otherwise returns false
	 */
	public boolean isEmpty() 
	{
		if (size == 0) 
		{
			return true;
		} else 
		{
			return false;
		}
	}
	
	/*
	 * accepts an index of the array and returns the Image object if there is one.
	 * if there is no image then it returns an out of bounds exception
	 */
	public BufferedImage get(int index) 
	{
		if (arr[index] == null) 
		{
			throw new IndexOutOfBoundsException();
		} else 
		{
			return arr[index];
		}
	}
	
	/*
	 * Returns the number of BufferedImage objects in the stack
	 */
	public int getSize()
	{
		return size;
	}
	
	/*
	 * Returns an int of the size of the array holding the Image objects
	 */
	public int getArraySize()
	{
		return arr.length;
	}
}
