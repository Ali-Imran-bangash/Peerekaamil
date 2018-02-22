package com.example.wasifyounas.peerekaamil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Wasif Younas on 1/26/2017.
 */

public class CustomFragmentList extends ArrayAdapter<Bookmarks>  {

    private final Activity context;
    private ArrayList<Bookmarks> originalList;

    DBhandler dBhandler;

    private ArrayList<Bookmarks> shopList= new ArrayList<>();
    public CustomFragmentList(Activity a, ArrayList<Bookmarks> spl) {
        super(a, R.layout.list_item, spl);
        this.context = a;
        shopList=spl;
        this.originalList = new ArrayList<>();
        this.originalList.addAll(spl);
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);

        dBhandler = DBhandler.getinstance(context);

        TextView tVBookmark = (TextView) rowView.findViewById(R.id.textViewListItem);
        Button btnRemove = (Button) rowView.findViewById(R.id.btnRemoveChillerFromListView);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!shopList.isEmpty()) {
                    dBhandler.deleteBookMark(String.valueOf(shopList.get(position).getBookmark_PageNo()));
                    GlobalBus.getBus().post(new Events.PageRemoveListener(0));

                }

            }
        });

        if (!shopList.isEmpty()) {

            String pageNo = "Page " +String.valueOf(shopList.get(position).getBookmark_PageNo() +1);
            tVBookmark.setText(pageNo);
        }
        return rowView;
    }

}