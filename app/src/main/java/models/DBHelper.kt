package models

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context): SQLiteOpenHelper (context, "veterinaria_db_v3", null, 1) { // DB Renombrada y versión reseteada

    companion object{
        // --- TABLA USUARIOS ---
        private const val tabla_usuarios = "Usuarios"
        private const val col_id = "id"
        private const val col_nombre = "nombre"
        private const val col_correo = "correo"
        private const val col_contrasena = "contrasena"
        private const val col_telefono = "telefono"
        private const val col_rol = "rol"

        // --- TABLA CITAS ---
        private const val TABLA_CITAS = "Citas"
        private const val COL_ID_CITA = "id_cita"
        private const val COL_NOMBRE_MASCOTA_CITA = "nombre_mascota"
        private const val COL_SEXO_MASCOTA = "sexo_mascota"
        private const val COL_CHIP_MASCOTA = "chip_mascota"
        private const val COL_NOMBRE_DUENO = "nombre_dueno"
        private const val COL_FECHA = "fecha"
        private const val COL_HORA = "hora"
        private const val COL_MOTIVO = "motivo"
        private const val COL_ESPECIE_MASCOTA = "especie_mascota"
        private const val COL_EDAD_MASCOTA = "edad_mascota"
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
                $COL_MOTIVO TEXT,
                $COL_ESPECIE_MASCOTA TEXT,
                $COL_EDAD_MASCOTA TEXT
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

    fun agregarCita(nombreMascota: String, sexoMascota: String, chipMascota: String, nombreDueno: String, fecha: String, hora: String, motivo: String, especie: String, edad: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NOMBRE_MASCOTA_CITA, nombreMascota)
        values.put(COL_SEXO_MASCOTA, sexoMascota)
        values.put(COL_CHIP_MASCOTA, chipMascota)
        values.put(COL_NOMBRE_DUENO, nombreDueno)
        values.put(COL_FECHA, fecha)
        values.put(COL_HORA, hora)
        values.put(COL_MOTIVO, motivo)
        values.put(COL_ESPECIE_MASCOTA, especie)
        values.put(COL_EDAD_MASCOTA, edad)
        val result = db.insert(TABLA_CITAS, null, values)
        db.close()
        return result
    }

    fun agregarUsuario(nombre: String, correo: String, contrasena: String, telefono: String, rol: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(col_nombre, nombre)
        values.put(col_correo, correo)
        values.put(col_contrasena, contrasena)
        values.put(col_telefono, telefono)
        values.put(col_rol, rol)
        val result = db.insert(tabla_usuarios, null, values)
        db.close()
        return result
    }

    // Devuelve un par (Rol, Nombre) o null si el usuario no existe
    fun checkUser(correo: String, contrasena: String): Pair<String, String>? {
        val db = this.readableDatabase
        val columns = arrayOf(col_rol, col_nombre)
        val selection = "$col_correo = ? AND $col_contrasena = ?"
        val selectionArgs = arrayOf(correo, contrasena)
        val cursor = db.query(tabla_usuarios, columns, selection, selectionArgs, null, null, null)
        var result: Pair<String, String>? = null
        if (cursor.moveToFirst()) {
            val roleColumnIndex = cursor.getColumnIndex(col_rol)
            val nameColumnIndex = cursor.getColumnIndex(col_nombre)
            if (roleColumnIndex != -1 && nameColumnIndex != -1) {
                val role = cursor.getString(roleColumnIndex)
                val name = cursor.getString(nameColumnIndex)
                result = Pair(role, name)
            }
        }
        cursor.close()
        db.close()
        return result
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

    fun checkMascotaExists(nombreMascota: String, nombreDueno: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COL_ID_CITA)
        val selection = "$COL_NOMBRE_MASCOTA_CITA = ? AND $COL_NOMBRE_DUENO = ?"
        val selectionArgs = arrayOf(nombreMascota, nombreDueno)
        val cursor = db.query(TABLA_CITAS, columns, selection, selectionArgs, null, null, null)
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
                val especie = cursor.getString(cursor.getColumnIndex(COL_ESPECIE_MASCOTA))
                val edad = cursor.getString(cursor.getColumnIndex(COL_EDAD_MASCOTA))
                citas.add(Cita(id, nombreMascota, sexoMascota, chipMascota, nombreDueno, fecha, hora, motivo, especie, edad))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return citas
    }

    @SuppressLint("Range")
    fun getCitasByDueno(nombreDueno: String): List<Cita> {
        val citas = mutableListOf<Cita>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLA_CITAS WHERE $COL_NOMBRE_DUENO = ?"
        val cursor = db.rawQuery(query, arrayOf(nombreDueno))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COL_ID_CITA))
                val nombreMascota = cursor.getString(cursor.getColumnIndex(COL_NOMBRE_MASCOTA_CITA))
                val sexoMascota = cursor.getString(cursor.getColumnIndex(COL_SEXO_MASCOTA))
                val chipMascota = cursor.getString(cursor.getColumnIndex(COL_CHIP_MASCOTA))
                val dueno = cursor.getString(cursor.getColumnIndex(COL_NOMBRE_DUENO))
                val fecha = cursor.getString(cursor.getColumnIndex(COL_FECHA))
                val hora = cursor.getString(cursor.getColumnIndex(COL_HORA))
                val motivo = cursor.getString(cursor.getColumnIndex(COL_MOTIVO))
                val especie = cursor.getString(cursor.getColumnIndex(COL_ESPECIE_MASCOTA))
                val edad = cursor.getString(cursor.getColumnIndex(COL_EDAD_MASCOTA))
                citas.add(Cita(id, nombreMascota, sexoMascota, chipMascota, dueno, fecha, hora, motivo, especie, edad))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return citas
    }
}