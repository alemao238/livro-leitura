package com.mrcllw.livroleitura.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Marcello on 20/11/2017.
 */

public class Base64Util {

    public static String bitmapToBase64(String fileName) {
        Bitmap source = BitmapFactory.decodeFile(fileName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        source.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
