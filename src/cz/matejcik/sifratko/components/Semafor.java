package cz.matejcik.sifratko.components;

import android.graphics.Color;
import android.graphics.drawable.shapes.ArcShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import cz.matejcik.sifratko.ActiveShape;
import cz.matejcik.sifratko.Component;
import cz.matejcik.sifratko.R;
import cz.matejcik.sifratko.ShapeView;

import java.util.ArrayList;

public class Semafor extends Component implements View.OnTouchListener {
	
	private static final int COLOR_DEFAULT = Color.BLUE;
	private static final int COLOR_HI = Color.RED;
	private static final int COLOR_SELECTED = Color.WHITE;

	private static final double OFS = 0.05;
	private static final double[] OFS_X = {OFS, OFS / 1.41, 0, -OFS / 1.41, -OFS, -OFS / 1.41, 0, OFS / 1.41};
	private static final double[] OFS_Y = {0, OFS / 1.41, OFS, OFS / 1.41, 0, -OFS / 1.41, -OFS, -OFS / 1.41};
	
	private ActiveShape[] shapes = new ActiveShape[8];
	private ArrayList<Integer> selection = new ArrayList<Integer>(3);
	
	public static final String SEMAFOR = " ABCDEFG__HIKLMN___OPQRS____TUY-_____-JV______WX_______Z";

	private ShapeView shapeView;
	private TextView letterView;
	
	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);

		setContentView(R.layout.semafor);

		shapeView = (ShapeView)findViewById(R.id.semafor_view);
		shapeView.setOnTouchListener(this);
		
		letterView = (TextView)findViewById(R.id.semafor_pismeno);
		
		for (int i = 0; i < 8; i++) {
			int angle = i * 45;
			ArcShape arc = new ArcShape((float)(angle - 20), 40);
			ActiveShape as = new ActiveShape(arc, COLOR_DEFAULT, 0.1 + OFS_X[i], 0.1 + OFS_Y[i], 0.8, 0.8);
			shapeView.addShape(as);
			shapes[i] = as;
		}
	}

	@Override
	public String toString() {
		return "Semafor";
	}
	
	private void refreshView()
	{
		for (ActiveShape a : shapes) a.setColor(COLOR_DEFAULT);
		for (int i : selection)
			if (i > -1) shapes[i].setColor(COLOR_SELECTED);
		shapeView.invalidate();
		
		if (selection.size() > 1 || selection.size() == 1 && activeidx > -1) {
			int a = selection.get(0);
			int b = (activeidx > -1) ? activeidx : selection.get(1);
			a = (a+6) % 8; b = (b+6) % 8;
			int min = Math.min(a,b);
			int max = Math.max(a,b);
			int ltr = (min << 3) + max;
			if (ltr < SEMAFOR.length()) letterView.setText(String.valueOf(SEMAFOR.charAt(ltr)));
			else letterView.setText("");
		} else letterView.setText("");
	}

	private void highlightShape (int idx) {
		shapes[idx].setColor(COLOR_HI);
	}

	private void toggleShape (int idx) {
		// is selected?
		if (selection.contains(idx)) selection.remove(new Integer(idx));
		else selection.add(idx);
		if (selection.size() > 2) selection.remove(0);
	}

	private int activeidx = -1;

	public boolean onTouch(View view, MotionEvent motionEvent) {
		switch (motionEvent.getAction()) {
			case MotionEvent.ACTION_CANCEL:
				refreshView();
				activeidx = -1;
				break;
			case MotionEvent.ACTION_DOWN:
				if (selection.size() > 1) selection.remove(0);
			case MotionEvent.ACTION_MOVE:
				refreshView();
				activeidx = indexFromCoords(motionEvent);
				highlightShape(activeidx);
				break;
			case MotionEvent.ACTION_UP:
				activeidx = indexFromCoords(motionEvent);
				toggleShape(activeidx);
				activeidx = -1;
				refreshView();
				break;
			default:
				return false;
		}
		return true;
	}

	private int indexFromCoords(MotionEvent motionEvent) {
		double x = motionEvent.getX() - shapeView.getWidth()/2;
		double y = motionEvent.getY() - shapeView.getHeight()/2;
		double angle = Math.atan2(y, x);
		double quad = Math.round(angle * 4 / Math.PI);
		int idx = (int)(quad + 8) % 8;
		return idx;
	}
}
