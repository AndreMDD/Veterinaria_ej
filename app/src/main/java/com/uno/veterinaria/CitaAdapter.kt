package com.uno.veterinaria

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import models.Cita

class CitaAdapter(private val citas: List<Cita>) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = citas[position]
        holder.bind(cita)
    }

    override fun getItemCount(): Int {
        return citas.size
    }

    class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombreMascota: TextView = itemView.findViewById(R.id.tvNombreMascota)
        private val tvNombreDueno: TextView = itemView.findViewById(R.id.tvNombreDueno)
        private val tvFechaHora: TextView = itemView.findViewById(R.id.tvFechaHora)
        private val tvMotivo: TextView = itemView.findViewById(R.id.tvMotivo)

        fun bind(cita: Cita) {
            tvNombreMascota.text = cita.nombreMascota
            tvNombreDueno.text = "Dueño: ${cita.nombreDueno}"
            tvFechaHora.text = "Fecha: ${cita.fecha} - Hora: ${cita.hora}"
            tvMotivo.text = "Motivo: ${cita.motivo}"
        }
    }
}