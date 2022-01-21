package com.example.photoeditor;
import android.graphics.Matrix;
import java.util.ArrayList;

public class IEditImageInfo {
    public int brightness = 0;
    public int contrast = 0;
    public Matrix matrix = null;
    public ArrayList<IPathInfo> patharr;
    public int saturation = 256;
    public ArrayList<ITextInfo> textarr = null;
}

