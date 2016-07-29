package com.zemulla.android.app.transaction;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zemulla.android.app.R;

import java.util.ArrayList;

/**
 * Created by krishnakumar on 29-07-2016.
 */

public class CyberSourceAdapter extends RecyclerView.Adapter<CyberSourceAdapter.ViewHolder> {

    private ArrayList<CyberSourceBean> mainBean;

    public CyberSourceAdapter(ArrayList<CyberSourceBean> data) {
        this.mainBean = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trans_cyber_source, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CyberSourceBean data = mainBean.get(position);
        holder.title.setText(data.getZemullaTransDate());
        holder.genre.setText(data.amount);
        holder.year.setText(data.getFirstName());
    }

    @Override
    public int getItemCount() {
        return mainBean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }
}
