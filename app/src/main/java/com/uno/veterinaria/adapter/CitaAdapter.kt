package com.uno.veterinaria.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uno.veterinaria.R
import models.HistorialCita
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CitaAdapter(private var citas: List<HistorialCita>) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Asumimos que los IDs en tu layout 'list_item_cita.xml' son estos.
        // Si la app crashea aquí, es porque los IDs en el XML son diferentes.
        val tvNombreMascota: TextView = itemView.findViewById(R.id.tvNombreMascota)
        val tvNombreDueno: TextView = itemView.findViewById(R.id.tvNombreDueno)
        val tvFechaHora: TextView = itemView.findViewById(R.id.tvFechaHora)
        val tvMotivo: TextView = itemView.findViewById(R.id.tvMotivo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]

        // Tu API no devuelve nombres, así que mostramos el ID de la mascota.
        // Para mostrar nombres, necesitarías modificar tu API.
        holder.tvNombreMascota.text = "Mascota ID: ${cita.mascotaId}"
        holder.tvNombreDueno.text = ""
        holder.tvNombreDueno.visibility = View.GONE // Ocultamos el campo del dueño ya que no hay datos

        // Convertimos el timestamp (número largo) a un formato de fecha y hora legible
        val formattedDate = try {
            val sdf = SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm", Locale.getDefault())
            val netDate = Date(cita.fechaHoraTimestamp)
            sdf.format(netDate)
        } catch (e: Exception) {
            "Fecha inválida"
        }

        holder.tvFechaHora.text = formattedDate
        holder.tvMotivo.text = "Motivo: ${cita.motivo}"
    }

    override fun getItemCount() = citas.size

    fun actualizarCitas(nuevasCitas: List<HistorialCita>) {
        citas = nuevasCitas
        notifyDataSetChanged()
    }
}
