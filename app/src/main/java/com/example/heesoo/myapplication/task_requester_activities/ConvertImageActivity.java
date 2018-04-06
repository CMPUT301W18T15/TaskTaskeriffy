package com.example.heesoo.myapplication.task_requester_activities;

/**
 * Created by riyariya on 2018-03-29.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class ConvertImageActivity
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream);
        Log.e("Compression Size:",String.valueOf(outputStream.toByteArray().length));
        if(outputStream.toByteArray().length > 65536){
            Log.e("Compression Error:","Image too big, even after compression");
            return null;
        }
        String inputString = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        return inputString;
    }

}