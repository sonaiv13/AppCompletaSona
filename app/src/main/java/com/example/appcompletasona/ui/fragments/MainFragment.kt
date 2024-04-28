package com.example.appcompletasona.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.appcompletasona.R
import com.example.appcompletasona.adapter.LigasAdapter
import com.example.appcompletasona.databinding.FragmentLoginBinding
import com.example.appcompletasona.databinding.FragmentMainBinding
import com.example.appcompletasona.model.Liga
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONObject

class MainFragment: Fragment(), LigasAdapter.OnRecyclerLigasListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var ligasAdapter: LigasAdapter
    private lateinit var listaLigas: ArrayList<Liga>

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        listaLigas = ArrayList()
        ligasAdapter = LigasAdapter(listaLigas, requireContext(), this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerLigas.adapter = ligasAdapter
        binding.recyclerLigas.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        rellenarLista()

    }


    override fun onDetach() {
        super.onDetach()
    }

    fun rellenarLista(){

        val peticion: JsonObjectRequest = JsonObjectRequest(Request.Method.GET, "https://www.thesportsdb.com/api/v1/json/3/all_leagues.php", null,
            {
                val leagues: JSONArray = it.getJSONArray("leagues")
                for (i in 0 until leagues.length()){
                    val league: JSONObject = leagues.getJSONObject(i)
                    val liga: Liga = Liga(
                        league.getString("idLeague"),
                        league.getString("strLeague")
                    )
                    ligasAdapter.addLiga(liga)
                }
            },
            {
                Snackbar.make(binding.root, "Error en la conexión", Snackbar.LENGTH_SHORT).show()
            })

        Volley.newRequestQueue(context).add(peticion)
    }

    override fun onLigaSelected(liga: Liga) {
        val bundle = Bundle()
        bundle.putString("ligaSeleccionada", liga.idLiga)
        bundle.putString("nombreLiga", liga.nombreLiga)
        findNavController().navigate(R.id.action_mainFragment_to_ligaFragment, bundle)
    }

}