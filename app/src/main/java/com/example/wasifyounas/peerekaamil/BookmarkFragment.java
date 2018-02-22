package com.example.wasifyounas.peerekaamil;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by Wasif Younas on 2/9/2018.
 */

public class BookmarkFragment extends DialogFragment {

    ListView listView ;
    private CustomFragmentList adapter;
    DBhandler dBhandler;
    ArrayList<Bookmarks> bookmarksArrayList ;

    @Override
    public void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getDialog().requestWindowFeature(Window.FEATURE_LEFT_ICON);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bookmark_fragment , null);
       // getDialog() ;
      //  getDialog().getWindow().setStatusBarColor(getResources().getColor(R.color.colorDarkGrey));

       // getDialog().setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON , R.drawable.bookmarksicon);

        dBhandler = DBhandler.getinstance(getActivity());
        bookmarksArrayList = dBhandler.getBookmarks();
        listView = (ListView) view.findViewById(R.id.listViewBookmarks);
        adapter = new CustomFragmentList(getActivity() ,bookmarksArrayList );

        listView.setAdapter(adapter);
        GlobalBus.getBus().register(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                GlobalBus.getBus().post(new Events.PageChangeListener(bookmarksArrayList.get(position).getBookmark_PageNo()));
                                            }
                                        }
        );
        return view;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Subscribe
    public void refreshListView(Events.PageRemoveListener pageRemoveListener)
    {
        bookmarksArrayList = dBhandler.getBookmarks();
        adapter = new CustomFragmentList(getActivity() ,bookmarksArrayList );
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
