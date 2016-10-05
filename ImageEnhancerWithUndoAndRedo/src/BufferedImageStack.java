import java.awt.image.BufferedImage;
import java.util.EmptyStackException;
import java.lang.IndexOutOfBoundsException;

public class BufferedImageStack
{
	private int size;
	private BufferedImage[] arr;
	/*top of stack
	 * [ 0, 1, 2, 3] <- top of array
	 * 
	 */
	
	public BufferedImageStack() 
	{
		arr = new BufferedImage[2];
		size = 0;
	}
	
	public void push(BufferedImage img) 
	{
		System.out.println(size);
		if (size == arr.length) 
		{
			//create new array
			BufferedImage[] temp = new BufferedImage[arr.length * 2];
			System.arraycopy(arr, 0, temp, 0, arr.length);
			arr = temp;
		}
		arr[size] = img;
		size++;
		System.out.println(size);
	}
	
	public BufferedImage pop() 
	{
		if (size == 0) 
		{
			throw new EmptyStackException();
		} else 
		{
			BufferedImage temp =  arr[size];
			arr[size] = null;
			size--;
			return temp;
			//return last item in array and remove
		}
	}
	
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
	
	public int getSize()
	{
		return size;
	}
	
	public int getArraySize()
	{
		return arr.length;
	}
}
