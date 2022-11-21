package com.example.pm1p2ej2_4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pm1p2ej2_4.Tablas.signaturess;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<signaturess> implements View.OnClickListener{

    private List<signaturess> dataset;
    private Context mContext;

    public static class ViewHolder{
        TextView id, descripcion;
        ImageView firma;
    }

    public CustomAdapter(List<signaturess> data, Context context) {
        super(context, R.layout.activity_custom_adapter, data);

        this.dataset = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        Object object = getItem(position);

        signaturess datModel = (signaturess) object;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        signaturess datosModel = getItem(position);
        ViewHolder viewHolder = null;
        final View Result;

        if(convertView == null){
            viewHolder = new ViewHolder();


            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.activity_custom_adapter, parent, false);

            viewHolder.id = convertView.findViewById(R.id.txtid);
            viewHolder.descripcion = convertView.findViewById(R.id.txtdescrip);
            viewHolder.firma = convertView.findViewById(R.id.img);
            Result = convertView;
            convertView.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) convertView.getTag();
            convertView.setTag(viewHolder);
        }

        viewHolder.id.setText("ID: "+String.valueOf(datosModel.getId()));
        viewHolder.descripcion.setText("Descripcion: "+datosModel.getDescripcion());
        if (datosModel.getSignatures().length()>0){
            Log.i("LOG",""+datosModel.getSignatures().length());
            viewHolder.firma.setImageBitmap(ConvertBase64toImage(datosModel.signatures));
        }

        return convertView;
    }

    private Bitmap ConvertBase64toImage(String Base64String)
    {
        //String base64Image  = Base64String.split(",")[1];
        String base64Image  = Base64String;
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }


}