package com.uno.veterinaria

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.Cita
import models.DBHelper
import java.text.SimpleDateFormat
import java.util.Locale

class FichaClinica_act : AppCompatActivity() {

    // Se define un Adaptador interno para el historial, que usa el layout "list_item_historial.xml"
    inner class HistorialAdapter(private val citas: List<Cita>) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

        inner class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            // Asumimos que los IDs en list_item_historial.xml son estos. Si falla, es por los IDs.
            val tvFechaHistorial: TextView = itemView.findViewById(R.id.tvFechaHistorial)
            val tvMotivoHistorial: TextView = itemView.findViewById(R.id.tvMotivoHistorial)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
            // Inflamos el layout específico para el historial del usuario.
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_historial, parent, false)
            return HistorialViewHolder(view)
        }

        override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
            val cita = citas[position]
            holder.tvFechaHistorial.text = "${cita.fecha} - ${cita.hora}"
            holder.tvMotivoHistorial.text = "Motivo: ${cita.motivo}"
        }

        override fun getItemCount(): Int = citas.size
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_clinica)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // 1. Obtener el nombre del usuario que inició sesión
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", null)

        if (userName != null) {
            // 2. Buscar las citas de ese usuario en la base de datos
            val dbHelper = DBHelper(this)
            val citasDelDueno = dbHelper.getCitasByDueno(userName)

            if (citasDelDueno.isNotEmpty()) {
                // 3. Poblar la información de la mascota y próxima cita
                val primeraCita = citasDelDueno[0] // Asumimos que todas las citas son de la misma mascota
                findViewById<TextView>(R.id.tvNombreMascotaFicha).text = primeraCita.nombreMascota
                findViewById<TextView>(R.id.tvEspecieMascotaFicha).text = primeraCita.especie
                findViewById<TextView>(R.id.tvEdadMascotaFicha).text = "${primeraCita.edad} años"

                // Lógica para encontrar la próxima cita (la más cercana en el futuro)
                val proximaCita = citasDelDueno.sortedBy { 
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.fecha)?.time 
                }.lastOrNull()
                
                if(proximaCita != null) {
                    findViewById<TextView>(R.id.tvFechaProximaCita).text = "${proximaCita.fecha} - ${proximaCita.hora}"
                    findViewById<TextView>(R.id.tvMotivoProximaCita).text = "Motivo: ${proximaCita.motivo}"
                }

                // 4. Mostrar el historial en la lista
                val rvHistorial: RecyclerView = findViewById(R.id.rvHistorialClinico)
                rvHistorial.layoutManager = LinearLayoutManager(this)
                rvHistorial.adapter = HistorialAdapter(citasDelDueno)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}