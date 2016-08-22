package com.vallco.downjoz.utils;

/**
 * Created by Javad on 8/5/2016.
 */
public class DrawerViewModel {
    int icon;
    String name;

    public DrawerViewModel(String name , int icon){
        this.name=name;
        this.icon=icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }
}
