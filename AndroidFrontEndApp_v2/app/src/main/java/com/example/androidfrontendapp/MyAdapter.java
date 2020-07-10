package com.example.androidfrontendapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Item> items;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener
    {
        void OnItemClick(int position, String text);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mlistener = listener;
    }

    public MyAdapter(Context context, ArrayList<Item> items)
    {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item currentItem = items.get(position);
        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        int likeCount = currentItem.getLikes();
        holder.mCreatorView.setText(creatorName);
        String info = context.getString(R.string.var) + likeCount;
        holder.mLikesView.setText(info);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mImageView;
        public TextView mCreatorView;
        public TextView mLikesView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mCreatorView = itemView.findViewById(R.id.text_view_creator);
            mLikesView = itemView.findViewById(R.id.text_view_likes);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistener != null)
                    {
                        int position = getAdapterPosition();
                        String text = mCreatorView.getText().toString();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            mlistener.OnItemClick(position, text);
                        }
                    }
                }
            });
        }
    }
}
