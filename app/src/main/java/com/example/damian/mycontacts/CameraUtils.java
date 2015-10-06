package com.example.damian.mycontacts;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Admin on 06.10.2015.
 */
public class CameraUtils {

    public static void setPic(ImageView imageView, String mCurrentPhotoPath, Context context) {
        if (mCurrentPhotoPath != null) {
            String realPath = getRealPathFromURI(context, Uri.parse(mCurrentPhotoPath));
            // Get the dimensions of the View
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(realPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            if (targetW != 0) {
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;

                Bitmap bitmap = BitmapFactory.decodeFile(realPath, bmOptions);
                imageView.setImageBitmap(bitmap);
            } else {
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inPurgeable = true;
                Bitmap bitmap = BitmapFactory.decodeFile(realPath, bmOptions);
                imageView.setImageBitmap(bitmap);
            }
            float degree = getExifOrientation(getRealPathFromURI(context, Uri.parse(mCurrentPhotoPath)));
            if (degree != 0) {
                imageView.setRotation(degree);
            }
        }
    }

    public static int getExifOrientation(String filePath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException ex) {
            // Log.e(UIUtils.class.getSimpleName(), "cannot read exif", ex);
        } catch (IllegalArgumentException e) {
            // Log.e(UIUtils.class.getSimpleName(), "cannot read exif", e);
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                // We only recognize a subset of orientation tag values.
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        if (context == null) return "";
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                if (cursor.getCount() == 0) {
                    return "";
                }
                return cursor.getString(columnIndex);
            }
            return "";
        } finally {
            closeCloseable(cursor);
        }
    }

    private static void closeCloseable(Closeable... closeabls) {
        for (Closeable closeable : closeabls) {
            if (closeable != null) try {
                closeable.close();
            } catch (IOException ignored) {
                //We really don't need to see exception
            }
        }
    }
}
