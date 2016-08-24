package com.zemulla.android.app.model.user.notification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zemulla.android.app.R;
import com.zemulla.android.app.widgets.TfTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by raghavthakkar on 24-08-2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final Context context;


    private List<Notification> items;

    public NotificationAdapter(List<Notification> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setItems(List<Notification> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification item = items.get(position);
        holder.setDetails(item);

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }


    class NotificationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        CircleImageView imageView;
        @BindView(R.id.textView)
        TfTextView textView;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDetails(Notification item) {
            Glide.clear(imageView);
            Glide.with(context)
                    .load(item.getProfilePicWithURL())
                    .centerCrop()
                    .placeholder(R.drawable.ic_action_account_circle)
                    .crossFade()
                    .into(imageView);


            textView.setText(item.getMessage() != null ? item.getMessage() : "No Message");


        }
    }


}