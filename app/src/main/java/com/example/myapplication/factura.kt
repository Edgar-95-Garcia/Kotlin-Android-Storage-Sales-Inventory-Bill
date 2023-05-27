package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.random.Random

class factura : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.factura)

        val cadena = generarCadenaAleatoria()
        findViewById<TextView>(R.id.textView13).text = cadena

        val total = intent.getStringExtra("total")
        findViewById<TextView>(R.id.textView11).text = total

        val empresa = intent.getStringExtra("empresa")
        findViewById<TextView>(R.id.textView5).text = empresa

        val array_empresa = empresa?.toCharArray()
        var RFC = ""
        var j = 0
        var auxiliar = false
        var auxiliar2 = false
        if (array_empresa != null) {
            while(j < array_empresa.size){
                if(j in 0..3){
                    auxiliar2 = true
                    RFC += (array_empresa[j]).toString().uppercase(Locale.getDefault())
                }
                if(j == 4){
                    RFC += "2023"
                }
                if(array_empresa[j] == '('){
                    auxiliar = true
                    j++
                }else if (array_empresa[j] == ')'){
                    auxiliar = false
                }
                if (auxiliar) RFC += (array_empresa[j]).toString().uppercase(Locale.getDefault())
            j++
            }
            findViewById<TextView>(R.id.textView7).text = RFC
        }

        val listaRecibida = intent.getStringArrayListExtra("lista")

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        val params = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)

        if (!listaRecibida.isNullOrEmpty()) {
            var i = 0
            val tableRow = TableRow(this)

            val textView1 = TextView(this)
            textView1.text = "Producto"
            textView1.layoutParams = params
            tableRow.addView(textView1)

            val textView2 = TextView(this)
            textView2.text = "Cantidad"
            textView2.layoutParams = params
            tableRow.addView(textView2)

            val textView3 = TextView(this)
            textView3.text = "Total"
            textView3.layoutParams = params
            tableRow.addView(textView3)

            tableLayout.addView(tableRow)
            while (i < listaRecibida.size) {
                val tableRow = TableRow(this)

                val textView1 = TextView(this)
                textView1.text = listaRecibida[i]
                textView1.layoutParams = params
                tableRow.addView(textView1)

                val textView2 = TextView(this)
                textView2.text = listaRecibida[i+1]
                textView2.layoutParams = params
                tableRow.addView(textView2)

                val textView3 = TextView(this)
                textView3.text = listaRecibida[i+2]
                textView3.layoutParams = params
                tableRow.addView(textView3)

                tableLayout.addView(tableRow)

                i+=3
            }
        }

        val btnregresar = findViewById<Button>(R.id.button5)

        btnregresar.setOnClickListener{
            val mainactivity = Intent(this, MainActivity::class.java)
            startActivity(mainactivity)
        }

    }
    fun generarCadenaAleatoria(): String {
        val letras = ('A'..'Z').toList()  // Rango de letras
        val digitos = ('0'..'9').toList()             // Rango de dígitos
        val random = Random.Default
        val cadenaAleatoria = buildString {
            repeat(10) {
                val randomIndex = random.nextInt(0, digitos.size)
                append(digitos[randomIndex])
            }
            repeat(10) {
                val randomIndex = random.nextInt(0, letras.size)
                append(letras[randomIndex])
            }
        }
        return cadenaAleatoria
    }
}