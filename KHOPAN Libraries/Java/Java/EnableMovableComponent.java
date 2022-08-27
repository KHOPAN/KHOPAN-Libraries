import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EnableMovableComponent extends MouseAdapter
{
	public Insets DragableComponentInsets = new Insets(0, 0, 0, 0);
	public Dimension DragableComponentSnapSize = new Dimension(1, 1);
	public Insets DragableComponentEdgeInsets = new Insets(0, 0, 0, 0);
	public Boolean DragableComponentChangeCursor = true;
	public Boolean DragableComponentAutoLayout = false;

	@SuppressWarnings("rawtypes")
	public Class DestClass;
	public Component Source;
	public Component Dest;
	public Component DestComponent;

	public Point PointPressed;
	public Point PointLocation;

	public Cursor OriginalCursor;
	public Boolean AutoScroll;
	public Boolean PotentialDrag;
	
	public EnableMovableComponent()
	{
		
	}
	
	@SuppressWarnings("rawtypes")
	public EnableMovableComponent(Class DestClass, Component... Component)
	{
		this.DestClass = DestClass;
		registerComponent(Component);
	}
	
	public EnableMovableComponent(Component DestComponent, Component... Component)
	{
		this.DestComponent = DestComponent;
		registerComponent(Component);
	}
	
	public boolean isAutoLayout()
	{
		return DragableComponentAutoLayout;
	}
	
	public void setAutoLayout(Boolean AutoLayout)
	{
		this.DragableComponentAutoLayout = AutoLayout;
	}

	public boolean isChangeCursor()
	{
		return DragableComponentChangeCursor;
	}

	public void setChangeCursor(Boolean ChangeCursor)
	{
		this.DragableComponentChangeCursor = ChangeCursor;
	}
	
	public Insets getDragInsets()
	{
		return DragableComponentInsets;
	}

	public void setDragInsets(Insets DragInsets)
	{
		this.DragableComponentInsets = DragInsets;
	}

	public Insets getEdgeInsets()
	{
		return DragableComponentEdgeInsets;
	}

	public void setEdgeInsets(Insets EdgeInsets)
	{
		this.DragableComponentEdgeInsets = EdgeInsets;
	}

	public void deregisterComponent(Component... Component)
	{
		for(Component Component1 : Component)
		{
			Component1.removeMouseListener(this);
		}
	}

	public void registerComponent(Component... Component)
	{
		for(Component Component1 : Component)
		{
			Component1.addMouseListener(this);
		}
	}

	public Dimension getSnapSize()
	{
		return DragableComponentSnapSize;
	}

	public void setSnapSize(Dimension SnapSize)
	{
		if(SnapSize.width < 1 ||  SnapSize.height < 1)
		{
			throw new IllegalArgumentException("Snap sizes must be greater than 0");
		}

		this.DragableComponentSnapSize = SnapSize;
	}

	@Override
	public void mousePressed(MouseEvent event)
	{
		Source = event.getComponent();
		int WidthSize  = Source.getSize().width - DragableComponentInsets.left - DragableComponentInsets.right;
		int HeightSize = Source.getSize().height - DragableComponentInsets.top - DragableComponentInsets.bottom;
		Rectangle Rectangle = new Rectangle(DragableComponentInsets.left, DragableComponentInsets.top, WidthSize, HeightSize);

		if(Rectangle.contains(event.getPoint()))
		{
			setupForDragging(event);
		}
	}

	private void setupForDragging(MouseEvent event)
	{
		Source.addMouseMotionListener(this);
		PotentialDrag = true;

		if(DestComponent != null)
		{
			Dest = DestComponent;
		}
		
		else if(DestClass == null)
		{
			Dest = Source;
		}
		
		else
		{
			Dest = SwingUtilities.getAncestorOfClass(DestClass, Source);
		}

		PointPressed = event.getLocationOnScreen();
		PointLocation = Dest.getLocation();

		if(DragableComponentChangeCursor)
		{
			OriginalCursor = Source.getCursor();
			Source.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}

		if(Dest instanceof JComponent)
		{
			JComponent JComponent = (JComponent) Dest;
			AutoScroll = JComponent.getAutoscrolls();
			JComponent.setAutoscrolls( false );
		}
	}

	@Override
	public void mouseDragged(MouseEvent event)
	{
		Point DraggedPoint = event.getLocationOnScreen();
		int DragX = getDragDistance(DraggedPoint.x, PointPressed.x, DragableComponentSnapSize.width);
		int DragY = getDragDistance(DraggedPoint.y, PointPressed.y, DragableComponentSnapSize.height);

		int XLocation = PointLocation.x + DragX;
		int YLocation = PointLocation.y + DragY;

		while(XLocation < DragableComponentEdgeInsets.left)
		{
			XLocation += DragableComponentSnapSize.width;
		}

		while(YLocation < DragableComponentEdgeInsets.top)
		{
			YLocation += DragableComponentSnapSize.height;
		}

		Dimension Dimension = getBoundingSize(Dest);

		while(XLocation + Dest.getSize().width + DragableComponentEdgeInsets.right > Dimension.width)
		{
			XLocation -= DragableComponentSnapSize.width;
		}

		while(YLocation + Dest.getSize().height + DragableComponentEdgeInsets.bottom > Dimension.height)
		{
			YLocation -= DragableComponentSnapSize.height;
		}
		
		Dest.setLocation(XLocation, YLocation);
	}

	public int getDragDistance(int Larger, int Smaller, int SnapSize)
	{
		int HalfWay = SnapSize / 2;
		int Drag = Larger - Smaller;
		Drag += (Drag < 0) ? - HalfWay : HalfWay;
		Drag = (Drag / SnapSize) * SnapSize;

		return Drag;
	}

	private Dimension getBoundingSize(Component Source)
	{
		if(Source instanceof Window)
		{
			GraphicsEnvironment GraphicsEnvironment1 = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Rectangle Rectangle = GraphicsEnvironment1.getMaximumWindowBounds();
			return new Dimension(Rectangle.width, Rectangle.height);
		}
		
		else
		{
			return Source.getParent().getSize();
		}
	}

	@Override
	public void mouseReleased(MouseEvent event)
	{
		if(!PotentialDrag)
		{
			return;
		}

		Source.removeMouseMotionListener(this);
		PotentialDrag = false;

		if(DragableComponentChangeCursor)
		{
			Source.setCursor(OriginalCursor);
		}

		if(Dest instanceof JComponent)
		{
			((JComponent)Dest).setAutoscrolls(AutoScroll);
		}

		if(DragableComponentAutoLayout)
		{
			if(Dest instanceof JComponent)
			{
				((JComponent) Dest).revalidate();
			}
			
			else
			{
				Dest.validate();
			}
		}
	}
}