package com.example.heesoo.myapplication.Requester;

/**
 * Created by riyariya on 2018-03-29.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class ImageUtil
{
// Code taken from https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
    public static Bitmap convert(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap)
    {
        //Encoding to Base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        String inputString = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        byte[] input = inputString.getBytes();

        // Compression Algorithm taken from https://stackoverflow.com/questions/13981965/decompress-base64-string on April 4, 2018
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION, true);
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(stream, compresser);
        try {
            deflaterOutputStream.write(input);
            deflaterOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

}