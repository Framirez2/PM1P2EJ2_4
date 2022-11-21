package com.example.pm1p2ej2_4.Tablas;

public class Transacciones {
    public static final String NameDatabase = "PM01Signature";

    /*Creacion de la base de Datos*/
    public static final String TablaSignatures = "signatures";

    /*Creación de la tabla Signatures*/
    public static final String id = "id";
    public static final String descripcion = "descripcion";
    public static final String signature = "signature";

    //DDL
    public static final String createTableSignatures = "CREATE TABLE "+Transacciones.TablaSignatures+
            " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "descripcion TEXT,"+
            "signature BLOB)";

    public static final String GetSignatures = "SELECT * FROM "+Transacciones.TablaSignatures;

    public static final String DropTableSignatures = "DROP TABLE IF EXISTS Signatures";

    public static final String DeleteSignature = "DELETE FROM "+TablaSignatures;
}
