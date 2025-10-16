package com.koll.projetocarrogaragem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koll.projetocarrogaragem.databinding.ItemVehicleBinding

class VehicleListAdapter : ListAdapter<Vehicle, VehicleListAdapter.VehicleViewHolder>(VehiclesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding = ItemVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class VehicleViewHolder(private val binding: ItemVehicleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicle: Vehicle) {
            binding.textViewPlate.text = vehicle.plate
            binding.textViewModel.text = vehicle.model
            binding.textColor.text = vehicle.color
            binding.textViewYear.text = vehicle.year.toString()
        }
    }

    class VehiclesComparator : DiffUtil.ItemCallback<Vehicle>() {
        override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
