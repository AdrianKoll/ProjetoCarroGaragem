package com.projetocarrogaragem.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.projetocarrogaragem.R

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Card Verificar / Registrar
        findViewById<CardView>(R.id.cardVerificarRegistrar).setOnClickListener {
            startActivity(Intent(this, CheckVehicleActivity::class.java))
        }

        // Card Editar → abre o layout de edição
        findViewById<CardView>(R.id.cardEditar).setOnClickListener {
            startActivity(Intent(this, VehicleListActivity::class.java))
        }
    }
}
