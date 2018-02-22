package com.example.wasifyounas.peerekaamil;

/**
 * Created by Wasif Younas on 2/16/2018.
 */

public class HotBeverage {

    private static String note = "Hot Beverage";
    public static  class HotTea {

        static int myvar=5;
        static int myvar2=10;

        public static int getMyvar2() {
            return myvar2;
        }

        public static void setMyvar2(int myvar2) {
            HotTea.myvar2 = myvar2;
        }

        public static int getMyvar() {
            return myvar;
        }

        public static void setMyvar(int myvar) {
            HotTea.myvar = myvar;
        }

        public String displayOutput() {
            return  "Hot Tea says: " + note+myvar+myvar2;
        }
    }

}
