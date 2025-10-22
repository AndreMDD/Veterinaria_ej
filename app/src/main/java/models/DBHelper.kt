package models

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context): SQLiteOpenHelper (context, "db_veterinaria", null, 1) {

    companion object{
        private const val db_name = "db_veterinaria"
        private const val db_version = 1
        private const val tabla_usuarios = "Usuarios"
        private const val col_id = "id"
        private const val col_nombre = "nombre"
        private const val col_correo = "correo"
        private const val col_contrasena = "contrasena"
        private const val col_telefono = "telefono"
        private const val col_rol = "rol" // Columna para el rol

        // tabla mascotas
        private const val tabla_mascotas = "Mascotas"
        private const val col_id_mascota = "id_mascota"
        private const val col_nombre_mascota = "nombre_mascota"
        private const val col_raza = "raza"
        private const val col_edad = "edad"
        private const val col_peso = "peso"
        private const val col_id_usuario = "id_usuario"

        // tabla citas
        private const val TABLA_CITAS = "Citas"
        private const val COL_ID_CITA = "id_cita"
        private const val COL_NOMBRE_MASCOTA_CITA = "nombre_mascota"
        private const val COL_SEXO_MASCOTA = "sexo_mascota"
        private const val COL_CHIP_MASCOTA = "chip_mascota"
        private const val COL_NOMBRE_DUENO = "nombre_dueno"
        private const val COL_FECHA = "fecha"
        private const val COL_HORA = "hora"
        private const val COL_MOTIVO = "motivo"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val crearTablaUsuarios = """
            CREATE TABLE $tabla_usuarios (
                $col_id INTEGER PRIMARY KEY AUTOINCREMENT,
                $col_nombre TEXT,
                $col_correo TEXT,
                $col_contrasena TEXT,
                $col_telefono TEXT,
                $col_rol TEXT
            )
        """.trimIndent()
        db?.execSQL(crearTablaUsuarios)

        val crearTablaCitas = """
            CREATE TABLE $TABLA_CITAS (
                $COL_ID_CITA INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE_MASCOTA_CITA TEXT,
                $COL_SEXO_MASCOTA TEXT,
                $COL_CHIP_MASCOTA TEXT,
                $COL_NOMBRE_DUENO TEXT,
                $COL_FECHA TEXT,
                $COL_HORA TEXT,
                $COL_MOTIVO TEXT
            )
        """.trimIndent()
        db?.execSQL(crearTablaCitas)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $tabla_usuarios")
        db?.execSQL("DROP TABLE IF EXISTS $TABLA_CITAS")
        onCreate(db)
    }

    fun agregarCita(nombreMascota: String, sexoMascota: String, chipMascota: String, nombreDueno: String, fecha: String, hora: String, motivo: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NOMBRE_MASCOTA_CITA, nombreMascota)
        values.put(COL_SEXO_MASCOTA, sexoMascota)
        values.put(COL_CHIP_MASCOTA, chipMascota)
        values.put(COL_NOMBRE_DUENO, nombreDueno)
        values.put(COL_FECHA, fecha)
        values.put(COL_HORA, hora)
        values.put(COL_MOTIVO, motivo)
        val result = db.insert(TABLA_CITAS, null, values)
        db.close()
        return result
    }

    fun agregarUsuario(nombre: String, correo: String, contrasena: String, telefono: String, rol: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(col_nombre, nombre)
        values.put(col_correo, correo)
        values.put(col_contrasena, contrasena) // Es recomendable usar un método de hash para las contraseñas
        values.put(col_telefono, telefono)
        values.put(col_rol, rol)
        val result = db.insert(tabla_usuarios, null, values)
        db.close()
        return result
    }

    fun checkUser(correo: String, contrasena: String): String? {
        val db = this.readableDatabase
        val columns = arrayOf(col_rol)
        val selection = "$col_correo = ? AND $col_contrasena = ?"
        val selectionArgs = arrayOf(correo, contrasena)
        val cursor = db.query(tabla_usuarios, columns, selection, selectionArgs, null, null, null)
        var role: String? = null
        if (cursor.moveToFirst()) {
            val roleColumnIndex = cursor.getColumnIndex(col_rol)
            if (roleColumnIndex != -1) {
                role = cursor.getString(roleColumnIndex)
            }
        }
        cursor.close()
        db.close()
        return role
    }

    fun checkUserExists(correo: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(col_id)
        val selection = "$col_correo = ?"
        val selectionArgs = arrayOf(correo)
        val cursor = db.query(tabla_usuarios, columns, selection, selectionArgs, null, null, null)
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    @SuppressLint("Range")
    fun getAllCitas(): List<Cita> {
        val citas = mutableListOf<Cita>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLA_CITAS"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COL_ID_CITA))
                val nombreMascota = cursor.getString(cursor.getColumnIndex(COL_NOMBRE_MASCOTA_CITA))
                val sexoMascota = cursor.getString(cursor.getColumnIndex(COL_SEXO_MASCOTA))
                val chipMascota = cursor.getString(cursor.getColumnIndex(COL_CHIP_MASCOTA))
                val nombreDueno = cursor.getString(cursor.getColumnIndex(COL_NOMBRE_DUENO))
                val fecha = cursor.getString(cursor.getColumnIndex(COL_FECHA))
                val hora = cursor.getString(cursor.getColumnIndex(COL_HORA))
                val motivo = cursor.getString(cursor.getColumnIndex(COL_MOTIVO))
                citas.add(Cita(id, nombreMascota, sexoMascota, chipMascota, nombreDueno, fecha, hora, motivo))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return citas
    }
}