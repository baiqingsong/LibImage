package com.dawn.libimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.dawn.image.LImageUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File sdCard = Environment.getExternalStorageDirectory();
        String output = sdCard.getAbsolutePath() + "/image/output.png";
        String input = sdCard.getAbsolutePath() + "/image/input.png";
        String output2 = sdCard.getAbsolutePath() + "/image/output5.png";
        Log.i("dawn", "output: " + output);
        try{
//            LImageUtil.copyDrawableToSDCard(this, R.drawable.pic1844x1240, "input.png");
//            LImageUtil.compressImage(this, input, output, 100);
//            LImageUtil.compressImage(input, output2, 800, -1);
            LImageUtil.cropImage(input, output2, 100, 100, 100, 100);
            ImageView imageView = findViewById(R.id.imageView);
            LImageUtil.showImage(output2, imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}