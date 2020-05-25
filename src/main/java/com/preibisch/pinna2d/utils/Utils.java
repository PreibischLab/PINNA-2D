package com.preibisch.pinna2d.utils;

public class Utils {

    public static String toString(double[] val) {
        String string = "{ ";
        for (int i = 0; i < val.length;i++) {
            string +=  String.valueOf(val[i]);
            if(i<val.length-1)  string +=  " - ";
        }
        string+= " }";
        return string;
    }
}
