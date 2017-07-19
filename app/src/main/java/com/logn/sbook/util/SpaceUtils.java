package com.logn.sbook.util;

/**
 * Created by long on 2017/7/17.
 */

public class SpaceUtils {
    private SpaceUtils() {
    }

    public static String replaceSpace(String strWithSpace) {
        return strWithSpace.replaceAll(" ", "$#$");
    }

    public static String getSpaceBack(String strWithoutSpace) {
        return strWithoutSpace.replaceAll("$#$", " ");
    }
}
