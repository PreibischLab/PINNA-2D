package com.preibisch.pinna2d.tools;

public class CV2 {
    public static void loadOpenCVNativeLibrary() {
        nu.pattern.OpenCV.loadShared();
    }

    public static void main(String[] args) {
        loadOpenCVNativeLibrary();
    }
}
