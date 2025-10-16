package com.koll.meu_app_car.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.koll.meu_app_car.R

class DetalhesEntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_entry)

        val tvDetalhes = findViewById<TextView>(R.id.tvDetalhes)

        // Receber dados do Intent
        val vehicleId = intent.getIntExtra("vehicleId", -1)
        val nome = intent.getStringExtra("nome") ?: ""
        val motivo = intent.getStringExtra("motivo") ?: ""
        val placa = intent.getStringExtra("placa") ?: ""
        val modeloCor = intent.getStringExtra("modeloCor") ?: ""
        val data = intent.getStringExtra("data") ?: ""
        val horaEntrada = intent.getStringExtra("horaEntrada") ?: ""
        val horaSaida = intent.getStringExtra("horaSaida") ?: ""
        val observacao = intent.getStringExtra("observacao") ?: ""

        // Montar texto de detalhes
        val detalhes = """
            📋 DETALHES DO VEÍCULO
            
            👤 Nome: $nome
            📝 Motivo: $motivo
            🚗 Placa: $placa
            🎨 Modelo/Cor: $modeloCor
            📅 Data: $data
            ⏰ Entrada: $horaEntrada
            ⏰ Saída: ${if (horaSaida.isBlank()) "—" else horaSaida}
            💬 Observação: ${if (observacao.isBlank()) "—" else observacao}
        """.trimIndent()

        tvDetalhes.text = detalhes
    }
}