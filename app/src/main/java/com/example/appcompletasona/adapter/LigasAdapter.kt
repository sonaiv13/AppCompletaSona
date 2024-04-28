package com.example.appcompletasona.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.appcompletasona.R
import com.example.appcompletasona.model.Liga
import com.example.appcompletasona.ui.fragments.MainFragment

class LigasAdapter(var lista: ArrayList<Liga>, var context: Context, var listener: OnRecyclerLigasListener): RecyclerView.Adapter<LigasAdapter.MyHolder>() {

    private lateinit var listaCompleta: ArrayList<Liga>

    class MyHolder(item: View): ViewHolder(item){
        var nombreLiga: TextView

        init {
            nombreLiga = item.findViewById(R.id.nombreLiga)
        }
    }


    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista: View = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return MyHolder(vista)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val liga = lista[position]
        holder.nombreLiga.text = liga.nombreLiga
        holder.nombreLiga.setOnClickListener {
            listener.onLigaSelected(liga)
        }
    }

    fun addLiga(liga: Liga){
        this.lista.add(liga)
        notifyItemInserted(lista.size-1)
    }

    interface OnRecyclerLigasListener{
        fun onLigaSelected(liga: Liga)
    }

}