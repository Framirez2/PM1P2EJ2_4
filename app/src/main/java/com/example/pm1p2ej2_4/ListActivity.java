package com.example.pm1p2ej2_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pm1p2ej2_4.Configuracion.SQLiteConexion;
import com.example.pm1p2ej2_4.Tablas.Transacciones;
import com.example.pm1p2ej2_4.Tablas.signaturess;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ListView listfirmas;
    List<signaturess> FirmasList;
    ArrayList<String> firmalist;
    private static CustomAdapter adaptercustom;
    SQLiteConexion conexion;
    ArrayList<signaturess> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        listfirmas = (ListView) findViewById(R.id.listfirmas);
        FirmasList = new ArrayList<>();

        firmalist = new ArrayList<String>();
        GetListFirmas();

        listfirmas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String id = lista.get(i).getSignatures();
                Intent intent = new Intent(ListActivity.this, FirmaDigital.class);
                intent.putExtra("firma",lista.get(i).getSignatures());
                startActivity(intent);
            }
        });

    }

    private void GetListFirmas() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        signaturess listFirmas;

        lista = new ArrayList<>();
        Cursor cursor = db.rawQuery( Transacciones.GetSignatures ,null);

        while (cursor.moveToNext()){
            listFirmas = new signaturess();
            listFirmas.setId(cursor.getInt(0));
            listFirmas.setDescripcion(cursor.getString(1));
            listFirmas.setSignatures(cursor.getString(2));

            lista.add(listFirmas);
        }
        cursor.close();

        adaptercustom = new CustomAdapter(lista, getApplicationContext());

        listfirmas.setAdapter(adaptercustom);
    }
}