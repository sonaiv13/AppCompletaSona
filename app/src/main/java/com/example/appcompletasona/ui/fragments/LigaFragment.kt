package com.example.appcompletasona.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appcompletasona.R
import com.example.appcompletasona.adapter.EquiposAdapter
import com.example.appcompletasona.databinding.FragmentLigaBinding
import com.example.appcompletasona.databinding.FragmentMainBinding
import com.example.appcompletasona.model.Equipo
import com.example.appcompletasona.model.Liga
import com.example.appcompletasona.viewmodel.LigaViewModel
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONObject

class LigaFragment: Fragment(), EquiposAdapter.OnBotonFavClickListener {

    private lateinit var binding: FragmentLigaBinding
    private lateinit var equiposAdapter: EquiposAdapter
    private lateinit var listaEquipos: ArrayList<Equipo>
    private lateinit var listaFav: ArrayList<Equipo>
    private var ligaSeleccionada: String? = null
    private var nombreLiga: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ligaSeleccionada = arguments?.getString("ligaSeleccionada")
        nombreLiga = arguments?.getString("nombreLiga")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLigaBinding.inflate(inflater, container, false)
        listaEquipos = ArrayList()
        equiposAdapter = EquiposAdapter(listaEquipos, requireContext(), this)
        listaFav = ArrayList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tituloLigaSeleccionada.text = nombreLiga
        binding.recyclerEquipos.adapter = equiposAdapter
        binding.recyclerEquipos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rellenarLista()

    }


    override fun onDetach() {
        super.onDetach()
    }

    fun rellenarLista(){

        //URL base
        val url = "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=$nombreLiga"

        val peticion: JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                val teams: JSONArray = it.getJSONArray("teams")
                for(i in 0 until teams.length()){
                    val team: JSONObject = teams.getJSONObject(i)
                    val equipo: Equipo = Equipo(
                        team.getString("strLeague"),
                        team.getString("idTeam"),
                        team.getString("strTeam"))
                    equiposAdapter.addEquipo(equipo)
                }
                //equiposAdapter.actualizarLista(teamsArray)
            },
            {
                Snackbar.make(binding.root, "Error en la conexión", Snackbar.LENGTH_SHORT).show()
            })

        Volley.newRequestQueue(context).add(peticion)

    }


    override fun onBotonFavClick(equipo: Equipo) {
        if(equipo.isFav){
            listaFav.add(equipo)
        } else {
            listaFav.remove(equipo)
        }
        equiposAdapter.notifyDataSetChanged()
    }



}