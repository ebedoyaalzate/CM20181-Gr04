package co.edu.udea.compumovil.gr04_20181.lab1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class bebida extends AppCompatActivity {

    private ImageView imagenEntrada, imagenMuestra;
    private EditText nombre;
    private EditText precio;
    private EditText ingredientes;
    private TextView nombreM, precioM, ingredientesM;
    private FloatingActionButton guardar,limpiar;
    private Uri imageUri;
    private Context context = this;
    private RelativeLayout datos;
    private Drawable imagenDefefecto;
    private String mensajeNombre;
    private String mensajePrecio;
    private String mensajeIngredientes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebida);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        imagenEntrada = (ImageView) findViewById(R.id.fotoI);
        imagenMuestra = (ImageView) findViewById(R.id.fotoM);
        nombre = (EditText) findViewById(R.id.nombre);
        precio = (EditText) findViewById(R.id.precio);
        ingredientes = (EditText) findViewById(R.id.ingredientes);
        nombreM = (TextView) findViewById(R.id.nombreM);
        precioM = (TextView) findViewById(R.id.precioM);
        ingredientesM = (TextView) findViewById(R.id.ingredientesM);
        guardar = (FloatingActionButton) findViewById(R.id.guardar);
        limpiar = (FloatingActionButton) findViewById(R.id.limpiar);
        datos = (RelativeLayout) findViewById(R.id.datos);

        datos.setVisibility(View.GONE);

        imagenDefefecto = imagenEntrada.getDrawable();
        mensajeNombre = nombre.getText().toString();
        mensajePrecio = precio.getText().toString();
        mensajeIngredientes = ingredientes.getText().toString();

        SharedPreferences sharedPref = context.getSharedPreferences("Data",context.MODE_PRIVATE);

        data();



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datosCompletos()){
                    SharedPreferences sharedPref = getPreferences(context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("nombre",nombre.getText().toString());
                    editor.putString("precio",precio.getText().toString());
                    editor.putString("ingredientes",ingredientes.getText().toString());
                    editor.putString("imagenUri",imageUri.toString());
                    editor.putBoolean("hayDatos",true);
                    editor.commit();
                    mostrarDatos();
                    limpiarGuardado();
                    Toast.makeText(getApplicationContext(), "Guardado exitosamete", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Datos incompletos", Toast.LENGTH_SHORT).show();
                }


            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getPreferences(context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("nombre","");
                editor.putString("precio","");
                editor.putString("ingredientes","");
                editor.putString("imagenUri","");
                editor.putBoolean("hayDatos",false);
                editor.commit();
                datos.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Limpiado exitosamete", Toast.LENGTH_SHORT).show();
            }
        });

        imagenEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });



    }

    private void limpiarGuardado() {
        imagenEntrada.setImageDrawable(imagenDefefecto);
        nombre.setText(mensajeNombre);
        precio.setText(mensajePrecio);
        ingredientes.setText(mensajeIngredientes);
    }

    private boolean datosCompletos() {

        if(nombre.getText().toString().isEmpty()){
            return false;
        }
        if(precio.getText().toString().isEmpty()){
            return false;
        }
        if(ingredientes.getText().toString().isEmpty()){
            return false;
        }
        if(imagenDefefecto.equals(imagenEntrada.getDrawable())){
            return false;
        }
        return true;
    }

    private void data() {
        SharedPreferences sharedPref = getPreferences(context.MODE_PRIVATE);
        if(sharedPref.getBoolean("hayDatos",false)){
            mostrarDatos();
        }
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            imageUri = data.getData();
            imagenEntrada.setImageURI(imageUri);
        }
    }

    private  void mostrarDatos(){
        SharedPreferences sharedPref = getPreferences(context.MODE_PRIVATE);
        Uri imagenUriMuestra = Uri.parse(sharedPref.getString("imagenUri",""));
        imagenMuestra.setImageURI(imagenUriMuestra);
        nombreM.setText(sharedPref.getString("nombre",""));
        precioM.setText(sharedPref.getString("precio",""));
        ingredientesM.setText(sharedPref.getString("ingredientes",""));
        datos.setVisibility(View.VISIBLE);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (imageUri != null) {
            savedInstanceState.putParcelable("photo", imageUri);
        }


        savedInstanceState.putString("nameDish", nombre.getText().toString());
        savedInstanceState.putString("priceDish", precio.getText().toString());
        savedInstanceState.putString("ingredientsDish", ingredientes.getText().toString());


    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        nombre.setText(savedInstanceState.getString("nameDish"));
        precio.setText(savedInstanceState.getString("priceDish"));
        ingredientes.setText(savedInstanceState.getString("ingredientsDish"));
        imageUri = (Uri) savedInstanceState.getParcelable("photo");
        if (imageUri != null) {
            imagenEntrada.setImageURI(imageUri);
        }
    }



}
