package com.example.appcompletasona.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.appcompletasona.R
import com.example.appcompletasona.model.Equipo
import com.example.appcompletasona.model.Liga
import com.example.appcompletasona.ui.fragments.MainFragment

class EquiposFavAdapter(var lista: ArrayList<Equipo>, var context: Context): RecyclerView.Adapter<EquiposFavAdapter.MyHolder>() {

    private lateinit var listaCompleta: ArrayList<Equipo>

    class MyHolder(item: View): ViewHolder(item){
        var nombreEquipo: TextView

        init {
            nombreEquipo = item.findViewById(R.id.nombreEquipo)
        }
    }


    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista: View = LayoutInflater.from(context).inflate(R.layout.fav_recycler, parent, false)
        return MyHolder(vista)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val equipo = lista[position]
        holder.nombreEquipo.text = equipo.nombreEquipo
    }



}