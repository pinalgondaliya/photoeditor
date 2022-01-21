package com.example.photoeditor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.Closeable;
import java.io.IOException;

public class FontUtils {
    public static Typeface sTypeface;

    public static Typeface loadFont(Context context, String str) {
        sTypeface = Typeface.createFromAsset(context.getAssets(), str);
        return sTypeface;
    }

    public static void setFont(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof TextView) {
                ((TextView) childAt).setTypeface(sTypeface);
            } else if (childAt instanceof ViewGroup) {
                setFont((ViewGroup) childAt);
            }
        }
    }

    public static void setFont(View view) {
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(sTypeface);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: android.graphics.Typeface} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.io.FileOutputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.io.FileOutputStream} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static Typeface getTypefaceFromRaw(Context r7, int r8) {
        /*
            r0 = 0
            android.content.res.Resources r1 = r7.getResources()     // Catch:{ IOException -> 0x0070, all -> 0x006c }
            java.io.InputStream r8 = r1.openRawResource(r8)     // Catch:{ IOException -> 0x0070, all -> 0x006c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            r1.<init>()     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            java.io.File r7 = r7.getCacheDir()     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            r1.append(r7)     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            java.lang.String r7 = "/tmp"
            r1.append(r7)     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            r1.append(r2)     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            java.lang.String r7 = ".raw"
            r1.append(r7)     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            java.lang.String r7 = r1.toString()     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            r1.<init>(r7)     // Catch:{ IOException -> 0x0069, all -> 0x0066 }
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x0063 }
            r2.<init>(r1)     // Catch:{ IOException -> 0x0063 }
            int r3 = r8.available()     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            byte[] r3 = new byte[r3]     // Catch:{ IOException -> 0x005e, all -> 0x005b }
        L_0x003a:
            int r4 = r8.read(r3)     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            if (r4 <= 0) goto L_0x0045
            r5 = 0
            r2.write(r3, r5, r4)     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            goto L_0x003a
        L_0x0045:
            android.graphics.Typeface r0 = android.graphics.Typeface.createFromFile(r7)     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            java.io.File r3 = new java.io.File     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            r3.<init>(r7)     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            r3.delete()     // Catch:{ IOException -> 0x005e, all -> 0x005b }
            tryClose(r2)
            tryClose(r1)
            tryClose(r8)
            goto L_0x0081
        L_0x005b:
            r7 = move-exception
            r0 = r2
            goto L_0x0083
        L_0x005e:
            r7 = move-exception
            r6 = r2
            r2 = r0
            r0 = r6
            goto L_0x0074
        L_0x0063:
            r7 = move-exception
            r2 = r0
            goto L_0x0074
        L_0x0066:
            r7 = move-exception
            r1 = r0
            goto L_0x0083
        L_0x0069:
            r7 = move-exception
            r1 = r0
            goto L_0x0073
        L_0x006c:
            r7 = move-exception
            r8 = r0
            r1 = r8
            goto L_0x0083
        L_0x0070:
            r7 = move-exception
            r8 = r0
            r1 = r8
        L_0x0073:
            r2 = r1
        L_0x0074:
            r7.printStackTrace()     // Catch:{ all -> 0x0082 }
            tryClose(r0)
            tryClose(r1)
            tryClose(r8)
            r0 = r2
        L_0x0081:
            return r0
        L_0x0082:
            r7 = move-exception
        L_0x0083:
            tryClose(r0)
            tryClose(r1)
            tryClose(r8)
            goto L_0x008e
        L_0x008d:
            throw r7
        L_0x008e:
            goto L_0x008d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ratiocrop.FontUtils.getTypefaceFromRaw(android.content.Context, int):android.graphics.Typeface");
    }

    private static void tryClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
