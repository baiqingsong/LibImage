package com.dawn.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import com.arthenica.mobileffmpeg.FFmpeg;

public class LImageUtil {
    /**
     * 显示图片
     * @param imageUrl 图片地址
     * @param imageView 图片控件
     */
    public static void showImage(String imageUrl, ImageView imageView) {
        Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    /**
     * Compress an image using FFmpeg
     * @param inputPath Path to the input image
     * @param outputPath Path to save the compressed image
     * @param quality Compression quality (0-100)
     */
    public static void compressImage(String inputPath, String outputPath, int quality) {
        String command = String.format("-i %s -q:v %d %s", inputPath, quality, outputPath);
        int rc = FFmpeg.execute(command);

        if (rc == Config.RETURN_CODE_SUCCESS) {
            Log.i("FFmpeg", "Compression successful");
        } else if (rc == Config.RETURN_CODE_CANCEL) {
            Log.i("FFmpeg", "Compression cancelled");
        } else {
            Log.i("FFmpeg", String.format("Compression failed with rc=%d", rc));
        }
    }

    /**
     * Compress an image using FFmpeg
     * @param inputPath Path to the input image
     * @param outputPath Path to save the compressed image
     * @param width Desired width
     * @param height Desired height
     */
    public static void compressImage(String inputPath, String outputPath, int width, int height) {
        String command = String.format("-i %s -vf scale=%d:%d -q:v 2 %s", inputPath, width, height, outputPath);
//        String command = String.format("-i %s -vf scale=%d:-1,crop=%d:%d -q:v %d %s", inputPath, width, width, height, quality, outputPath);
        int rc = FFmpeg.execute(command);

        if (rc == Config.RETURN_CODE_SUCCESS) {
            Log.i("FFmpeg", "Compression successful");
        } else if (rc == Config.RETURN_CODE_CANCEL) {
            Log.i("FFmpeg", "Compression cancelled");
        } else {
            Log.i("FFmpeg", String.format("Compression failed with rc=%d", rc));
        }
    }

    /**
     * Crop an image using FFmpeg
     * @param inputPath Path to the input image
     * @param outputPath Path to save the cropped image
     * @param top Margin from the top
     * @param bottom Margin from the bottom
     * @param left Margin from the left
     * @param right Margin from the right
     */
    public static void cropImage(String inputPath, String outputPath, int top, int bottom, int left, int right) {
        String command = String.format("-i %s -vf crop=in_w-%d-%d:in_h-%d-%d:%d:%d %s", inputPath, left, right, top, bottom, left, top, outputPath);
        int rc = FFmpeg.execute(command);

        if (rc == Config.RETURN_CODE_SUCCESS) {
            Log.i("FFmpeg", "Cropping successful");
        } else if (rc == Config.RETURN_CODE_CANCEL) {
            Log.i("FFmpeg", "Cropping cancelled");
        } else {
            Log.i("FFmpeg", String.format("Cropping failed with rc=%d", rc));
        }
    }

    /**
     * Copy an image from drawable to SD card
     * @param context Application context
     * @param drawableId Drawable resource ID
     * @param fileName Name of the file to save on SD card
     * @throws IOException If an I/O error occurs
     */
    public static void copyDrawableToSDCard(Context context, int drawableId, String fileName) throws IOException {
        // Get the input stream from drawable resource
        InputStream inputStream = context.getResources().openRawResource(drawableId);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        // Create the output file on SD card
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/image");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);

        // Write the bitmap to the output file
        FileOutputStream outputStream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     * 保存Bitmap到文件
     * @param bitmap Bitmap
     * @param file 文件
     * @param format 格式
     * @param quality 质量
     * @throws IOException IO异常
     */
    public static void saveBitmapToFile(Bitmap bitmap, File file, Bitmap.CompressFormat format, int quality) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(format, quality, out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 保存Bitmap到文件
     * @param bitmap 图片
     * @param filePath 文件路径
     * @throws IOException IO异常
     */
    public static void saveBitmapToFile(Bitmap bitmap, String filePath) throws IOException {
        saveBitmapToFile(bitmap, new File(filePath), Bitmap.CompressFormat.JPEG, 100);
    }

}
