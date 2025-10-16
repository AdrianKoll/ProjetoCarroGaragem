package com.koll.projetocarrogaragem

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.koll.projetocarrogaragem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardVerificarRegistrar.setOnClickListener {
            startActivity(Intent(this, CheckVehicleActivity::class.java))
        }

        binding.cardEditar.setOnClickListener {
            startActivity(Intent(this, VehicleListActivity::class.java))
        }

        binding.imageViewLogout.setOnClickListener {
            // Implementar lógica de logout, por exemplo, voltar para a tela de login
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }
    }
}
