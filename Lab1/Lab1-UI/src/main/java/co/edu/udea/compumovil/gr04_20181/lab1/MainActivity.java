package co.edu.udea.compumovil.gr04_20181.lab1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton bebidas,comidas,salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Para Creae los Actions bar a mi gusto
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
         getSupportActionBar().setCustomView(R.layout.abs_layout);

        bebidas= (FloatingActionButton) findViewById(R.id.bebidas);
        comidas= (FloatingActionButton) findViewById(R.id.comidas);
        salir = (FloatingActionButton) findViewById(R.id.salir);

        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bebidas = new Intent(MainActivity.this,bebida.class);
                startActivity(bebidas);
            }
        });
        comidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent comidas = new Intent(MainActivity.this,comida.class);
                startActivity(comidas);
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }


}
