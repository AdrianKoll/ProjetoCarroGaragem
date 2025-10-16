package com.koll.projetocarrogaragem

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.koll.projetocarrogaragem.databinding.ActivityCheckVehicleBinding
import kotlinx.coroutines.launch

class CheckVehicleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckVehicleBinding
    private val vehicleViewModel: VehicleViewModel by viewModels { VehicleViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCheckVehicle.setOnClickListener {
            val plate = binding.editTextPlate.text.toString().uppercase()
            if (plate.isNotEmpty()) {
                lifecycleScope.launch {
                    val vehicle = vehicleViewModel.getVehicleByPlate(plate)
                    if (vehicle != null) {
                        Toast.makeText(this@CheckVehicleActivity, "Veículo encontrado: ${vehicle.model}", Toast.LENGTH_LONG).show()
                        // Optionally navigate to an edit screen or display details
                    } else {
                        Toast.makeText(this@CheckVehicleActivity, "Veículo não encontrado. Registre um novo.", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@CheckVehicleActivity, AddVehicleActivity::class.java)
                        intent.putExtra("plate", plate)
                        startActivity(intent)
                    }
                }
            } else {
                Toast.makeText(this@CheckVehicleActivity, "Por favor, insira a placa do veículo", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonRegisterNewVehicle.setOnClickListener {
            val intent = Intent(this, AddVehicleActivity::class.java)
            startActivity(intent)
        }
    }
}
