package com.koll.meu_app_car.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.koll.meu_app_car.R
import com.koll.meu_app_car.data.AppDatabase
import com.koll.meu_app_car.model.VehicleEntry
import kotlinx.coroutines.launch

class AddEntryActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        // Inicializar banco de dados
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "vehicle_database"
        ).build()

        // Referências aos campos
        val etMotivo = findViewById<TextInputEditText>(R.id.editTextText)
        val etNome = findViewById<TextInputEditText>(R.id.editTextText2)
        val etPlaca = findViewById<TextInputEditText>(R.id.editTextText3)
        val etModeloCor = findViewById<TextInputEditText>(R.id.editTextText4)
        val etData = findViewById<TextInputEditText>(R.id.editTextData)
        val etHoraEntrada = findViewById<TextInputEditText>(R.id.editTextText5)
        val etHoraSaida = findViewById<TextInputEditText>(R.id.editTextText6)
        val etObservacao = findViewById<TextInputEditText>(R.id.editTextText7)
        val btnSalvar = findViewById<MaterialButton>(R.id.btnSalvar)

        // --- Preenche os campos se vierem dados para edição ---
        val vehicleId = intent.getIntExtra("vehicleId", -1)
        val nomeExistente = intent.getStringExtra("nome")
        val motivoExistente = intent.getStringExtra("motivo")
        val placaExistente = intent.getStringExtra("placa")
        val modeloCorExistente = intent.getStringExtra("modeloCor")
        val dataExistente = intent.getStringExtra("data")
        val horaEntradaExistente = intent.getStringExtra("horaEntrada")
        val horaSaidaExistente = intent.getStringExtra("horaSaida")
        val observacaoExistente = intent.getStringExtra("observacao")

        nomeExistente?.let { etNome.setText(it) }
        motivoExistente?.let { etMotivo.setText(it) }
        placaExistente?.let { etPlaca.setText(it) }
        modeloCorExistente?.let { etModeloCor.setText(it) }
        dataExistente?.let { etData.setText(it) }
        horaEntradaExistente?.let { etHoraEntrada.setText(it) }
        horaSaidaExistente?.let { etHoraSaida.setText(it) }
        observacaoExistente?.let { etObservacao.setText(it) }

        // --- Máscaras automáticas ---

        // Placa (XXX-XXXX)
        etPlaca.addTextChangedListener(object : TextWatcher {
            var isUpdating = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true
                val texto = s.toString().replace("-", "").uppercase()
                val formatado = if (texto.length > 3) texto.substring(0, 3) + "-" + texto.substring(3) else texto
                etPlaca.setText(formatado)
                etPlaca.setSelection(formatado.length)
                isUpdating = false
            }
        })

        // Data (DD/MM/AAAA)
        etData.addTextChangedListener(object : TextWatcher {
            var isUpdating = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true
                val texto = s.toString().replace("/", "")
                val formatado = when {
                    texto.length >= 5 -> texto.substring(0, 2) + "/" + texto.substring(2, 4) + "/" + texto.substring(4)
                    texto.length >= 3 -> texto.substring(0, 2) + "/" + texto.substring(2)
                    else -> texto
                }
                etData.setText(formatado)
                etData.setSelection(formatado.length)
                isUpdating = false
            }
        })

        // Hora de entrada (HH:MM)
        etHoraEntrada.addTextChangedListener(object : TextWatcher {
            var isUpdating = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true
                val texto = s.toString().replace(":", "")
                val formatado = if (texto.length >= 3) texto.substring(0, 2) + ":" + texto.substring(2) else texto
                etHoraEntrada.setText(formatado)
                etHoraEntrada.setSelection(formatado.length)
                isUpdating = false
            }
        })

        // Hora de saída (HH:MM)
        etHoraSaida.addTextChangedListener(object : TextWatcher {
            var isUpdating = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true
                val texto = s.toString().replace(":", "")
                val formatado = if (texto.length >= 3) texto.substring(0, 2) + ":" + texto.substring(2) else texto
                etHoraSaida.setText(formatado)
                etHoraSaida.setSelection(formatado.length)
                isUpdating = false
            }
        })

        // --- Clique do botão Salvar ---
        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString().trim()
            val motivo = etMotivo.text.toString().trim()
            val placa = etPlaca.text.toString().trim()
            val modeloCor = etModeloCor.text.toString().trim()
            val data = etData.text.toString().trim()
            val horaEntrada = etHoraEntrada.text.toString().trim()
            val horaSaida = etHoraSaida.text.toString().trim()
            val observacao = etObservacao.text.toString().trim()

            // Validação
            if (nome.isBlank()) {
                etNome.error = "Informe o nome completo"
                etNome.requestFocus()
                return@setOnClickListener
            }
            if (motivo.isBlank()) {
                etMotivo.error = "Informe o motivo"
                etMotivo.requestFocus()
                return@setOnClickListener
            }
            if (placa.isBlank()) {
                etPlaca.error = "Informe a placa"
                etPlaca.requestFocus()
                return@setOnClickListener
            }
            if (modeloCor.isBlank()) {
                etModeloCor.error = "Informe o modelo/cor"
                etModeloCor.requestFocus()
                return@setOnClickListener
            }
            if (data.isBlank()) {
                etData.error = "Informe a data"
                etData.requestFocus()
                return@setOnClickListener
            }
            if (horaEntrada.isBlank()) {
                etHoraEntrada.error = "Informe a hora de entrada"
                etHoraEntrada.requestFocus()
                return@setOnClickListener
            }

            // Salvar no banco de dados
            lifecycleScope.launch {
                try {
                    if (vehicleId != -1) {
                        // Atualizar registro existente
                        val vehicleEntry = VehicleEntry(
                            id = vehicleId,
                            nomeCompleto = nome,
                            motivo = motivo,
                            placaCarro = placa,
                            modeloCor = modeloCor,
                            data = data,
                            horarioEntrada = horaEntrada,
                            horarioSaida = horaSaida.ifBlank { null },
                            observacao = observacao.ifBlank { null }
                        )
                        database.vehicleDao().update(vehicleEntry)
                        Toast.makeText(this@AddEntryActivity, "Veículo atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish() // Fecha a tela após atualização bem-sucedida
                    } else {
                        // Criar novo registro
                        val vehicleEntry = VehicleEntry(
                            id = 0, // Room irá gerar automaticamente
                            nomeCompleto = nome,
                            motivo = motivo,
                            placaCarro = placa,
                            modeloCor = modeloCor,
                            data = data,
                            horarioEntrada = horaEntrada,
                            horarioSaida = horaSaida.ifBlank { null },
                            observacao = observacao.ifBlank { null }
                        )
                        val existingVehicle = database.vehicleDao().getVehicleByLicensePlate(placa)
                        if (existingVehicle == null) {
                            database.vehicleDao().insert(vehicleEntry)
                            Toast.makeText(this@AddEntryActivity, "Veículo cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                            finish() // Fecha a tela apenas se salvou com sucesso
                        } else {
                            Toast.makeText(this@AddEntryActivity, "Erro: Já existe um veículo com esta placa!", Toast.LENGTH_LONG).show()
                            return@launch // Não fecha a tela se há erro
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@AddEntryActivity, "Erro ao salvar: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}