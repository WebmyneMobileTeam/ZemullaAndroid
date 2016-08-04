package com.zemulla.android.app.api;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by raghavthakkar on 04-08-2016.
 */
public interface APIListener<T> {
    void onResponse(Response<T> response);

    void onFailure(Call<T> call, Throwable t);
}
