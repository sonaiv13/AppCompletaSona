package com.example.appcompletasona.ui.fragments

import android.content.Context
import android.media.audiofx.DynamicsProcessing.Eq
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appcompletasona.R
import com.example.appcompletasona.adapter.EquiposAdapter
import com.example.appcompletasona.adapter.EquiposFavAdapter
import com.example.appcompletasona.adapter.LigasAdapter
import com.example.appcompletasona.databinding.FragmentFavBinding
import com.example.appcompletasona.databinding.FragmentLoginBinding
import com.example.appcompletasona.databinding.FragmentMainBinding
import com.example.appcompletasona.model.Equipo
import com.example.appcompletasona.model.Liga
import com.example.appcompletasona.viewmodel.LigaViewModel
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONObject

class FavFragment: Fragment() {

    private lateinit var binding: FragmentFavBinding
    private lateinit var equiposFavAdapter: EquiposFavAdapter
    private lateinit var listaFav: ArrayList<Equipo>

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        listaFav = ArrayList()
        equiposFavAdapter = EquiposFavAdapter(listaFav, requireContext())
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listaFav = arguments?.getSerializable("listaFav") as ArrayList<Equipo>
        if(listaFav != null){
            binding.recyclerFav.adapter = equiposFavAdapter
            binding.recyclerFav.layoutManager = LinearLayoutManager(context)
        } else {
            Snackbar.make(binding.root, "La lista de favoritos es nula", Snackbar.LENGTH_SHORT).show()
        }
    }


    override fun onDetach() {
        super.onDetach()
    }

}