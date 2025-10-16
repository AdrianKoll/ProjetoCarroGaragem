package com.projetocarrogaragem.ui

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.projetocarrogaragem.R
import com.projetocarrogaragem.data.AppDatabase
import kotlinx.coroutines.launch

class CheckVehicleActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_vehicle)

        db = AppDatabase.getDatabase(applicationContext)

        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLicensePlate = findViewById<EditText>(R.id.etLicensePlate)
        val btnCheck = findViewById<Button>(R.id.btnCheckVehicle)
        val btnRegister = findViewById<Button>(R.id.btnRegisterNewVehicle)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnCheck.setOnClickListener {
            val nome = etFirstName.text.toString().trim()
            val placa = etLicensePlate.text.toString().trim()

            if (nome.isEmpty() || placa.isEmpty()) {
                Toast.makeText(this, "Preencha nome e placa", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val existente = db.vehicleDao().checkVehicleByNameAndPlate(nome, placa)
                runOnUiThread {
                    if (existente != null) {
                        tvResult.text = getString(R.string.vehicle_registered_yes)
                        tvResult.visibility = TextView.VISIBLE
                        btnRegister.visibility = Button.GONE
                    } else {
                        tvResult.text = getString(R.string.vehicle_registered_no)
                        tvResult.visibility = TextView.VISIBLE
                        btnRegister.visibility = Button.VISIBLE
                    }
                }
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, AddEntryActivity::class.java))
        }
    }
}