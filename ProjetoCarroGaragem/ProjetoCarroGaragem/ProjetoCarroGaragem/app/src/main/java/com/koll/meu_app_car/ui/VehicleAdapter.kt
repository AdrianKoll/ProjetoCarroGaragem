package com.koll.meu_app_car.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koll.meu_app_car.R
import com.koll.meu_app_car.model.VehicleEntry

class VehicleAdapter(
    private val items: List<VehicleEntry>,
    private val onCheck: (VehicleEntry) -> Unit,
    private val onEdit: (VehicleEntry) -> Unit,
    private val onDelete: (VehicleEntry) -> Unit,
    private val modo: String
) : RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

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

        // Preenche os textos
        holder.tvNome.text = entry.nomeCompleto
        holder.tvPlaca.text = entry.placaCarro

        // Sempre mostrar todos os botões
        holder.btnCheck.visibility = View.VISIBLE
        holder.btnEdit.visibility = View.VISIBLE
        holder.btnDelete.visibility = View.VISIBLE

        // Configura os cliques dos botões
        holder.btnCheck.setOnClickListener { 
            // Visualizar detalhes do veículo
            val context = holder.itemView.context
            val intent = Intent(context, DetalhesEntryActivity::class.java).apply {
                putExtra("vehicleId", entry.id)
                putExtra("nome", entry.nomeCompleto)
                putExtra("motivo", entry.motivo)
                putExtra("placa", entry.placaCarro)
                putExtra("modeloCor", entry.modeloCor)
                putExtra("data", entry.data)
                putExtra("horaEntrada", entry.horarioEntrada)
                putExtra("horaSaida", entry.horarioSaida)
                putExtra("observacao", entry.observacao)
            }
            context.startActivity(intent)
        }

        holder.btnEdit.setOnClickListener { 
            // Editar veículo
            val context = holder.itemView.context
            val intent = Intent(context, AddEntryActivity::class.java).apply {
                putExtra("vehicleId", entry.id)
                putExtra("nome", entry.nomeCompleto)
                putExtra("motivo", entry.motivo)
                putExtra("placa", entry.placaCarro)
                putExtra("modeloCor", entry.modeloCor)
                putExtra("data", entry.data)
                putExtra("horaEntrada", entry.horarioEntrada)
                putExtra("horaSaida", entry.horarioSaida)
                putExtra("observacao", entry.observacao)
            }
            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener { 
            // Excluir veículo
            onDelete(entry)
        }
    }

    override fun getItemCount() = items.size
}