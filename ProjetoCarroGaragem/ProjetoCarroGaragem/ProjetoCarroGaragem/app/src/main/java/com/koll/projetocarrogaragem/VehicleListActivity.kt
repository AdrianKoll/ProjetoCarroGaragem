package com.koll.projetocarrogaragem

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.koll.projetocarrogaragem.databinding.ActivityVehicleListBinding

class VehicleListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleListBinding
    private val vehicleViewModel: VehicleViewModel by viewModels { VehicleViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = VehicleListAdapter()
        binding.recyclerViewVehicles.adapter = adapter
        binding.recyclerViewVehicles.layoutManager = LinearLayoutManager(this)

        vehicleViewModel.allVehicles.observe(this) { vehicles ->
            vehicles?.let { adapter.submitList(it) }
        }
    }
}
