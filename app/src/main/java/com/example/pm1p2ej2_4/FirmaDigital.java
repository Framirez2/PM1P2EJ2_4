package com.example.pm1p2ej2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

public class FirmaDigital extends AppCompatActivity {

    ImageView imagen;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma_digital);
        imagen = findViewById(R.id.imgsign);

        Bundle datos = getIntent().getExtras();
        String image = datos.getString("firma");

        imagen.setImageBitmap(ConvertBase64toImage(image));
    }

    private Bitmap ConvertBase64toImage(String Base64String)
    {
        String base64Image  = Base64String;
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}