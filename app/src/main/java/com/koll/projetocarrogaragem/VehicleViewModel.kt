package com.koll.projetocarrogaragem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VehicleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VehicleRepository

    val allVehicles: LiveData<List<Vehicle>>

    init {
        val vehicleDao = AppDatabase.getDatabase(application).vehicleDao()
        repository = VehicleRepository(vehicleDao)
        allVehicles = repository.allVehicles.asLiveData()
    }

    fun insert(vehicle: Vehicle) = viewModelScope.launch {
        repository.insert(vehicle)
    }

    fun update(vehicle: Vehicle) = viewModelScope.launch {
        repository.update(vehicle)
    }

    fun delete(vehicle: Vehicle) = viewModelScope.launch {
        repository.delete(vehicle)
    }

    suspend fun getVehicleByPlate(plate: String): Vehicle? {
        return repository.getVehicleByPlate(plate)
    }
}
