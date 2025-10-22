package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Inicio_act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val horasBtn = findViewById<Button>(R.id.horas_btn)

        horasBtn.setOnClickListener {
            val intent = Intent(this, AgendarHora_act::class.java)
            startActivity(intent)
        }
    }
}