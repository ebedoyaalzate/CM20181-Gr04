package co.edu.udea.compumovil.gr04_20181.lab2;

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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class comida extends AppCompatActivity {

    private ImageView imagenEntrada, imagenMuestra;
    private EditText nombre;
    private EditText precio;
    private EditText ingredientes;
    private TextView nombreM, precioM, ingredientesM,duracionM,tipoPlatoM;
    private FloatingActionButton guardar,limpiar;
    private TextView duracion;
    private RadioButton fuerte;
    private RadioButton entrada;
    private Uri imageUri;
    private Context context = this;
    private RelativeLayout datos;

    private Drawable imagenDefefecto;

    private String mensajeNombre;
    private String mensajePrecio;
    private String mensajeIngredientes;
    private String mensajeEditar;
    private String mensajeEntrada;
    private String mensajeFuerte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida);


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
        duracion = (TextView) findViewById(R.id.duracion);
        duracionM = (TextView) findViewById(R.id.duracionM);
        tipoPlatoM= (TextView) findViewById(R.id.tipoPlatoM);
        fuerte = (RadioButton) findViewById(R.id.fuerte);
        entrada = (RadioButton) findViewById(R.id.entrada);
        datos.setVisibility(View.GONE);

        imagenDefefecto = imagenEntrada.getDrawable();
        mensajeNombre = nombre.getText().toString();
        mensajePrecio = precio.getText().toString();
        mensajeIngredientes = ingredientes.getText().toString();
        mensajeEditar = duracion.getText().toString();
        mensajeEntrada = entrada.getText().toString();
        mensajeFuerte = fuerte.getText().toString();

        SharedPreferences sharedPref = context.getSharedPreferences("Data",context.MODE_PRIVATE);

        data();



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(datosCompletos()){
                    SharedPreferences sharedPref = getPreferences(context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("nombreComida",nombre.getText().toString());
                    editor.putString("precioComida",precio.getText().toString());
                    editor.putString("ingredientesComida",ingredientes.getText().toString());
                    editor.putString("imagenUriComida",imageUri.toString());
                    editor.putString("duracionComida",duracion.getText().toString());
                    if(fuerte.isChecked()){
                        editor.putString("tipoPlato",mensajeFuerte);
                    }else if(entrada.isChecked()){
                        editor.putString("tipoPlato",mensajeEntrada);
                    }
                    editor.putBoolean("hayDatosComida",true);
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
                editor.putString("nombreComida","");
                editor.putString("precioComida","");
                editor.putString("ingredientesComida","");
                editor.putString("imagenUriComida","");
                editor.putString("duracionComida","");
                editor.putString("tipoPlato","");
                editor.putBoolean("hayDatosComida",false);
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

        duracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerDialog(view);
            }
        });

    }

    private void limpiarGuardado() {
        imagenEntrada.setImageDrawable(imagenDefefecto);
        nombre.setText(mensajeNombre);
        precio.setText(mensajePrecio);
        duracion.setText(mensajeEditar);
        ingredientes.setText(mensajeIngredientes);
        entrada.setChecked(false);
        fuerte.setChecked(false);
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
        }if(duracion.getText().toString().equals(mensajeEditar)){
            return false;
        }
        if(imagenDefefecto.equals(imagenEntrada.getDrawable())){
            return false;
        }
        if((!fuerte.isChecked()) &&(!entrada.isChecked())){
            return false;
        }

        return true;
    }

    private void data() {
        SharedPreferences sharedPref = getPreferences(context.MODE_PRIVATE);
        if(sharedPref.getBoolean("hayDatosComida",false)){
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
        Uri imagenUriMuestra = Uri.parse(sharedPref.getString("imagenUriComida",""));
        imagenMuestra.setImageURI(imagenUriMuestra);
        nombreM.setText(sharedPref.getString("nombreComida",""));
        precioM.setText(sharedPref.getString("precioComida",""));
        duracionM.setText(sharedPref.getString("duracionComida",""));
        ingredientesM.setText(sharedPref.getString("ingredientesComida",""));
        tipoPlatoM.setText(sharedPref.getString("tipoPlato",""));
        datos.setVisibility(View.VISIBLE);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (imageUri != null) {
            savedInstanceState.putParcelable("photo", imageUri);
        }


        savedInstanceState.putString("nameDish", nombre.getText().toString());
        savedInstanceState.putString("priceDish", precio.getText().toString());
        savedInstanceState.putString("duracion", duracion.getText().toString());
        savedInstanceState.putString("ingredientsDish", ingredientes.getText().toString());


    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        nombre.setText(savedInstanceState.getString("nameDish"));
        precio.setText(savedInstanceState.getString("priceDish"));
        ingredientes.setText(savedInstanceState.getString("ingredientsDish"));
        duracion.setText(savedInstanceState.getString("duracion"));
        imageUri = (Uri) savedInstanceState.getParcelable("photo");
        if (imageUri != null) {
            imagenEntrada.setImageURI(imageUri);
        }
    }

    public void showPickerDialog(View view) {
        LayoutInflater l = getLayoutInflater();
        final View v = l.inflate(R.layout.picker_layout, null);
        final FrameLayout layout = new FrameLayout(this);
        layout.addView(v, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));
        NumberPicker numberPicker = v.findViewById(R.id.minutos);
        numberPicker.setMaxValue(60);
        numberPicker.setMinValue(0);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.duracion);
        alertDialogBuilder.setView(layout);
        final NumberPicker minutePicker = v.findViewById(R.id.minutos);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                String minute = String.valueOf(minutePicker.getValue());
                                if (minute.length() == 1) {
                                    minute = "0" + minute;
                                }
                                TextView tv = (TextView) findViewById(R.id.duracion);
                                tv.setText(minute+"m" );

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

}
