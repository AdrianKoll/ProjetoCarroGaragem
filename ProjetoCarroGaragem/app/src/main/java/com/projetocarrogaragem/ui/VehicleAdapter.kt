package com.projetocarrogaragem.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projetocarrogaragem.R
import com.projetocarrogaragem.model.VehicleEntity

class VehicleAdapter(
    private var items: List<VehicleEntity>,
    private val onCheck: (VehicleEntity) -> Unit,
    private val onEdit: (VehicleEntity) -> Unit,
    private val onDelete: (VehicleEntity) -> Unit,
    private val modo: String
) : RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    // 🔹 Método para atualizar a lista
    fun updateItems(newItems: List<VehicleEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tvNome)
        val tvPlaca: TextView = view.findViewById(R.id.tvPlaca)
        val btnCheck: ImageButton = view.findViewById(R.id.btnCheck)
        val btnEdit: ImageButton = view.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = items[position]

        holder.tvNome.text = entry.nomeCompleto
        holder.tvPlaca.text = entry.placaCarro

        when (modo.lowercase()) {
            "visualizar" -> {
                holder.btnCheck.visibility = View.VISIBLE
                holder.btnEdit.visibility = View.GONE
                holder.btnDelete.visibility = View.GONE
            }
            "editar" -> {
                holder.btnCheck.visibility = View.GONE
                holder.btnEdit.visibility = View.VISIBLE
                holder.btnDelete.visibility = View.VISIBLE
            }
            else -> {
                holder.btnCheck.visibility = View.VISIBLE
                holder.btnEdit.visibility = View.VISIBLE
                holder.btnDelete.visibility = View.VISIBLE
            }
        }

        holder.btnCheck.setOnClickListener { onCheck(entry) }
        holder.btnEdit.setOnClickListener { onEdit(entry) }
        holder.btnDelete.setOnClickListener { onDelete(entry) }
    }

    override fun getItemCount() = items.size
}