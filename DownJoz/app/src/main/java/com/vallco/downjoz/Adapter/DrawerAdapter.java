package com.vallco.downjoz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vallco.downjoz.R;
import com.vallco.downjoz.utils.DrawerViewModel;

import java.util.List;

/**
 * Created by Javad on 8/5/2016.
 */
public class DrawerAdapter extends BaseAdapter {
    private Context mContext;
    private List<DrawerViewModel> viewModelList;
    private LayoutInflater layoutInflater;
    private DrawerItemOnclick DrawerItemOnclick;

    public DrawerAdapter(Context mContext, DrawerItemOnclick DrawerItemOnclick, List<DrawerViewModel> viewModelList) {
        super();
        this.mContext = mContext;

        layoutInflater = LayoutInflater.from(mContext);
        this.viewModelList = viewModelList;
        this.DrawerItemOnclick = DrawerItemOnclick;

    }

    public class ViewHolder {
        TextView tvName;
        ImageView imgIcon;
        //      LinearLayout linearLayoutView;
    }

    @Override
    public int getCount() {
        return viewModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.item_drawer_list, null);
            holder.tvName = (TextView) view.findViewById(R.id.itemDrawerText);
            holder.imgIcon = (ImageView) view.findViewById(R.id.itemDrawerImage);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        int id = viewModelList.get(position).getIcon();

        holder.imgIcon.setImageResource(id);

        holder.tvName.setText(viewModelList.get(position).getName());


        final View finalView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerItemOnclick.onItemClick(finalView, position);
            }
        });

        return view;
    }

    public interface DrawerItemOnclick {
        void onItemClick(View view, int position);
    }
}
