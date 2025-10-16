package com.koll.meu_app_car.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.koll.meu_app_car.R

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnVerificar = findViewById<MaterialButton>(R.id.btnVerificar)
        btnVerificar.setOnClickListener {
            val intent = Intent(this, CheckVehicleActivity::class.java)
            startActivity(intent)
        }

        val btnEditar = findViewById<MaterialButton>(R.id.btnEditar)
        btnEditar.setOnClickListener {
            val intent = Intent(this, VehicleListActivity::class.java)
            intent.putExtra("modo", "editar")
            startActivity(intent)
        }
    }
}