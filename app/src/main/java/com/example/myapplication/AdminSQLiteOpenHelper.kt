package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class AdminSQLiteOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "movilesm4.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table IF NOT EXISTS catalogo (producto_id INTEGER PRIMARY KEY, Producto TEXT, Clave TEXT, Precio TEXT, Cantidad INTEGER, Precio_real TEXT, Tipo_venta TEXT)")
    }
     fun insertDataText(){
         this.insertData(1,"Aceite sintético","AS120EX","25 (por unidad)",1000,"25","unidad")
         this.insertData(2,"Diesel","DR231EX","9 (por litro)",1000,"9","litro")
         this.insertData(3,"Gasolina 87 octanos","GA092EX","6 (por litro)",1000,"6","litro")
         this.insertData(4,"Gas licuado","LI852EX","10 (por litro)",1000,"10","litro")
         this.insertData(5,"Aceite extendedor parafinico","AEP12EX","13 (por unidad)",1000,"13","unidad")
     }

    fun insertData(ID: Int, Producto: String, Clave: String, Precio: String, Cantidad: Int, Precio_real: String, Tipo_venta: String): Long {
        var newRowId: Long = -1
        try{
            val db = writableDatabase
            val values = ContentValues().apply {
                put("producto_id", ID)
                put("Producto", Producto)
                put("Clave", Clave)
                put("Precio", Precio)
                put("Cantidad", Cantidad)
                put("Precio_real", Precio_real)
                put("Tipo_venta", Tipo_venta)
            }
            newRowId = db.insert("catalogo", null, values)

        }catch (e: Exception){
        }
        return newRowId
    }

    fun deleteTable() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS catalogo")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
    fun getNumber(id: Int): Cursor? {
        val db = readableDatabase
        return db.rawQuery("SELECT Cantidad FROM catalogo WHERE producto_id = $id", null)
    }

    fun updateCantidadByID(dbHelper: AdminSQLiteOpenHelper, id: Int, Cantidad: Int){
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("Cantidad", Cantidad)
        }

        val selection = "producto_id = ?"
        val selectionArgs = arrayOf(id.toString())

        val rowCount = db.update("catalogo", values, selection, selectionArgs)


    }


    fun getAllData(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM catalogo", null)
    }

    fun getProductosNombre(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT Producto FROM catalogo", null)
    }

    fun getPrecioNombre(nombre: String): Cursor{
        val db = readableDatabase
        return db.rawQuery("SELECT Precio_real FROM catalogo WHERE Producto = '$nombre'", null)
    }

    fun getCantidad(nombre: String): Cursor{
        val db = readableDatabase
        return db.rawQuery("SELECT Cantidad FROM catalogo WHERE Producto = '$nombre'", null)
    }

    fun actualizarCantidad(nombre: String, cantidad: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("Cantidad", cantidad)
        }
        val whereClause = "Producto = ?"
        val whereArgs = arrayOf(nombre)
        db.update("catalogo", values, whereClause, whereArgs)
    }
}