package com.projetocarrogaragem.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.projetocarrogaragem.R
import com.projetocarrogaragem.data.AppDatabase
import com.projetocarrogaragem.model.VehicleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VehicleAdapter
    private lateinit var editTextSearch: EditText
    private lateinit var textViewNoResults: TextView
    private lateinit var chipAll: Chip
    private lateinit var chipPlaca: Chip
    private lateinit var chipNome: Chip
    private lateinit var chipMotivo: Chip
    private lateinit var chipDataHora: Chip
    private lateinit var database: AppDatabase

    private var allVehicles = emptyList<VehicleEntity>()
    private var currentSearchType = "all"
    private val modo: String = "editar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)

        database = AppDatabase.getDatabase(applicationContext)

        initViews()
        setupRecyclerView()
        setupSearch()
        setupChips()
    }

    override fun onResume() {
        super.onResume()
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
        chipDataHora = findViewById(R.id.chipDataHora)

        // Botão voltar no header
        findViewById<View>(R.id.imageViewBack)?.setOnClickListener { finish() }

        // "Todos" selecionado por padrão
        chipAll.isChecked = true
        currentSearchType = "all"
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VehicleAdapter(
            items = emptyList(),
            onCheck = { showVehicleDetails(it) },
            onEdit = { editVehicle(it) },
            onDelete = { deleteVehicle(it) },
            modo = modo
        )
        recyclerView.adapter = adapter
    }

    private fun setupSearch() {
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                performSearch(s?.toString()?.trim().orEmpty())
            }
        })
    }

    private fun setupChips() {
        val chips = listOf(chipAll, chipPlaca, chipNome, chipMotivo, chipDataHora)
        chips.forEach { chip ->
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    chips.filter { it != chip }.forEach { it.isChecked = false }
                    currentSearchType = when (chip.id) {
                        R.id.chipPlaca -> "placa"
                        R.id.chipNome -> "nome"
                        R.id.chipMotivo -> "motivo"
                        R.id.chipDataHora -> "dataHora"
                        else -> "all"
                    }
                    performSearch(editTextSearch.text.toString().trim())
                }
            }
        }
    }

    private fun loadVehicles() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val lista = database.vehicleDao().getAll()
                allVehicles = lista
                withContext(Dispatchers.Main) { updateAdapter(lista) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    updateAdapter(emptyList())
                    Toast.makeText(
                        this@VehicleListActivity,
                        "Erro ao carregar veículos: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun deleteVehicle(vehicle: VehicleEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                database.vehicleDao().delete(vehicle)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@VehicleListActivity, "Veículo excluído!", Toast.LENGTH_SHORT).show()
                    loadVehicles()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@VehicleListActivity,
                        "Erro ao excluir: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun performSearch(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val results = if (query.isEmpty()) {
                allVehicles
            } else {
                try {
                    when (currentSearchType) {
                        "placa" -> database.vehicleDao().searchByPlaca(query)
                        "nome" -> database.vehicleDao().searchByNome(query)
                        "motivo" -> database.vehicleDao().searchByMotivo(query)
                        "dataHora" -> database.vehicleDao().searchByDataHora(query)
                        else -> database.vehicleDao().searchAll(query)
                    }
                } catch (e: Exception) {
                    // Fallback local
                    allVehicles.filter { v ->
                        when (currentSearchType) {
                            "placa" -> v.placaCarro.contains(query, true)
                            "nome" -> v.nomeCompleto.contains(query, true)
                            "motivo" -> v.motivo.contains(query, true)
                            "dataHora" -> v.dataHora.contains(query, true)
                            else -> v.placaCarro.contains(query, true) ||
                                    v.nomeCompleto.contains(query, true) ||
                                    v.motivo.contains(query, true) ||
                                    v.modeloCor.contains(query, true)
                        }
                    }
                }
            }
            withContext(Dispatchers.Main) { updateAdapter(results) }
        }
    }

    private fun updateAdapter(vehicles: List<VehicleEntity>) {
        adapter.updateItems(vehicles)
        textViewNoResults.visibility = if (vehicles.isEmpty()) View.VISIBLE else View.GONE
        recyclerView.visibility = if (vehicles.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun showVehicleDetails(vehicle: VehicleEntity) {
        Toast.makeText(this, "Detalhes de ${vehicle.nomeCompleto}", Toast.LENGTH_SHORT).show()
    }

    private fun editVehicle(vehicle: VehicleEntity) {
        val intent = Intent(this, AddEntryActivity::class.java).apply {
            putExtra("vehicleId", vehicle.id)
            putExtra("nome", vehicle.nomeCompleto)
            putExtra("motivo", vehicle.motivo)
            putExtra("placa", vehicle.placaCarro)
            putExtra("modeloCor", vehicle.modeloCor)
            putExtra("dataHora", vehicle.dataHora)
            putExtra("horaEntrada", vehicle.horarioEntrada)
            putExtra("horaSaida", vehicle.horarioSaida)
            putExtra("observacao", vehicle.observacao)
            putExtra("modo", "editar")
        }
        startActivity(intent)
    }
}
