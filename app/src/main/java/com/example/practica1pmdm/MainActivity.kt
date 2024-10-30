package com.example.practica1pmdm

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var spinnerPaisSeleccion: Spinner
    lateinit var paisSelecionado: String
    lateinit var suscrito: String
    lateinit var nombreMostrar: String
    lateinit var apellidoMostrar: String
    lateinit var correoMostrar: String
    lateinit var mostrarProgreso: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val suscripcion = findViewById<Switch>(R.id.switchBoletin)
        val textoNombre = findViewById<TextView>(R.id.editTextNombre)
        val textoApellido = findViewById<TextView>(R.id.editTextApellido)
        val textoCorreo = findViewById<TextView>(R.id.editTextCorreo)
        val radioGroupGenero = findViewById<RadioGroup>(R.id.radioGroup)
        val seleccionHobbiesArte = findViewById<CheckBox>(R.id.checkBoxArte)
        val seleccionHobbiesDeporte = findViewById<CheckBox>(R.id.checkBoxDeporte)
        val seleccionHobbiesLectura = findViewById<CheckBox>(R.id.checkBoxLectura)
        val seleccionHobbiesMusica = findViewById<CheckBox>(R.id.checkBoxMusica)
        val seekBarSatisfaccion = findViewById<SeekBar>(R.id.seekBarSatisfaccion)
        val botonGuardar = findViewById<Button>(R.id.buttonGuardar)
        val nivelSatisfaccionText = findViewById<TextView>(R.id.textViewNivelSatisfaccion)
        val textoMostrar = findViewById<TextView>(R.id.textViewMostrar)
        mostrarProgreso = "0"
        textoMostrar.text = ""
        nombreMostrar = ""
        apellidoMostrar = ""
        correoMostrar = ""
        spinnerPaisSeleccion = findViewById<Spinner>(R.id.spinnerPais)
        seekBarSatisfaccion.max = 10
        seekBarSatisfaccion.min = 0

        textoNombre.requestFocus()


        ArrayAdapter.createFromResource(
            this,
            R.array.paises_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPaisSeleccion.adapter = adapter
        }


        seekBarSatisfaccion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, progreso: Int, p2: Boolean) {
                nivelSatisfaccionText.text = "Satisfacción nivel \n $progreso estrellas"
                 mostrarProgreso = progreso.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })


        botonGuardar.setOnClickListener {

            if (textoNombre.text.toString() == "") {
                Toast.makeText(this, "El campo Nombre no puede estar vacío", LENGTH_LONG).show()
            } else if (textoApellido.text.toString() == "") {
                Toast.makeText(this, "El campo Apellido no puede estar vacío", LENGTH_LONG).show()
            } else if (textoCorreo.text.toString() == "") {
                Toast.makeText(this, "El campo Correo no puede estar vacío", LENGTH_LONG).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textoCorreo.text.toString()).matches()) {
                Toast.makeText(this, "En el Campo correo debe haber un correo válido", LENGTH_LONG)
                    .show()

            }else {
                var textoHobbies = ""
                val selectedRadioButtonId = radioGroupGenero.checkedRadioButtonId
                val textoSelectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
                var generoMostrar = textoSelectedRadioButton.text.toString()
                nombreMostrar = textoNombre.text.toString()
                apellidoMostrar = textoApellido.text.toString()
                correoMostrar = textoCorreo.text.toString()



                if(seleccionHobbiesArte.isChecked){
                    textoHobbies += " " + seleccionHobbiesArte.text.toString().lowercase() + ","
                }
                if(seleccionHobbiesMusica.isChecked){
                    textoHobbies += " " + seleccionHobbiesMusica.text.toString().lowercase() + ","
                }
                if(seleccionHobbiesDeporte.isChecked){
                    textoHobbies += " " + seleccionHobbiesDeporte.text.toString().lowercase() + ","
                }
                if(seleccionHobbiesLectura.isChecked){
                    textoHobbies += " " + seleccionHobbiesLectura.text.toString().lowercase() + ","
                }
                if(textoHobbies == ""){
                    textoHobbies = "No has seleccionado ningún  hobbie"
                }

                if(textoHobbies.endsWith(',')){
                    textoHobbies = textoHobbies.substring(0, textoHobbies.trimmedLength()) + '.'
                }



                if (suscripcion.isChecked){
                    suscrito = "Si"
                }else{
                    suscrito = "No"
                }




                textoMostrar.text = """
                    Nombre: $nombreMostrar
                    Apellido: $apellidoMostrar
                    Correo: $correoMostrar
                    Género : $generoMostrar
                    Intereses: $textoHobbies
                    Nivel de satisfacción: $mostrarProgreso
                    Suscrito al boletín: $suscrito
                """.trimIndent()

            }

        }

        spinnerPaisSeleccion.onItemSelectedListener = this
    }

         override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
              paisSelecionado = spinnerPaisSeleccion.selectedItem.toString()
         }

         override fun onNothingSelected(p0: AdapterView<*>?) {
             paisSelecionado = "No hay niguno seleccionado"
         }


     }