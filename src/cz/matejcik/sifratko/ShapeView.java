package cz.matejcik.sifratko;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class ShapeView extends View {
	private ArrayList<ActiveShape> drawables = new ArrayList<ActiveShape>();
	
	public ShapeView(Context context, AttributeSet attrs) { super(context, attrs); }

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		for (ActiveShape d : drawables) d.reshape(w,h);
	}

	public void addShape (ActiveShape a)
	{
		drawables.add(a);
	}

	@Override
	protected void onDraw (Canvas canvas) {
		super.onDraw(canvas);
		for (ActiveShape d : drawables) d.shape.draw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = MeasureSpec.getSize(heightMeasureSpec);
		if (w == 0) w = 200;
		if (h == 0) h = 200;
		int min = Math.min(w,h);
		//setMeasuredDimension(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		setMeasuredDimension(min, min);
	}

	@Override
	protected int getSuggestedMinimumHeight() {
		return 10;
	}

	@Override
	protected int getSuggestedMinimumWidth() {
		return 10;
	}
}
