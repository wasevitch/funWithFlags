package com.example.ouzin.drapeaux.db.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by ouzin on 27/09/2017.
 */

public class SerialBitmap implements Serializable {

    private Bitmap bitmap;

    public SerialBitmap(byte[] data) {
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
