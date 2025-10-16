package com.koll.meu_app_car.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.koll.meu_app_car.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAbrirFormulario = findViewById<MaterialButton>(R.id.btnAbrirFormulario)

        btnAbrirFormulario.setOnClickListener {
            // Agora abre a tela dos 3 botões (MenuActivity)
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}