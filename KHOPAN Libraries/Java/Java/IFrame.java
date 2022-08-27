import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class IFrame
{
	public static JFrame JFrame = new JFrame();
	
	public static final int NOTHING = 0;
	public static final int HIDE = 1;
	public static final int DISPOSE = 2;
	public static final int EXIT = 3;
	public static final int CENTER = 0;
	public static final int TOPLEFT = 1;
	public static final int TOPRIGHT = 2;
	public static final int BOTTOMLEFT = 3;
	public static final int BOTTOMRIGHT = 4;

	public static int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	public static void createFrame(String title, int closeOperation, boolean isResizable, int WIDTH, int HEIGHT, int position)
	{
		try
		{
			TooLargeSizeException.ifGreater(SCREEN_WIDTH, WIDTH, SCREEN_HEIGHT, HEIGHT);
		}
		
		catch(TooLargeSizeException event)
		{
			event.printStackTrace();
			System.exit(0);
		}
		
		int LOCATION_X = 0;
		int LOCATION_Y = 0;
		
		if(position == 0)
		{
			LOCATION_X = (SCREEN_WIDTH / 2) - (WIDTH / 2);
			LOCATION_Y = (SCREEN_HEIGHT / 2) - (HEIGHT / 2);
		}
		
		if(position == 1)
		{
			LOCATION_X = 0;
			LOCATION_Y = 0;
		}
		
		if(position == 2)
		{
			LOCATION_X = SCREEN_WIDTH - WIDTH;
			LOCATION_Y = 0;
		}
		
		if(position == 3)
		{
			LOCATION_X = 0;
			LOCATION_Y = SCREEN_HEIGHT - HEIGHT;
		}
		
		if(position == 4)
		{
			LOCATION_X = SCREEN_WIDTH - WIDTH;
			LOCATION_Y = SCREEN_HEIGHT - HEIGHT;
		}
		
		JFrame.setDefaultCloseOperation(closeOperation);
		JFrame.setLayout(null);
		JFrame.setResizable(isResizable);
		JFrame.setSize(WIDTH, HEIGHT);
		JFrame.setLocation(LOCATION_X, LOCATION_Y);
		JFrame.setVisible(true);
		JFrame.setTitle(title);
	}
	
	public static void add(java.awt.Component comp)
	{
		JFrame.add(comp);
	}
	
	public static void remove(java.awt.Component comp)
	{
		JFrame.remove(comp);
	}
	
	public static void setContentBackground(Color background)
	{
		JFrame.getContentPane().setBackground(background);
	}
	
	public static class TooLargeSizeException extends Exception
	{
		protected static final long serialVersionUID = 1L;
		
		TooLargeSizeException(String text)
		{
			super(text);
		}
		
		public static void ifGreater(int index1, int index2, int index3, int index4) throws TooLargeSizeException
		{
			if(index1 < index2 || index3 < index4)
			{
				throw new TooLargeSizeException("Size Larger than " + index1 + ", " + index3);
			}
		}
	}
}
