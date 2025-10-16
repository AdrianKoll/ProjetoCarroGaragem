package com.koll.projetocarrogaragem

import kotlinx.coroutines.flow.Flow

class VehicleRepository(private val vehicleDao: VehicleDao) {

    val allVehicles: Flow<List<Vehicle>> = vehicleDao.getAllVehicles()

    suspend fun insert(vehicle: Vehicle) {
        vehicleDao.insert(vehicle)
    }

    suspend fun update(vehicle: Vehicle) {
        vehicleDao.update(vehicle)
    }

    suspend fun delete(vehicle: Vehicle) {
        vehicleDao.delete(vehicle)
    }

    suspend fun getVehicleByPlate(plate: String): Vehicle? {
        return vehicleDao.getVehicleByPlate(plate)
    }
}
