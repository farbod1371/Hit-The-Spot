package com.example.elessar1992.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.elessar1992.test.Model.Item_;

import java.util.List;


/**
 * Created by yamasakitaishi on 2016/10/20.
 */

public class ExploreListAdapter extends ArrayAdapter<Item_> {
	private LayoutInflater layoutInflater_;

	// View lookup cache
	private static class ViewHolder {
		TextView Name;
		TextView Rate;
		TextView Genre;
		TextView Distance;
		TextView Description;
		TextView Lat;
		TextView Lng;
	}

	public ExploreListAdapter(Context context, int layout, List<Item_> objects) {
		super(context, layout, objects);
		layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		Item_ item_ = getItem(position);

		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag
		if (convertView == null) {
			// If there's no view to re-use, inflate a brand new view for row
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.item_list, parent, false);

			// Lookup view for data population
			viewHolder.Name = (TextView) convertView.findViewById(R.id.Name);
			viewHolder.Rate = (TextView) convertView.findViewById(R.id.Rate);
			viewHolder.Genre = (TextView) convertView.findViewById(R.id.Genre);
			viewHolder.Distance = (TextView) convertView.findViewById(R.id.Distance);
			viewHolder.Description = (TextView) convertView.findViewById(R.id.Description);
			viewHolder.Lat = (TextView) convertView.findViewById(R.id.Lat);
			viewHolder.Lng = (TextView) convertView.findViewById(R.id.Lng);

			// Cache the viewHolder object inside the fresh view
			convertView.setTag(viewHolder);
		} else {
			// View is being recycled, retrieve the viewHolder object from tag
			viewHolder = (ViewHolder) convertView.getTag();
		}


		// Get each data from "item_"
		String Name = item_.getVenue().getName();
		double Rate = item_.getVenue().getRating();
		String Genre = item_.getVenue().getCategories().get(0).getName();
		int Distance = item_.getVenue().getLocation().getDistance();
		String Description = item_.getTips().get(0).getText();
		Double Lat = item_.getVenue().getLocation().getLat();
		Double Lng = item_.getVenue().getLocation().getLng();


		// Populate the data from the data object via the viewHolder object
		// into the template view.

		viewHolder.Name.setText(Name);
		viewHolder.Rate.setText(String.valueOf(Rate));
		viewHolder.Genre.setText(Genre);
		viewHolder.Distance.setText(String.valueOf(Distance));
		viewHolder.Description.setText(Description);
		viewHolder.Lat.setText(String.valueOf(Lat));
		viewHolder.Lng.setText(String.valueOf(Lng));

		// Return the completed view to render on screen
		return convertView;
	}
}