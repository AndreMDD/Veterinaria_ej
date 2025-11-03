package com.uno.veterinaria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import models.Cita

class CitaAdapter(private val citas: List<Cita>) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.bind(cita)
    }

    override fun getItemCount(): Int = citas.size

    class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombreMascota: TextView = itemView.findViewById(R.id.tvNombreMascota)
        private val tvNombreDueno: TextView = itemView.findViewById(R.id.tvNombreDueno)
        private val tvMotivo: TextView = itemView.findViewById(R.id.tvMotivo)
        private val tvFechaHora: TextView = itemView.findViewById(R.id.tvFechaHora)
        private val ivIconoMascota: ImageView = itemView.findViewById(R.id.ivIconoMascota)

        fun bind(cita: Cita) {
            tvNombreMascota.text = cita.nombreMascota
            tvNombreDueno.text = "Dueño: ${cita.nombreDueno}"
            tvMotivo.text = cita.motivo
            tvFechaHora.text = "${cita.fecha} - ${cita.hora}"

            // Aquí podrías cambiar el icono según el tipo de mascota, si tuvieras esa información
            // Por ahora, usamos uno por defecto.
            // ivIconoMascota.setImageResource(R.drawable.ic_dog) 
        }
    }
}