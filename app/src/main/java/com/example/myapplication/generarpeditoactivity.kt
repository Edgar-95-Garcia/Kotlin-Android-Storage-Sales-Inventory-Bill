package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class generarpeditoactivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.generarpeditoactivity)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val listaGlobal = ArrayList<String>()
        var selectedValue: String = ""
        var total_acumulador: Int = 0
        var precio_obtenido = ""

        //Obtener todas las filas de la base de datos y posteriormente agregarlos al SPINNER
        val dbHelper = AdminSQLiteOpenHelper(this)
        val cursor = dbHelper.getAllData()
        val dataList = mutableListOf<String>()

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val producto = cursor.getString(cursor.getColumnIndex("Producto"))
                dataList.add(producto)
            } while (cursor.moveToNext())
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataList)
        val spinner_editable = findViewById<Spinner>(R.id.spinner)
        spinner_editable.adapter = adapter
        //Fin rellenar SPINNER
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedValue = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val btn_factura = findViewById<Button>(R.id.button4)

        btn_factura.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val selectedText = selectedRadioButton.text.toString()

            val bundle = Bundle()
            bundle.putStringArrayList("lista", listaGlobal)
            bundle.putString("empresa", selectedText)
            bundle.putString("total", total_acumulador.toString())
            val factura_intent = Intent(this, factura::class.java)
            factura_intent.putExtras(bundle)
            startActivity(factura_intent)
        }

        val btn_agregar = findViewById<Button>(R.id.button3)
        btn_agregar.setOnClickListener {
            var cantidad = findViewById<EditText>(R.id.editTextText).text.toString()
            var cantidadInteger = cantidad.toInt()

            var cantidad_producto = dbHelper.getCantidad(selectedValue)
            var cantidad_obtenida = 0
            if (cantidad_producto.moveToFirst())
                cantidad_obtenida =
                    cantidad_producto.getInt(cantidad_producto.getColumnIndex("Cantidad"))

            if (cantidad_obtenida < cantidadInteger) {
                val intent2 = Intent(this, stock::class.java)
                startActivity(intent2)
                finish()
            } else {


                val dbHelper = AdminSQLiteOpenHelper(this)
                val cursor = dbHelper.getPrecioNombre(selectedValue)
                var precio = 0

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        val precioIndex = (cursor.getColumnIndex("Precio_real"))
                        if (precioIndex != -1) {
                            precio_obtenido = cursor.getString(precioIndex)
                        }
                    } while (cursor.moveToNext())
                }
                dbHelper.actualizarCantidad(
                    selectedValue,
                    (cantidad_obtenida - cantidadInteger).toString()
                )

                var sub_total = cantidadInteger * precio_obtenido.toInt()
                total_acumulador += sub_total
                findViewById<TextView>(R.id.textView15).text = total_acumulador.toString()
                listaGlobal.add(selectedValue);
                listaGlobal.add(cantidad);
                listaGlobal.add(sub_total.toString());
            }
        }
    }
}
