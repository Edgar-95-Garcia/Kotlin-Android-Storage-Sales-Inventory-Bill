package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class generarbase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.generarbase)
        val btnBase = findViewById<Button>(R.id.button9)
        btnBase.setOnClickListener{
            val dbHelper = AdminSQLiteOpenHelper(this)
        }
        val btnDatos = findViewById<Button>(R.id.button10)
        btnDatos.setOnClickListener{
            val dbHelper = AdminSQLiteOpenHelper(this)
            dbHelper.insertDataText()
        }
    }



}