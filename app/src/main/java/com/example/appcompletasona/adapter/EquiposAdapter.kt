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

class EquiposAdapter(var lista: ArrayList<Equipo>, var context: Context, var listener: OnBotonFavClickListener): RecyclerView.Adapter<EquiposAdapter.MyHolder>() {

    private lateinit var listaCompleta: ArrayList<Equipo>

    class MyHolder(item: View): ViewHolder(item){
        var nombreEquipo: TextView
        var btnFav: ImageButton

        init {
            nombreEquipo = item.findViewById(R.id.nombreEquipo)
            btnFav = item.findViewById(R.id.btnFav)
        }
    }


    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista: View = LayoutInflater.from(context).inflate(R.layout.equipo_recycler, parent, false)
        return MyHolder(vista)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val equipo = lista[position]
        holder.nombreEquipo.text = equipo.nombreEquipo

        //Configurar imagen de boton fav segun el estado
        if(equipo.isFav){
            holder.btnFav.setImageResource(R.drawable.favorito)
        } else {
            holder.btnFav.setImageResource(R.drawable.corazon)
        }

        //Agregar listener a btnFav
        holder.btnFav.setOnClickListener {
            //Cambiar el estado de favorito
            equipo.isFav = !equipo.isFav

            //Actualizar la imagen del boton
            if(equipo.isFav){
                holder.btnFav.setImageResource(R.drawable.favorito)
            } else {
                holder.btnFav.setImageResource(R.drawable.corazon)
            }

            //notificar al Fragment
            listener.onBotonFavClick(equipo)
        }
    }

    fun addEquipo(equipo: Equipo){
        this.lista.add(equipo)
        notifyItemInserted(lista.size-1)
    }

    interface OnBotonFavClickListener{
        fun onBotonFavClick(equipo: Equipo)
    }

}