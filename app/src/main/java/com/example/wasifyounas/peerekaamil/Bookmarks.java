package com.example.wasifyounas.peerekaamil;

/**
 * Created by Wasif Younas on 2/8/2018.
 */

public class Bookmarks {

    private int Bookmark_ID = 0;
    private int Bookmark_PageNo = 0;

    public Bookmarks(int bookmark_ID, int bookmark_PageNo) {
        Bookmark_ID = bookmark_ID;
        Bookmark_PageNo = bookmark_PageNo;
    }

    public int getBookmark_PageNo() {
        return Bookmark_PageNo;
    }

    public void setBookmark_PageNo(int bookmark_PageNo) {
        Bookmark_PageNo = bookmark_PageNo;
    }
}
