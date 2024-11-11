package com.example.appcompletasona.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.appcompletasona.R
import com.example.appcompletasona.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment: androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var correo: String? = null
    private var pass: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //recuperar datos
        correo = arguments?.getString("correo")
        pass = arguments?.getString("password")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(correo!=null && pass!=null){
            binding.editCorreo.setText(correo)
            binding.editPass.setText(pass)
        }
        // Boton de Login
        binding.btnLogin.setOnClickListener {
            if(!binding.editCorreo.text.isEmpty() && !binding.editPass.text.isEmpty()){
                auth.signInWithEmailAndPassword(binding.editCorreo.text.toString(), binding.editPass.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val bundle = Bundle()
                            bundle.putString("correo", binding.editCorreo.text.toString())
                            findNavController().navigate(R.id.action_loginFragment_to_mainFragment, bundle)
                        } else {
                            Snackbar.make(binding.root, "Fallo en el proceso", Snackbar.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Snackbar.make(binding.root, "Faltan datos", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.btnRegistro.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }
    }

    override fun onDetach() {
        super.onDetach()
    }


}