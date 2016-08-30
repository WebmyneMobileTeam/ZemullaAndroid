package com.zemulla.android.app.widgets.countrypicker;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.zemulla.android.app.R;
import com.zemulla.android.app.api.APIListener;
import com.zemulla.android.app.api.account.CountryAPI;
import com.zemulla.android.app.model.account.country.Country;
import com.zemulla.android.app.model.account.country.CountryResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 03-08-2016.
 */
public class CountryPickerView extends RelativeLayout implements APIListener<CountryResponse> {

    @BindView(R.id.intl_phone_edit__country)
    Spinner spinner;
    @BindView(R.id.intl_phone_edit__phone)
    EditText editText;
    Unbinder unbinder;
    private CountryPickerAdapter mCountryPickerAdapter;
    private Country mSelectedCountry;
    private CountryAPI countryAPI;

    private CountryPickerListener countryPickerListener;

    public CountryPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        View root = inflate(getContext(), R.layout.intl_phone_input, this);
        unbinder = ButterKnife.bind(root);
        mCountryPickerAdapter = new CountryPickerAdapter(context);
        spinner.setAdapter(mCountryPickerAdapter);
        spinner.setOnItemSelectedListener(mCountrySpinnerListener);


    }


    public void fetchCountry() {
        countryAPI = new CountryAPI();
        countryAPI.getCountryAPI(this);
    }

    private AdapterView.OnItemSelectedListener mCountrySpinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mSelectedCountry = mCountryPickerAdapter.getItem(position);
            Log.d("test", mSelectedCountry.getCountryName());
            countryPickerListener.OnSelected(mSelectedCountry);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
        countryAPI.onDestory();
        if (countryPickerListener != null) {
            countryPickerListener = null;
        }
    }

    public void setCountryPickerListener(CountryPickerListener countryPickerListener) {
        this.countryPickerListener = countryPickerListener;
    }

    @Override
    public void onResponse(Response<CountryResponse> response) {
        if (response.isSuccessful()) {
            mCountryPickerAdapter.addAll(response.body().getResponseData().getData());
        } else {

        }
    }

    @Override
    public void onFailure(Call<CountryResponse> call, Throwable t) {
        countryPickerListener.OnFailed(t);
    }

    public interface CountryPickerListener {
        void OnFailed(Throwable t);

        void OnSelected(Country country);
    }


    public EditText getEditText() {
        return editText;
    }

    public String getPhoneNumber() {
        return editText.getText().toString().trim();
    }
}
