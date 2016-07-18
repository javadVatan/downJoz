package com.vallco.downjoz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vallco.downjoz.R;
import com.vallco.downjoz.utils.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements View.OnClickListener {

    // private OnRecyclerViewItemClickListener<ViewModel> itemClickListener;

    private ArrayList<ViewModel> dataList ;
    private  Context mContext;

    public RecyclerAdapter(Context mContext, ArrayList<ViewModel> dataList) {
        this.dataList = dataList;
        this.mContext=mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fr_all_data_item_list, parent, false);
       /* v.setOnClickListener(this);*/
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.titleName_tv.setText(dataList.get(position).getText());
        holder.textId_tv.setText(dataList.get(position).getId());

        Picasso.with(mContext)
                .load(dataList.get(position).getImage())
/*
                .error(R.drawable.erorr)
                .placeholder(R.drawable.wait)
*/
                .centerCrop()
                .resize(200,150)
                .into(holder.imagePoster_iv);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View view) {
    /*    if (itemClickListener != null) {
            ViewModel model = (ViewModel) view.getTag();
            itemClickListener.onItemClick(view, model);
        }*/
    }

    /*public void add(ViewModel item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }*/
/*
    public void remove(ViewModel item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }*/
/*
    public void setOnItemClickListener(OnRecyclerViewItemClickListener<ViewModel> listener) {
        this.itemClickListener = listener;
    }*/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imagePoster_iv;
        public TextView textId_tv;
        public TextView titleName_tv;


        public ViewHolder(View itemView) {
            super(itemView);
            imagePoster_iv = (ImageView) itemView.findViewById(R.id.small_poster_iv);
            textId_tv = (TextView) itemView.findViewById(R.id.sdate_tv);
            titleName_tv = (TextView) itemView.findViewById(R.id.title_tv);


        }


    }
}
