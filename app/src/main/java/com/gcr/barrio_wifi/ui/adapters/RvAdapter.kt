package com.gcr.barrio_wifi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gcr.barrio_wifi.R
import com.gcr.barrio_wifi.models.wifizones.Record
import kotlinx.android.synthetic.main.item_wifi.view.*

class RvAdapter(private val list: List<Record>, private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<RvAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Record) {
            itemView.tvAlcaldiaPolygon.text = item.alcaldia
            itemView.tvColoniaData.text = item.colonia
            itemView.tvStatusData.text = item.estatus_conectividad
            itemView.tvNameData.text = item.nombre_de_la_red
            itemView.setOnClickListener {
                itemClickListener.clickListener(item._id, item.geo_shape)
            }
        }
    }

    interface ItemClickListener {
        fun clickListener(id: Int, polygon: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wifi, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val record = list[position]
        holder.bind(record)
    }
}