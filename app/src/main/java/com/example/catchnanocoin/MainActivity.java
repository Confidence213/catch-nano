package com.example.catchnanocoin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.catchnanocoin.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    int score;
    ImageView[] nanoArray;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View mainView = binding.getRoot();
        setContentView(mainView);

        score = 0;
        nanoArray = new ImageView[]{
                binding.nano1,binding.nano2,binding.nano3,
                binding.nano4,binding.nano5,binding.nano6,
                binding.nano7,binding.nano8,binding.nano9};

        imageLoop();


        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                binding.time.setText(Integer.toString((int) millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

                binding.time.setText(" Off");
                handler.removeCallbacks(runnable);

                for(ImageView image : nanoArray){
                    image.setVisibility(View.VISIBLE);
                    image.setOnClickListener(null);
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Restart");
                alert.setMessage("Do you want to play again ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Game Over!", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();

            }
        }.start();

    }

    public void increaseScore(View view) {
        if(view.getVisibility() == View.VISIBLE){
            score++;
        }
        binding.score.setText(Integer.toString(score));
    }

    public void imageLoop(){

        handler = new Handler();
        runnable = () -> {
            for(ImageView image : nanoArray){
                image.setVisibility(View.INVISIBLE);
            }

            Random random = new Random();
            int i = random.nextInt(9);
            nanoArray[i].setVisibility(View.VISIBLE);

            handler.postDelayed(runnable,600);
        };

        handler.post(runnable);

    }
}