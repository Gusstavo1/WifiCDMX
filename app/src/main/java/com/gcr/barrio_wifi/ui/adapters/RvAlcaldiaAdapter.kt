package com.gcr.barrio_wifi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gcr.barrio_wifi.R
import com.gcr.barrio_wifi.models.alcaldia.RecordX
import kotlinx.android.synthetic.main.item_wifi.view.*

class RvAlcaldiaAdapter(
    private val list: List<RecordX>,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<RvAlcaldiaAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RecordX) {
            itemView.tvAlcaldiaPolygon.text = item.nomgeo
            itemView.setOnClickListener {
                itemClickListener.clickListener(item._id, item.geo_shape, item.nomgeo)
            }
        }
    }

    interface ItemClickListener {
        fun clickListener(id: Int, polygon: String, alcadia: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alcaldia, parent, false)
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