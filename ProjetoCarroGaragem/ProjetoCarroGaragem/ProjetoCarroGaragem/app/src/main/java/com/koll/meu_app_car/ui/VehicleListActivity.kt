package com.koll.meu_app_car.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.koll.meu_app_car.R
import com.koll.meu_app_car.data.AppDatabase
import com.koll.meu_app_car.model.VehicleEntry
import kotlinx.coroutines.launch

class VehicleListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VehicleAdapter
    private lateinit var editTextSearch: TextInputEditText
    private lateinit var textViewNoResults: TextView
    private lateinit var chipAll: Chip
    private lateinit var chipPlaca: Chip
    private lateinit var chipNome: Chip
    private lateinit var chipMotivo: Chip
    private lateinit var modo: String
    private lateinit var database: AppDatabase
    
    private var allVehicles = listOf<VehicleEntry>()
    private var currentSearchType = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)

        modo = intent.getStringExtra("modo") ?: "visualizar"
        
        // Inicializar banco de dados
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "vehicle_database"
        ).build()

        initViews()
        setupRecyclerView()
        setupSearch()
        setupChips()
    }

    override fun onResume() {
        super.onResume()
        // Recarregar dados sempre que a tela voltar ao foco
        loadVehicles()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewVehicles)
        editTextSearch = findViewById(R.id.editTextSearch)
        textViewNoResults = findViewById(R.id.textViewNoResults)
        chipAll = findViewById(R.id.chipAll)
        chipPlaca = findViewById(R.id.chipPlaca)
        chipNome = findViewById(R.id.chipNome)
        chipMotivo = findViewById(R.id.chipMotivo)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = VehicleAdapter(
            items = emptyList(),
            onCheck = { veiculo ->
                // Não precisa fazer nada aqui, o adapter já gerencia
            },
            onEdit = { veiculo ->
                // Não precisa fazer nada aqui, o adapter já gerencia
            },
            onDelete = { veiculo ->
                deleteVehicle(veiculo)
            },
            modo = modo
        )

        recyclerView.adapter = adapter
    }

    private fun setupSearch() {
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                performSearch(query)
            }
        })
    }

    private fun setupChips() {
        chipAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                uncheckOtherChips(chipAll)
                currentSearchType = "all"
                performSearch(editTextSearch.text.toString().trim())
            }
        }

        chipPlaca.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                uncheckOtherChips(chipPlaca)
                currentSearchType = "placa"
                performSearch(editTextSearch.text.toString().trim())
            }
        }

        chipNome.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                uncheckOtherChips(chipNome)
                currentSearchType = "nome"
                performSearch(editTextSearch.text.toString().trim())
            }
        }

        chipMotivo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                uncheckOtherChips(chipMotivo)
                currentSearchType = "motivo"
                performSearch(editTextSearch.text.toString().trim())
            }
        }
    }

    private fun uncheckOtherChips(selectedChip: Chip) {
        val chips = listOf(chipAll, chipPlaca, chipNome, chipMotivo)
        chips.forEach { chip ->
            if (chip != selectedChip) {
                chip.isChecked = false
            }
        }
    }

    private fun loadVehicles() {
        lifecycleScope.launch {
            try {
                allVehicles = database.vehicleDao().getAll()
                

                
                updateAdapter(allVehicles)
            } catch (e: Exception) {
                Toast.makeText(this@VehicleListActivity, "Erro ao carregar veículos: ${e.message}", Toast.LENGTH_LONG).show()
                updateAdapter(emptyList())
            }
        }
    }

    private fun deleteVehicle(vehicle: VehicleEntry) {
        lifecycleScope.launch {
            try {
                database.vehicleDao().delete(vehicle)
                Toast.makeText(this@VehicleListActivity, "Veículo excluído com sucesso!", Toast.LENGTH_SHORT).show()
                loadVehicles() // Recarregar lista
            } catch (e: Exception) {
                Toast.makeText(this@VehicleListActivity, "Erro ao excluir: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun performSearch(query: String) {
        if (query.isEmpty()) {
            updateAdapter(allVehicles)
            return
        }

        lifecycleScope.launch {
            try {
                val results = when (currentSearchType) {
                    "placa" -> database.vehicleDao().searchByPlaca(query)
                    "nome" -> database.vehicleDao().searchByNome(query)
                    "motivo" -> database.vehicleDao().searchByMotivo(query)
                    else -> database.vehicleDao().searchAll(query)
                }
                updateAdapter(results)
            } catch (e: Exception) {
                // Fallback para busca local se o banco não estiver disponível
                val results = allVehicles.filter { vehicle ->
                    when (currentSearchType) {
                        "placa" -> vehicle.placaCarro.contains(query, ignoreCase = true)
                        "nome" -> vehicle.nomeCompleto.contains(query, ignoreCase = true)
                        "motivo" -> vehicle.motivo.contains(query, ignoreCase = true)
                        else -> vehicle.placaCarro.contains(query, ignoreCase = true) ||
                                vehicle.nomeCompleto.contains(query, ignoreCase = true) ||
                                vehicle.motivo.contains(query, ignoreCase = true) ||
                                vehicle.modeloCor.contains(query, ignoreCase = true)
                    }
                }
                updateAdapter(results)
            }
        }
    }

    private fun updateAdapter(vehicles: List<VehicleEntry>) {
        adapter = VehicleAdapter(
            items = vehicles,
            onCheck = { veiculo ->
                // Não precisa fazer nada aqui, o adapter já gerencia
            },
            onEdit = { veiculo ->
                // Não precisa fazer nada aqui, o adapter já gerencia
            },
            onDelete = { veiculo ->
                deleteVehicle(veiculo)
            },
            modo = modo
        )
        recyclerView.adapter = adapter

        // Mostrar/ocultar mensagem de "nenhum resultado"
        if (vehicles.isEmpty()) {
            textViewNoResults.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            textViewNoResults.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
}