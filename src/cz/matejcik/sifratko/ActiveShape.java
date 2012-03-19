package cz.matejcik.sifratko;


import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

public class ActiveShape {
	public ShapeDrawable shape;
	public double x, y, width, height;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
		shape.getPaint().setColor(color);
	}

	public int color;

	public ActiveShape (Shape shape, int color, double x, double y, double w, double h)
	{
		this.shape = new ShapeDrawable(shape);
		this.color = color;
		this.shape.getPaint().setColor(color);
		this.x = x; this.y = y; this.width = w; this.height = h;
	}

	public void reshape (int pxwidth, int pxheight) {
		Rect r = new Rect();
		r.left = (int)(pxwidth * x);
		r.top = (int)(pxheight * y);
		r.right = (int)(pxwidth * x + pxwidth * width);
		r.bottom = (int)(pxheight * y + pxheight * height);
		shape.setBounds(r);
	}
}
