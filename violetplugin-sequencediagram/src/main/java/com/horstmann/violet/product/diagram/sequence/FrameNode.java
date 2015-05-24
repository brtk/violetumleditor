package com.horstmann.violet.product.diagram.sequence;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import org.omg.CORBA.FREE_MEM;

import com.horstmann.violet.framework.theme.ThemeManager;
import com.horstmann.violet.product.diagram.abstracts.node.IResizableNode;
import com.horstmann.violet.product.diagram.abstracts.node.RectangularNode;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.product.diagram.common.NoteNode;

public class FrameNode extends RectangularNode implements IResizableNode{
	
	@Override
	public void draw(Graphics2D g2) {
		// Backup current color;
        Color oldColor = g2.getColor();
        super.draw(g2);
        Shape shape = getFrameStringShape();
        g2.setColor(getBackgroundColor());
        g2.fill(shape);
        g2.setColor(getBorderColor());
        g2.draw(shape);		 
        g2.draw(getShape()); // the main shape is transparent
        g2.setColor(getTextColor());
        text.draw(g2, shape.getBounds());
        
        g2.setColor(oldColor);
        System.err.print(">>>>>>>>>>");
	}
	
	private Shape getFrameStringShape() {
		Rectangle2D textBounds = getFrameStringBounds();
		Rectangle2D frameBounds = getBounds();
		GeneralPath path = new GeneralPath();
		path.moveTo(frameBounds.getX(), frameBounds.getY());
		path.lineTo(frameBounds.getX() + textBounds.getWidth(), frameBounds.getY());
		path.lineTo(frameBounds.getX() + textBounds.getWidth(), frameBounds.getY() + textBounds.getHeight() - textFrameCornerHeight);
		path.lineTo(frameBounds.getX() + textBounds.getWidth() - textFrameCornerHeight, frameBounds.getY() + textBounds.getHeight());
		path.lineTo(frameBounds.getX(), frameBounds.getY() + textBounds.getHeight());
		path.clone();
		return path;
	}
	
	private Rectangle2D getFrameStringBounds() {
		Rectangle2D bnds = null;
		if (text.getText().equals("")) {
			text.setText("AMZ"); // some text
			double h = text.getBounds().getHeight();
			bnds = new Rectangle2D.Double(0, 0, h, h);
			text.setText(""); // some text
		} else {
			double h = text.getBounds().getHeight();
			double w = text.getBounds().getWidth();
			bnds = new Rectangle2D.Double(0, 0, w, h);
		}
		return bnds;
	}
	
	@Override
	public Rectangle2D getBounds() {
		Rectangle2D textBounds = getFrameStringBounds();
		int maxH = Math.max(defaultHeight, (int) wantedSize.getHeight());
		int maxW = Math.max(defaultWidth, (int) wantedSize.getWidth());
		if (textBounds.getWidth() + 30 >= maxW) {
			maxW = (int)textBounds.getWidth() + 30;
		}
		Point2D loc = getLocation();
		return getGraph().getGridSticker().snap(new Rectangle2D.Double(loc.getX(), loc.getY(), maxW, maxH));
	}

	@Override
	public void setWantedSize(Rectangle2D size) {
		wantedSize = size;
		
	}
	
	@Override
    public FrameNode clone()
    {
        FrameNode cloned = (FrameNode) super.clone();
        cloned.text = text.clone();
        return cloned;
    }
	
	@Override
	public int getZ() {
		// Ensures that this kind of nodes is always on top
		return INFINITE_Z_LEVEL;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public MultiLineString getText() {
		return text;
	}

	public void setText(MultiLineString text) {
		this.text = text;
	}

	private Color color = new Color(1, 1, 1);
	private Rectangle2D wantedSize = new Rectangle2D.Double();
	private int defaultHeight = 60;
	private int defaultWidth = 100;
	private MultiLineString text = new MultiLineString();
	private final int textFrameCornerHeight = 10;
	private static int INFINITE_Z_LEVEL = 10000;
}
