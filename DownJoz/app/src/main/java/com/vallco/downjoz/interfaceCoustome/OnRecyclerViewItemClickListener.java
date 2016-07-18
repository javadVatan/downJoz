package com.vallco.downjoz.interfaceCoustome;

import android.view.View;

public interface OnRecyclerViewItemClickListener<Model> {
    public void onItemClick(View view, Model model);
}