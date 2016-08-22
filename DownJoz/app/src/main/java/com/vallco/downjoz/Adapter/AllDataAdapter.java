package com.vallco.downjoz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vallco.downjoz.DataBase.DbHandler;
import com.vallco.downjoz.R;
import com.vallco.downjoz.utils.ViewModel;

import java.util.ArrayList;

public class AllDataAdapter extends RecyclerView.Adapter<AllDataAdapter.ViewHolder> implements View.OnClickListener {

    // private OnRecyclerViewItemClickListener<ViewModel> itemClickListener;

    private ArrayList<ViewModel> dataList;
    private Context mContext;


    public AllDataAdapter(Context mContext, ArrayList<ViewModel> dataList) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fr_all_data_list, parent, false);
       /* v.setOnClickListener(this);*/
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DbHandler dbHandler = new DbHandler(mContext);
        dbHandler.openDataBase();

        holder.titleName_tv.setText(dataList.get(position).getBookName());
        holder.tv_versionType.setText(dataList.get(position).getTypeVersion());
        holder.tv_authorName.setText(dataList.get(position).getAuthor());
        holder.tv_priceProduct.setText("قیمت: " + dataList.get(position).getPriceProduct());
        Picasso.with(mContext).load(dataList.get(position).getImageUrl())
/*
                .error(R.drawable.erorr)
                .placeholder(R.drawable.wait)
*/
                .into(holder.imagePoster_iv);

        if(dbHandler.checkId(dataList.get(holder.getAdapterPosition()).getId())){
            holder.icAddFavorite.setImageResource(R.drawable.ic_bookmark_black_24dp);
        }else {
            holder.icAddFavorite.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }
        holder.icAddFavorite.setOnClickListener(this);

        holder.icDownload.setOnClickListener(this);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        switch (view.getId()) {
            case R.id.ic_add_favorite:
                manageFavorite(viewHolder);
                break;
            case R.id.ic_download:
                Toast.makeText(mContext, viewHolder.getAdapterPosition() + "", Toast.LENGTH_SHORT).show();

                break;

        }
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
        public ImageView imagePoster_iv, icAddFavorite, icDownload;
        public TextView tv_versionType;
        public TextView titleName_tv, tv_authorName, tv_priceProduct;


        public ViewHolder(View itemView) {
            super(itemView);
            icAddFavorite = (ImageView) itemView.findViewById(R.id.ic_add_favorite);
            icDownload = (ImageView) itemView.findViewById(R.id.ic_download);
            icDownload.setTag(this);
            icAddFavorite.setTag(this);
            imagePoster_iv = (ImageView) itemView.findViewById(R.id.image_book);
            tv_versionType = (TextView) itemView.findViewById(R.id.tv_versionType);
            titleName_tv = (TextView) itemView.findViewById(R.id.tv_title_book);
            tv_priceProduct = (TextView) itemView.findViewById(R.id.tv_priceProduct);
            tv_authorName = (TextView) itemView.findViewById(R.id.tv_authorName);


        }


    }

    public void manageFavorite(ViewHolder holder) {
        DbHandler dbHandler = new DbHandler(mContext);
        dbHandler.openDataBase();

        if (!dbHandler.checkId(dataList.get(holder.getAdapterPosition()).getId())) {

            dbHandler.insert(
                    dataList.get(holder.getAdapterPosition()).getId(),
                    dataList.get(holder.getAdapterPosition()).getBookName(),
                    dataList.get(holder.getAdapterPosition()).getPriceProduct(),
                    dataList.get(holder.getAdapterPosition()).getAuthor(),
                    dataList.get(holder.getAdapterPosition()).getProductDescription(),
                    dataList.get(holder.getAdapterPosition()).getTypeVersion(),
                    dataList.get(holder.getAdapterPosition()).getImageUrl());

            holder.icAddFavorite.setImageResource(R.drawable.ic_bookmark_black_24dp);

        } else if (dbHandler.checkId(dataList.get(holder.getAdapterPosition()).getId())) {

            dbHandler.delete(dataList.get(holder.getAdapterPosition()).getId() ) ;

            holder.icAddFavorite.setImageResource(R.drawable.ic_bookmark_border_black_24dp);

        }
        dbHandler.closeDataBase();
    }
}
