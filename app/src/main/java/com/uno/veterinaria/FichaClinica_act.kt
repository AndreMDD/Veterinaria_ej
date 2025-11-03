package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class FichaClinica_act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usaremos el diseño estable que ya corregí anteriormente
        setContentView(R.layout.activity_ficha_clinica)
    }

    /**
     * Esta función es llamada por el atributo android:onClick del botón 'btnVolver'
     * en el archivo activity_ficha_clinica.xml
     */
    fun volver(view: View) {
        val intent = Intent(this, Inicio_act::class.java)
        // Limpiamos la pila de actividades para una navegación más limpia
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}