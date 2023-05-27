package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class stock: AppCompatActivity()  {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock)
        val btnMostrar = findViewById<Button>(R.id.button7)

        btnMostrar.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout2)

        var producto = ""
        var clave = ""
        var precio = ""
        var cantidad = 0

        val dbHelper = AdminSQLiteOpenHelper(this)
        val cursor = dbHelper.getAllData()

        if(cursor != null){
            while(cursor.moveToNext()){
                val tableRow = TableRow(this)
                var id = cursor.getInt(cursor.getColumnIndex("producto_id"))
                //--------------------------------
                val textView1 = TextView(this)
                textView1.text = cursor.getString(cursor.getColumnIndex("Producto"))
                tableRow.addView(textView1)
                //-------------------------------------
                val textView4 = TextView(this)
                textView4.text = cursor.getInt(cursor.getColumnIndex("Cantidad")).toString()
                tableRow.addView(textView4)
                //-------------------------------------
                val button = Button(this)
                button.text = "Abastecer"
                button.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("id", id)
                    val intent = Intent(this, modificar::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
                tableRow.addView(button)
                //---------------------
                tableLayout.addView(tableRow)
            }
        }
    }
}