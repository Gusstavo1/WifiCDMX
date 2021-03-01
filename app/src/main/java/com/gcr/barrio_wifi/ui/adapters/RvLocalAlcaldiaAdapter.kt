package com.gcr.barrio_wifi.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gcr.barrio_wifi.R
import com.gcr.barrio_wifi.localdata.Alcaldias
import com.gcr.barrio_wifi.models.alcaldia.LocalDataAlcaldia
import kotlinx.android.synthetic.main.item_wifi.view.*

class RvLocalAlcaldiaAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<RvLocalAlcaldiaAdapter.Holder>() {

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: LocalDataAlcaldia) {
            itemView.tvAlcaldiaPolygon.text = item.name
            itemView.setOnClickListener {
                itemClickListener.clickListener(item)
            }
        }
    }

    interface ItemClickListener {
        fun clickListener(item: LocalDataAlcaldia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alcaldia, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return setData().size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val record = setData()[position]
        holder.bind(record)
    }

    private fun setData(): List<LocalDataAlcaldia> {
        val alcadias = Alcaldias()
        return alcadias.getaAlcaldias()
    }
}