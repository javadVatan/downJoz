package com.vallco.downjoz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vallco.downjoz.DataBase.DbHandler;
import com.vallco.downjoz.R;
import com.vallco.downjoz.utils.ViewModel;

import java.util.ArrayList;

/**
 * Created by Javad on 7/27/2016.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<ViewModel> dataFavorite;
    private Context mContext;

    public FavoriteAdapter(Context mContext, ArrayList<ViewModel> dataFavorite) {
        this.mContext = mContext;
        this.dataFavorite = dataFavorite;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fr_all_data_list, parent, false);
       /* v.setOnClickListener(this);*/
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DbHandler dbHandler = new DbHandler(mContext);
        dbHandler.openDataBase();

        holder.titleName_tv.setText(dataFavorite.get(position).getBookName());
        holder.tv_versionType.setText(dataFavorite.get(position).getTypeVersion());
        holder.tv_authorName.setText(dataFavorite.get(position).getAuthor());
        holder.tv_priceProduct.setText("قیمت: " + dataFavorite.get(position).getPriceProduct());
        Picasso.with(mContext).load(dataFavorite.get(position).getImageUrl())
/*
                .error(R.drawable.erorr)
                .placeholder(R.drawable.wait)
*/
                .into(holder.imagePoster_iv);

        if (dbHandler.checkId(dataFavorite.get(holder.getAdapterPosition()).getId())) {
            holder.icAddFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            holder.icAddFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        holder.icAddFavorite.setOnClickListener(this);

        holder.icDownload.setOnClickListener(this);
    }


    @Override
    public int getItemCount() {
        return dataFavorite.size();
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
    }

    public void manageFavorite(ViewHolder holder) {
        Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.fade_in);
        DbHandler dbHandler = new DbHandler(mContext);


try {
    dbHandler.openDataBase();
    if (dbHandler.checkId(dataFavorite.get(holder.getAdapterPosition()).getId())) {

        dbHandler.delete(dataFavorite.get(holder.getAdapterPosition()).getId());
        dataFavorite.remove(holder.getAdapterPosition()).getId();
        holder.itemView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
    dbHandler.closeDataBase();
}catch (Exception   e){
    Toast.makeText(mContext,e.toString(),Toast.LENGTH_LONG).show();
}
    }

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
}
