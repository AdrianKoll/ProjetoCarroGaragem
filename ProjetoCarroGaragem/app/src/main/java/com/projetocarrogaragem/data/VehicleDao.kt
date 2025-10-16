package com.projetocarrogaragem.data

import androidx.room.*
import com.projetocarrogaragem.model.VehicleEntity

@Dao
interface VehicleDao {

    @Insert
    suspend fun insert(entry: VehicleEntity)

    @Update
    suspend fun update(entry: VehicleEntity)

    @Delete
    suspend fun delete(entry: VehicleEntity)

    @Query("SELECT * FROM vehicle_entries ORDER BY id DESC")
    suspend fun getAll(): List<VehicleEntity>

    @Query("SELECT * FROM vehicle_entries WHERE placaCarro LIKE '%' || :query || '%' ORDER BY id DESC")
    suspend fun searchByPlaca(query: String): List<VehicleEntity>

    @Query("SELECT * FROM vehicle_entries WHERE nomeCompleto LIKE '%' || :query || '%' ORDER BY id DESC")
    suspend fun searchByNome(query: String): List<VehicleEntity>

    @Query("SELECT * FROM vehicle_entries WHERE motivo LIKE '%' || :query || '%' ORDER BY id DESC")
    suspend fun searchByMotivo(query: String): List<VehicleEntity>

    @Query("SELECT * FROM vehicle_entries WHERE dataHora LIKE '%' || :query || '%' ORDER BY id DESC")
    suspend fun searchByDataHora(query: String): List<VehicleEntity>

    @Query("""
        SELECT * FROM vehicle_entries 
        WHERE placaCarro LIKE '%' || :query || '%' 
           OR nomeCompleto LIKE '%' || :query || '%' 
           OR motivo LIKE '%' || :query || '%' 
           OR modeloCor LIKE '%' || :query || '%' 
           OR dataHora LIKE '%' || :query || '%' 
        ORDER BY id DESC
    """)
    suspend fun searchAll(query: String): List<VehicleEntity>

    @Query("SELECT * FROM vehicle_entries WHERE placaCarro = :licensePlate LIMIT 1")
    suspend fun getVehicleByLicensePlate(licensePlate: String): VehicleEntity?

    // ✅ Novo: verificar se já existe veículo com mesmo nome e placa
    @Query("SELECT * FROM vehicle_entries WHERE nomeCompleto = :nome AND placaCarro = :placa LIMIT 1")
    suspend fun checkVehicleByNameAndPlate(nome: String, placa: String): VehicleEntity?

    // ✅ Novo: deletar por prefixo do nome
    @Query("DELETE FROM vehicle_entries WHERE nomeCompleto LIKE :prefix || '%'")
    suspend fun deleteByNamePrefix(prefix: String)

    // ✅ Novo: buscar veículos por nome parcial
    @Query("SELECT * FROM vehicle_entries WHERE nomeCompleto LIKE '%' || :query || '%' ORDER BY nomeCompleto ASC")
    suspend fun getVehiclesByPartialName(query: String): List<VehicleEntity>
}