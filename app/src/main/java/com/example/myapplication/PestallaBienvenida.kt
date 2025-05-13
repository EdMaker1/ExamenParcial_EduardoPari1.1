package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.InterfazBienvenidaBinding

class PestallaBienvenida : Fragment() {

    private var _vinculo: InterfazBienvenidaBinding? = null
    private val vinculo get() = _vinculo!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vinculo = InterfazBienvenidaBinding.inflate(inflater, container, false)
        return vinculo.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // boton para ir al juego
        vinculo.botonIniciar.setOnClickListener {
            findNavController().navigate(R.id.action_bienvenidojuego)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vinculo = null
    }
}
