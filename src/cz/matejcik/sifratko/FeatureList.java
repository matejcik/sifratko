package cz.matejcik.sifratko;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import cz.matejcik.sifratko.components.Semafor;

import java.util.ArrayList;

public class FeatureList extends ListActivity implements AdapterView.OnItemClickListener {
	
	private ArrayList<Component> components = new ArrayList<Component>();
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		components.add(new Semafor());
		
		setListAdapter(new ArrayAdapter<Component>(this, R.layout.menuitem, components));
		//setContentView(R.layout.main);

		getListView().setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		Component component = (Component)adapterView.getItemAtPosition(position);
		startActivity(new Intent(this, component.getClass()));
	}
}
