package com.koll.meu_app_car.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.koll.meu_app_car.R

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vehicle)

        val cardVerificarRegistrar = findViewById<CardView>(R.id.cardVerificarRegistrar)
        cardVerificarRegistrar.setOnClickListener {
            val intent = Intent(this, CheckVehicleActivity::class.java)
            startActivity(intent)
        }

        val cardEditar = findViewById<CardView>(R.id.cardEditar)
        cardEditar.setOnClickListener {
            val intent = Intent(this, VehicleListActivity::class.java)
            intent.putExtra("modo", "editar")
            startActivity(intent)
        }

        val imageViewLogout = findViewById<ImageView>(R.id.imageViewLogout)
        imageViewLogout.setOnClickListener {
            // Lógica de logout
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
