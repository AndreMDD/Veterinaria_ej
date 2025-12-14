package com.uno.veterinaria.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.uno.veterinaria.repository.CitasRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import models.HistorialCita
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Clase de prueba unitaria para FichaClinicaViewModel.
 * Verifica que la lógica del ViewModel funciona como se espera.
 */
@ExperimentalCoroutinesApi
class FichaClinicaViewModelTest {

    // Regla para ejecutar tareas de LiveData de forma síncrona, necesaria para los tests.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Creamos las variables para el repositorio falso y el ViewModel que vamos a probar.
    private lateinit var repository: CitasRepository
    private lateinit var viewModel: FichaClinicaViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Antes de cada test, preparamos el entorno.
        Dispatchers.setMain(testDispatcher) // Usamos un despachador de corrutinas de prueba.
        repository = mockk() // Creamos el repositorio falso con MockK.
        viewModel = FichaClinicaViewModel(repository) // Creamos el ViewModel pasándole el repositorio falso.
    }

    @After
    fun tearDown() {
        // Después de cada test, limpiamos el entorno.
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando se llama a cargarCitas, el LiveData se actualiza con los datos del repositorio`() = runTest {
        // 1. Arrange (Preparar)
        val dueno = "testUser"
        val citasFalsas = listOf(
            HistorialCita(id = 1, mascotaId = 10, fechaHoraTimestamp = 1678886400000L, motivo = "Vacuna"),
            HistorialCita(id = 2, mascotaId = 11, fechaHoraTimestamp = 1678972800000L, motivo = "Control")
        )

        // Le "enseñamos" al repositorio falso qué debe devolver cuando se le llame.
        coEvery { repository.getHistorialCitas(dueno) } returns citasFalsas

        // Creamos un observador para capturar los cambios en el LiveData.
        val observer = Observer<List<HistorialCita>> {}
        viewModel.citas.observeForever(observer)

        // 2. Act (Actuar)
        // Llamamos a la función que queremos probar.
        viewModel.cargarCitas(dueno)
        
        // Avanzamos el despachador para que la corrutina dentro del ViewModel se complete.
        testDispatcher.scheduler.advanceUntilIdle() 

        // 3. Assert (Verificar)
        // Obtenemos el valor que ahora tiene el LiveData.
        val liveDataValue = viewModel.citas.value

        // Verificamos que el valor del LiveData sea el que esperamos.
        assertEquals(2, liveDataValue?.size)
        assertEquals(citasFalsas, liveDataValue)
        assertEquals("Vacuna", liveDataValue?.get(0)?.motivo)

        // Limpiamos el observador para evitar memory leaks.
        viewModel.citas.removeObserver(observer)
    }
}
