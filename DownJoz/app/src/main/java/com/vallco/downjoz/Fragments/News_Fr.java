package com.vallco.downjoz.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vallco.downjoz.DataBase.DbHandler;
import com.vallco.downjoz.R;

/**
 * Created by Javad on 7/13/2016.
 */
public class News_Fr extends Fragment {
    DbHandler dbHandler;
    private View currentView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_news, container, false);
        dbHandler = new DbHandler(getContext());

        // name  =dbHandler.getAllData();

          TextView textView= (TextView) currentView.findViewById(R.id.textTest);

        dbHandler.openDataBase();
dbHandler.getAllData();
/*
   ArrayList <ViewModel> viewModels= dbHandler.getAllData();
        textView.append(viewModels.get(1).getBookName());
*/

        return currentView;
    }

    public static Fragment newInstance() {
        return new News_Fr();
    }

    public String[] refresh() {
        dbHandler.openDataBase();
        int countDb = dbHandler.getCount();

        String[] name = new String[countDb];
        for (int i = 0; i < countDb; i++) {
            name[i] = dbHandler.getAllData().get(i).getId() +""+ dbHandler.getAllData().get(i).getAuthor();
        }
        dbHandler.closeDataBase();
        return name;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView textView= (TextView) currentView.findViewById(R.id.textTest);
        for (int i=0;i<refresh().length;i++) {
            textView.append(refresh()[i] +"\n");
        }
    }
}
