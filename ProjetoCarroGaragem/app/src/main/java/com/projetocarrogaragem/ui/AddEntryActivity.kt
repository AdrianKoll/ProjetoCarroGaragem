package com.projetocarrogaragem.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.projetocarrogaragem.R
import com.projetocarrogaragem.data.AppDatabase
import com.projetocarrogaragem.model.VehicleEntity
import kotlinx.coroutines.launch

class AddEntryActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        db = AppDatabase.getDatabase(applicationContext)

        val etNome = findViewById<EditText>(R.id.editTextNome)
        val etMotivo = findViewById<EditText>(R.id.editTextText)
        val etPlaca = findViewById<EditText>(R.id.editTextPlaca)
        val etModeloCor = findViewById<EditText>(R.id.editTextModeloCor)
        val etData = findViewById<EditText>(R.id.editTextData)
        val etHoraEntrada = findViewById<EditText>(R.id.editTextHoraEntrada)
        val etHoraSaida = findViewById<EditText>(R.id.editTextHoraSaida)
        val etObservacao = findViewById<EditText>(R.id.editTextObservacao)
        val btnSalvar = findViewById<MaterialButton>(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString().trim()
            val motivo = etMotivo.text.toString().trim()
            val placa = etPlaca.text.toString().trim()
            val modeloCor = etModeloCor.text.toString().trim()
            val data = etData.text.toString().trim()
            val horaEntrada = etHoraEntrada.text.toString().trim()
            val horaSaida = etHoraSaida.text.toString().trim()
            val observacao = etObservacao.text.toString().trim()

            if (nome.isEmpty() || motivo.isEmpty() || placa.isEmpty() || modeloCor.isEmpty() || data.isEmpty() || horaEntrada.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dataHora = "$data $horaEntrada"

            lifecycleScope.launch {
                val existente = db.vehicleDao().checkVehicleByNameAndPlate(nome, placa)
                if (existente == null) {
                    val novo = VehicleEntity(
                        nomeCompleto = nome,
                        motivo = motivo,
                        placaCarro = placa,
                        modeloCor = modeloCor,
                        dataHora = dataHora,
                        horarioEntrada = horaEntrada,
                        horarioSaida = horaSaida.ifBlank { null },
                        observacao = observacao.ifBlank { null }
                    )
                    db.vehicleDao().insert(novo)
                    runOnUiThread {
                        Toast.makeText(this@AddEntryActivity, "Veículo cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@AddEntryActivity, "Já existe veículo com esse nome e placa!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}