package com.example.myapplication

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.InterfazJuegoBinding

class PestallaJuego : Fragment() {

    private var _vinculo: InterfazJuegoBinding? = null
    private val vinculo get() = _vinculo!!

    private lateinit var sonidoCorrecto: MediaPlayer
    private lateinit var sonidoIncorrecto: MediaPlayer
    private lateinit var fondoMusica: MediaPlayer

    private val colores = listOf("Rojo", "Cafe", "Violeta", "Amarillo", "Naranja")
    private var colorActual = ""
    private var puntaje = 0
    private var temporizador: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vinculo = InterfazJuegoBinding.inflate(inflater, container, false)
        return vinculo.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sonidoCorrecto = MediaPlayer.create(requireContext(), R.raw.sonido_correcto)
        sonidoIncorrecto = MediaPlayer.create(requireContext(), R.raw.sonido_incorrecto)
        fondoMusica = MediaPlayer.create(requireContext(), R.raw.sonido_fondo)
        fondoMusica.isLooping = true
        fondoMusica.start()

        iniciarTemporizador()
        mostrarColorNuevo()

        vinculo.btnRojo.setOnClickListener { verificarColor("Rojo") }
        vinculo.btnCafe.setOnClickListener { verificarColor("Cafe") }
        vinculo.btnVioleta.setOnClickListener { verificarColor("Violeta") }
        vinculo.btnAmarillo.setOnClickListener { verificarColor("Amarillo") }
        vinculo.btnNaranja.setOnClickListener { verificarColor("Naranja") }
    }

    private fun mostrarColorNuevo() {
        colorActual = colores.random()


        val colorHex = when (colorActual) {
            "Rojo" -> "#F44336"
            "Cafe" -> "#795548"
            "Violeta" -> "#9C27B0"
            "Amarillo" -> "#FFEB3B"
            "Naranja" -> "#FF9800"
            else -> "#FFFFFF"
        }

        vinculo.cuadroColor.setBackgroundColor(Color.parseColor(colorHex))
    }

    private fun verificarColor(seleccionado: String) {
        if (seleccionado == colorActual) {
            puntaje++
            if (::sonidoCorrecto.isInitialized) sonidoCorrecto.start()
        } else {
            if (::sonidoIncorrecto.isInitialized) sonidoIncorrecto.start()
        }
        vinculo.txtPuntaje.text = "Puntos: $puntaje"
        mostrarColorNuevo()
    }

    private fun iniciarTemporizador() {
        temporizador = object : CountDownTimer(30000, 1000) {
            override fun onTick(msRestante: Long) {
                val segundos = msRestante / 1000
                vinculo.txtTiempo.text = "Tiempo: ${segundos}s"
            }

            override fun onFinish() {
                fondoMusica.stop()
                val accion = PestallaJuegoDirections.actionPestallaJuegoToPestallaFinal(puntaje)
                findNavController().navigate(accion)
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        temporizador?.cancel()
        if (::sonidoCorrecto.isInitialized) sonidoCorrecto.release()
        if (::sonidoIncorrecto.isInitialized) sonidoIncorrecto.release()
        if (::fondoMusica.isInitialized) fondoMusica.release()
        _vinculo = null
    }
}
