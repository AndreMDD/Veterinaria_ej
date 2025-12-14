package com.uno.veterinaria

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

/**
 * Clase de aplicación personalizada para realizar configuraciones iniciales,
 * como la creación de canales de notificación.
 */
class VeterinariaApplication : Application() {

    companion object {
        const val CHANNEL_ID_CITA = "channel_cita_agendada"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // La creación del canal solo es necesaria en Android 8.0 (API 26) y superior.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Citas Agendadas"
            val descriptionText = "Notificaciones para citas agendadas exitosamente."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID_CITA, name, importance).apply {
                description = descriptionText
            }
            
            // Registramos el canal en el sistema.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
