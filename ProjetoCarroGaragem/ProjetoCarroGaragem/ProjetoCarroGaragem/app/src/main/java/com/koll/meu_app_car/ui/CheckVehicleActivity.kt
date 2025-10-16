package com.koll.meu_app_car.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.text.TextWatcher
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.koll.meu_app_car.R
import com.koll.meu_app_car.data.AppDatabase
import kotlinx.coroutines.launch

class CheckVehicleActivity : AppCompatActivity() {
    private lateinit var etFirstName: AutoCompleteTextView
    private lateinit var etLicensePlate: EditText
    private lateinit var btnCheckVehicle: Button
    private lateinit var tvResult: TextView
    private lateinit var btnRegisterNewVehicle: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_vehicle)

        // Inicializar banco de dados
        database = AppDatabase.getDatabase(this)

        // Excluir registros que começam com 'app'
        lifecycleScope.launch {
            database.vehicleDao().deleteByNamePrefix("app")
        }

        // Inicializar views
        etFirstName = findViewById(R.id.etFirstName)
        etLicensePlate = findViewById(R.id.etLicensePlate)
        btnCheckVehicle = findViewById(R.id.btnCheckVehicle)
        tvResult = findViewById(R.id.tvResult)
        btnRegisterNewVehicle = findViewById(R.id.btnRegisterNewVehicle)

        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)
        imageViewBack.setOnClickListener {
            finish()
        }

        // Configurar autocompletar para o campo de nome
        setupAutoComplete()

        // Configurar botão de verificação
        btnCheckVehicle.setOnClickListener {
            checkVehicle()
        }

        // Configurar botão de registro de novo veículo
        btnRegisterNewVehicle.setOnClickListener {
            val intent = Intent(this, AddEntryActivity::class.java)
            intent.putExtra("nome", etFirstName.text.toString().trim())
            intent.putExtra("placa", etLicensePlate.text.toString().trim().uppercase())
            startActivity(intent)
        }
    }

    private fun setupAutoComplete() {
        etFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.length >= 2) {
                    lifecycleScope.launch {
                        val suggestions = database.vehicleDao().getVehiclesByPartialName(query)
                        val names = suggestions.map { it.nomeCompleto }.distinct()
                        runOnUiThread {
                            val adapter = ArrayAdapter(this@CheckVehicleActivity, android.R.layout.simple_dropdown_item_1line, names)
                            etFirstName.setAdapter(adapter)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        etFirstName.setOnItemClickListener { parent, _, position, _ ->
            val selectedName = parent.getItemAtPosition(position).toString()
            lifecycleScope.launch {
                val vehicle = database.vehicleDao().getVehiclesByPartialName(selectedName).firstOrNull()
                runOnUiThread {
                    vehicle?.let { 
                        etLicensePlate.setText(it.placaCarro)
                    }
                }
            }
        }
    }

    private fun checkVehicle() {
        val firstName = etFirstName.text.toString().trim()
        val licensePlate = etLicensePlate.text.toString().trim().uppercase()

        if (firstName.isEmpty() || licensePlate.isEmpty()) {
            tvResult.text = "Por favor, preencha todos os campos"
            tvResult.setTextColor(getColor(android.R.color.holo_red_dark))
            tvResult.visibility = View.VISIBLE
            return
        }

        lifecycleScope.launch {
            try {
                val vehicle = database.vehicleDao().checkVehicleByNameAndPlate(firstName, licensePlate)
                
                runOnUiThread {
                    if (vehicle != null) {
                        tvResult.text = "✓ Veículo Registrado\nProprietário: ${vehicle.nomeCompleto}\nModelo/Cor: ${vehicle.modeloCor}"
                        tvResult.setTextColor(getColor(android.R.color.holo_green_dark))
                        btnRegisterNewVehicle.visibility = View.GONE // Esconder botão de registro
                    } else {
                        tvResult.text = "✗ Veículo NÃO Registrado. Deseja registrar?"
                        tvResult.setTextColor(getColor(android.R.color.holo_red_dark))
                        btnRegisterNewVehicle.visibility = View.VISIBLE // Mostrar botão de registro
                    }
                    tvResult.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                runOnUiThread {
                    tvResult.text = "Erro ao verificar veículo: ${e.message}"
                    tvResult.setTextColor(getColor(android.R.color.holo_red_dark))
                    tvResult.visibility = View.VISIBLE
                }
            }
        }
    }
}

