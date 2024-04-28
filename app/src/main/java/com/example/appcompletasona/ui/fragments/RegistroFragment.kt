package com.example.appcompletasona.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appcompletasona.R
import com.example.appcompletasona.databinding.FragmentRegistroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroFragment: Fragment() {

    private lateinit var binding: FragmentRegistroBinding
    private var nombre: String? = null
    private var correo: String? = null
    private var pass: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        nombre = arguments?.getString("nombre")
        correo = arguments?.getString("correo")
        pass = arguments?.getString("password")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://appcompletasona-default-rtdb.europe-west1.firebasedatabase.app/")
        binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (nombre!=null && correo!=null && pass!=null){
            binding.editNombre.setText(nombre)
            binding.editCorreo.setText(correo)
            binding.editPass.setText(pass)
        }

        binding.btnRegistro.setOnClickListener {
            val nombreStr = binding.editNombre.text.toString()
            val correoStr = binding.editCorreo.text.toString()
            val passStr = binding.editPass.text.toString()

            if(nombreStr.isNotEmpty() && correoStr.isNotEmpty() && passStr.isNotEmpty()){
                //crear usuario
                auth.createUserWithEmailAndPassword(correoStr, passStr)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val uid = auth.currentUser!!.uid
                            val usuarioRef = database.getReference("usuarios").child(uid)
                            usuarioRef.child("nombre").setValue(nombreStr)
                            usuarioRef.child("favoritos").setValue("Añadir fav")
                            //pasar datos al login
                            val bundle = Bundle()
                            bundle.putString("correo", correoStr)
                            bundle.putString("password", passStr)
                            findNavController().navigate(R.id.action_registroFragment_to_loginFragment, bundle)
                        } else {
                            Snackbar.make(binding.root, "Fallo en el proceso", Snackbar.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Snackbar.make(binding.root, "Faltan datos", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

}