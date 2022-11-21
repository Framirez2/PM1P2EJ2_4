package com.example.pm1p2ej2_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pm1p2ej2_4.Configuracion.SQLiteConexion;
import com.example.pm1p2ej2_4.Tablas.Transacciones;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText desc;
    Button btnlista, btnguardar;

    private float floatStartX = -1, floatStartY = -1,
            floatEndX = -1, floatEndY = -1;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this
                ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.pen);
        desc = findViewById(R.id.txtdescripcion);
        btnlista = findViewById(R.id.btnlista);
        btnguardar = findViewById(R.id.btnguardar);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarImagen(view);
            }
        });

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });


    }

    private void drawPaintSketchImage(){

        if (bitmap == null){
            bitmap = Bitmap.createBitmap(imageView.getWidth(),
                    imageView.getHeight(),
                    Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(6);
        }
        canvas.drawLine(floatStartX,
                floatStartY-220,
                floatEndX,
                floatEndY-220,
                paint);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            floatStartX = event.getX();
            floatStartY = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE){
            floatEndX = event.getX();
            floatEndY = event.getY();
            drawPaintSketchImage();
            floatStartX = event.getX();
            floatStartY = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            floatEndX = event.getX();
            floatEndY = event.getY();
            drawPaintSketchImage();
        }
        return super.onTouchEvent(event);
    }

    public void guardarImagen(View view){

        if (desc.getText().length()>0 || bitmap != null){
            File fileSaveImage = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    Calendar.getInstance().getTime().toString() + ".jpg");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(fileSaveImage);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                String encoded = convertirBase64();
                fileOutputStream.flush();
                fileOutputStream.close();
                guardarRegistro(encoded);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Firma y Descripcion son campos obligatorios", Toast.LENGTH_LONG).show();
        }

    }
    public String convertirBase64(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }

    private void guardarRegistro(String encoded) {

        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Transacciones.descripcion,desc.getText().toString());
        values.put(Transacciones.signature,encoded);

        try {
            Long resultado = db.insert(Transacciones.TablaSignatures, Transacciones.id, values);
            Toast.makeText(getApplicationContext(), "Firma Ingresada Correctamente. "+resultado.toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al insertar. "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        bitmap = null;
        desc.setText("");
        desc.requestFocus();

        db.close();
    }




}