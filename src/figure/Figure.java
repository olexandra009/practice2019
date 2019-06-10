package figure;

import java.awt.Color;
import java.awt.Point;
import java.util.concurrent.CopyOnWriteArrayList;

public class Figure {
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int width;
	private int height;
	private Color color;
	private String type;

	public Figure(int x1, int y1, int x2, int y2, Color color) {
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		width=x2-x1;
		height=y2-y1;
		this.color = color;
	}
	
	public Figure(Point p1, Point p2, Color color) {
		this.x1=(int) p1.getX();
		this.y1=(int) p1.getY();
		this.x2=(int) p2.getX();
		this.y2=(int) p2.getY();
		width=x2-x1;
		height=y2-y1;
		this.color = color;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getX1() {
		return x1;
	}


	public void setX1(int x1) {
		this.x1 = x1;
	}


	public int getY1() {
		return y1;
	}


	public void setY1(int y1) {
		this.y1 = y1;
	}


	public int getX2() {
		return x2;
	}


	public void setX2(int x2) {
		this.x2 = x2;
	}


	public int getY2() {
		return y2;
	}


	public void setY2(int y2) {
		this.y2 = y2;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}
	
	

	@Override
	public String toString() {
		return "Figure [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + ", width=" + width + ", height="
				+ height + ", color=" + color + "]";
	}

	public void setLocation(int x1, int y1) {
		this.x1=x1;
		this.y1=y1;
		this.x2=width+x1;
		this.x2=height+x2;
	}

	public boolean hadInsidePoint(Point point) {
		if (point.x > this.getX1() && point.x < this.getX2() && point.y > this.getY1() && point.y < this.getY2()) 
			return true;
		return false;
	}

	public boolean colideWithotherFigures(CopyOnWriteArrayList<Figure> figures) {
		for(Figure f:figures)
			if(f.colide(this))
				return true;
		return false;
	}

	public boolean colide(Figure figure) {
		if(getX1()<figure.getX1()&&getX2()>figure.getX1()&&getY1()<figure.getY1()&&getY2()>figure.getY1())
			return true;
		else if (getX1()<figure.getX1()+figure.getWidth()&&getX2()>figure.getX1()+figure.getWidth()&&getY1()<figure.getY1()&&getY2()>figure.getY1())
			return true;
		else if(getX1()<figure.getX1()&&getX2()>figure.getX1()&&getY1()<figure.getY1()+figure.getHeight()&&getY2()>figure.getY1()+figure.getHeight())
			return true;
		if(getX1()<figure.getX1()+figure.getWidth()&&getX2()>figure.getX1()+figure.getWidth()&&getY1()<figure.getY1()+figure.getHeight()&&getY2()>figure.getY1()+figure.getHeight())
			return true;
		return false;
	}

}
