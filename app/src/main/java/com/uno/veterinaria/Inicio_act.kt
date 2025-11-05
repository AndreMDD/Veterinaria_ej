package com.uno.veterinaria

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import de.hdodenhof.circleimageview.CircleImageView

class Inicio_act : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val horasBtn = findViewById<Button>(R.id.horas_btn)
        val appointmentButton = findViewById<Button>(R.id.appointmentButton)
        val fichaClinicaBtn = findViewById<Button>(R.id.fichaClinica_btn)
        val profileImage = findViewById<CircleImageView>(R.id.profile_image)

        horasBtn.setOnClickListener {
            val intent = Intent(this, AgendarHora_act::class.java)
            startActivity(intent)
        }

        appointmentButton.setOnClickListener {
            val intent = Intent(this, AgendarHora_act::class.java)
            startActivity(intent)
        }

        fichaClinicaBtn.setOnClickListener {
            val intent = Intent(this, FichaClinica_act::class.java)
            startActivity(intent)
        }

        // TODO: Cargar la imagen del usuario desde la base de datos o SharedPreferences
        // profileImage.setImageURI(...) 

        profileImage.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.overflow_menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_profile -> {
                        val intent = Intent(this, Profile_act::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.action_logout -> {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
}