package com.arktech.mindvalley_waqasansari_android_test;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class CacheTextInFile {
    Context context;

    //Read cached Text from file
    public static String getCachedText(Context context, String filename) {
        File file = new File(context.getCacheDir(), filename);
        return getCachedTextFromFile(file);
    }

    private static String getCachedTextFromFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            return getCachedTextFromStream(inputStream);
        } catch(Exception ex) {
            return null;
        }
    }

    private static String getCachedTextFromStream(InputStream inputStream) {
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = bufferedReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }

        return text.toString();
    }






    //Cache text into file
    public static boolean cacheText(Context context, String filename, String text) {
        File file = new File(context.getCacheDir(), filename);
        return putTextInFile(file, text);
    }

    private static boolean putTextInFile(File file, String text) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            return putTextInStream(outputStream, text);
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private static boolean putTextInStream(OutputStream outputStream, String text) {
        OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputWriter);
        boolean success = false;

        try {
            bufferedWriter.write(text);
            success = true;
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        return success;
    }
}
