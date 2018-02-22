package com.example.wasifyounas.peerekaamil;

/**
 * Created by ALI IMRAN BANGASH on 7/10/2017.
 */

public class Events {

    public static class PageChangeListener {

        private int pageNo;

        public PageChangeListener(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageNo() {
            return pageNo;
        }
    }


    public static class PageRemoveListener {

        private int pageNo;

        public PageRemoveListener(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageNo() {
            return pageNo;
        }
    }


}
