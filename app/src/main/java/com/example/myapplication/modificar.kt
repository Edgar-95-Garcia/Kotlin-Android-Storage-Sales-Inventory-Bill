package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class modificar : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modificar)
        val btnRegresar = findViewById<Button>(R.id.button8)
        val btnAgregar = findViewById<Button>(R.id.button6)

        btnRegresar.setOnClickListener{
            val intent = Intent(this, stock::class.java)
            startActivity(intent)
        }

        btnAgregar.setOnClickListener{

            val id = intent.getIntExtra("id", 0)
            //Base de datos
            val dbHelper = AdminSQLiteOpenHelper(this)

            val nuevo = findViewById<EditText>(R.id.editTextNumber)
            val nuevoString = nuevo.text.toString()
            val nuevoInt = nuevoString.toInt()
            var antiguoInt = 0
            val cursor = dbHelper.getNumber(id)
            if (cursor != null) {
                if(cursor.moveToFirst()){
                    antiguoInt = cursor.getString(0).toInt()
                }
            }

            val total = antiguoInt + nuevoInt
            dbHelper.updateCantidadByID(dbHelper, id, total)
            val intent2 = Intent(this, stock::class.java)
            startActivity(intent2)
        }
    }
}