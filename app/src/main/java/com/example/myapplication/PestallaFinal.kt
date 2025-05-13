package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.InterfazFinalBinding

class PestallaFinal : Fragment() {

    private var _vinculo: InterfazFinalBinding? = null
    private val vinculo get() = _vinculo!!

    private val argumentos: PestallaFinalArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vinculo = InterfazFinalBinding.inflate(inflater, container, false)
        return vinculo.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val puntajeActual = argumentos.puntaje
        val gestor = GestorPuntuacion(requireContext())
        gestor.guardarPuntajeMaximo(puntajeActual)

        val puntajeMaximo = gestor.obtenerPuntajeMaximo()
        val mejoresPuntajes = gestor.obtenerMejoresPuntajes()

        vinculo.textoPuntaje.text = "Tu puntaje: $puntajeActual"
        vinculo.textoRecord.text = "Puntaje máximo: $puntajeMaximo"

        // Mostrar los mejores puntajes en la interfaz
        vinculo.textoMejoresPuntajes.text = "Mejores Puntajes: \n${mejoresPuntajes.joinToString("\n")}"

        vinculo.botonReiniciar.setOnClickListener {
            findNavController().navigate(R.id.action_pestallaFinal_to_pestallaJuego)
        }

        vinculo.botonSalir.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vinculo = null
    }
}
