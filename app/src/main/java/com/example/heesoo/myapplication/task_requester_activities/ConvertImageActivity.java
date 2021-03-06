package com.example.heesoo.myapplication.task_requester_activities;

/*
This class allows us to manipulate the images that a user assigns to a task. There are methods to
convert a bitmap to a string and a string to a bitmap.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class ConvertImageActivity
{
    /*Reference taken from https://stackoverflow.com/questions/9224056/android-bitmap-to-base64-string
    on March 30, 2018 */
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