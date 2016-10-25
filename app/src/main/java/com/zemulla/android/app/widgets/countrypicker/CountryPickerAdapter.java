package com.zemulla.android.app.widgets.countrypicker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zemulla.android.app.R;
import com.zemulla.android.app.model.account.country.Country;


    public class CountryPickerAdapter extends ArrayAdapter<Country> {
    private LayoutInflater mLayoutInflater;

    /**
     * Constructor
     *
     * @param context Context
     */
    public CountryPickerAdapter(Context context) {
        super(context, 0);
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * Drop down item view
     *
     * @param position    position of item
     * @param convertView View of item
     * @param parent      parent view of item's view
     * @return covertView
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        try {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.item_country, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.intl_phone_edit__country__item_image);
                viewHolder.mNameView = (TextView) convertView.findViewById(R.id.intl_phone_edit__country__item_name);
                viewHolder.mDialCode = (TextView) convertView.findViewById(R.id.intl_phone_edit__country__item_dialcode);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
                Country country = getItem(position);
            viewHolder.mImageView.setImageResource(getFlagResource(country));
            viewHolder.mNameView.setText(country.getCountryName());
            viewHolder.mDialCode.setText(String.format("+%s", country.getCallingCode()));
        } catch (Exception e) {
            Log.d("error","Exception");
        }
        return convertView;
    }

    /**
     * Drop down selected view
     *
     * @param position    position of selected item
     * @param convertView View of selected item
     * @param parent      parent of selected view
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Country country = getItem(position);

        if (convertView == null) {
            convertView = new ImageView(getContext());
        }
        ((ImageView) convertView).setImageResource(getFlagResource(country));
        return                  convertView;
    }

    /**
     * Fetch flag resource by Country
     *
     * @param country Country
     * @return int of resource | 0 value if not exists
     */
    private int getFlagResource(Country country) {

        try {

        } catch (Exception e) {
            Log.d("error","Exception");
            Log.d("test", country.getISO2Name().toLowerCase().trim());
        }

        return getContext().getResources().getIdentifier("country_" + country.getISO2Name().toLowerCase().trim(), "drawable", getContext().getPackageName());
    }


    /**
     * View holder for caching
     */
    private static class ViewHolder {
        public ImageView mImageView;
        public TextView mNameView;
        public TextView mDialCode;
    }
}
