package co.edu.udea.compumovil.gr04_20181.lab1;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class splash_screen extends AppCompatActivity {

    //Variable que me dara el tiempo que taradara en cambiar a la proxima activity
    private static final long SPLASH_SCREEN_DELAY = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // Esconde el actionbar para esta actividad

         getSupportActionBar().hide();


        //Este variable se define para luego ser usada en el cambio de activity

        TimerTask timer=new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(splash_screen.this, MainActivity.class);
                startActivity(mainIntent);
                //Termina la activity actual
                finish();
            }
        };

        // Se define un objeto tipo timer que sera lo que demore en mostrarse el Splash Screen
        Timer timer1 = new Timer();
        timer1.schedule(timer, SPLASH_SCREEN_DELAY);

    }
}
