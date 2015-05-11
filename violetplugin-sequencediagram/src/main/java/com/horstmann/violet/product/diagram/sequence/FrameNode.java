package com.horstmann.violet.product.diagram.sequence;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import org.omg.CORBA.FREE_MEM;

import com.horstmann.violet.product.diagram.abstracts.node.IResizableNode;
import com.horstmann.violet.product.diagram.abstracts.node.RectangularNode;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;

public class FrameNode extends RectangularNode implements IResizableNode{
	
	private Rectangle2D getFrameStringBounds() {
		Rectangle2D bnds = null;
		if (frameString.getText().equals("")) {
			frameString.setText("AMZ"); // some text
			double h = frameString.getBounds().getHeight();
			bnds = new Rectangle2D.Double(0, 0, h, h);
			frameString.setText(""); // some text
		} else {
			double h = frameString.getBounds().getHeight();
			double w = frameString.getBounds().getWidth();
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
		return new Rectangle2D.Double(loc.getX(), loc.getY(), maxW, maxH);
	}

	@Override
	public void setWantedSize(Rectangle2D size) {
		wantedSize = size;
		
	}
	
	private Rectangle2D wantedSize = new Rectangle2D.Double();
	private int defaultHeight = 60;
	private int defaultWidth = 100;
	private MultiLineString frameString = new MultiLineString();
}
