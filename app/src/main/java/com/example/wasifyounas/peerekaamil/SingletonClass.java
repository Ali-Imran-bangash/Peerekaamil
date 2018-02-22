package com.example.wasifyounas.peerekaamil;

import java.util.ArrayList;

/**
 * Created by Wasif Younas on 2/8/2018.
 */

public class SingletonClass {


    private static SingletonClass singletonClass;
    private SingletonClass ()
    {}

    private  ArrayList<Bookmarks> bookmarkList = new ArrayList<>();

    public ArrayList<Bookmarks> getBookmarkList() {
        return bookmarkList;
    }

    public void setBookmarkList(ArrayList<Bookmarks> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }

    public static SingletonClass getInstance ()
    {

         if (singletonClass == null)
         {
             singletonClass = new SingletonClass();
         }


        return singletonClass;
    }
}
