package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnMostrar = findViewById<Button>(R.id.button)
        val btnStock = findViewById<Button>(R.id.button2)
        val btnBaseActivity = findViewById<Button>(R.id.Base)
        btnMostrar.setOnClickListener{
            val intent = Intent(this, generarpeditoactivity::class.java)
            startActivity(intent)
        }
        btnStock.setOnClickListener{
            val intent2 = Intent(this, stock::class.java)
            startActivity(intent2)
        }
        btnBaseActivity.setOnClickListener{
            val intent = Intent(this, generarbase::class.java)
            startActivity(intent)
        }

        val dbHelper = AdminSQLiteOpenHelper(this)
        val cursor = dbHelper.getAllData()
        if(cursor != null){
            while(cursor.moveToNext()){
                var cantidad = cursor.getInt(cursor.getColumnIndex("Cantidad"))
                if(cantidad < 1000){
                    val intent2 = Intent(this, stock::class.java)
                    startActivity(intent2)
                }
            }
        }
    }
}