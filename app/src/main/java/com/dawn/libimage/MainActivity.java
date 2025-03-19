package com.dawn.libimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.dawn.image.ImageFactory;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File sdCard = Environment.getExternalStorageDirectory();
        String output = sdCard.getAbsolutePath() + "/image/output.png";
        String input = sdCard.getAbsolutePath() + "/image/input.png";
        String output2 = sdCard.getAbsolutePath() + "/image/output4.png";
        Log.i("dawn", "output: " + output);
        try{
//            LImageUtil.copyDrawableToSDCard(this, R.drawable.pic1844x1240, "input.png");
//            LImageUtil.compressImage(this, input, output, 100);
            ImageFactory.compressImage(input, output2, 800, -1);
            ImageView imageView = findViewById(R.id.imageView);
            ImageFactory.showImage(output2, imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}