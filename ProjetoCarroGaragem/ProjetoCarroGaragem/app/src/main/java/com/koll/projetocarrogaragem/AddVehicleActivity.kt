package com.koll.projetocarrogaragem

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.koll.projetocarrogaragem.databinding.ActivityAddVehicleBinding

class AddVehicleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddVehicleBinding
    private val vehicleViewModel: VehicleViewModel by viewModels { VehicleViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVehicleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSaveVehicle.setOnClickListener {
            val plate = binding.editTextPlate.text.toString().uppercase()
            val model = binding.editTextModel.text.toString()
            val color = binding.editTextColor.text.toString()
            val year = binding.editTextYear.text.toString().toIntOrNull()

            if (plate.isNotEmpty() && model.isNotEmpty() && color.isNotEmpty() && year != null) {
                val vehicle = Vehicle(plate = plate, model = model, color = color, year = year)
                vehicleViewModel.insert(vehicle)
                Toast.makeText(this, "Veículo adicionado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
