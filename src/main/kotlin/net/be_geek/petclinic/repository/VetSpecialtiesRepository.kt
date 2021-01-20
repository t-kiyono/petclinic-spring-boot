package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.VetSpecialties
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

interface VetSpecialtiesRepository: Repository<VetSpecialties, Void> {

  @Modifying
  @Query("INSERT INTO vet_specialties VALUES (:vetId, :specialtyId)")
  fun create(@Param("vetId") vetId: Int,
             @Param("specialtyId") specialtyId: Int)

  @Modifying
  @Query("DELETE FROM vet_specialties WHERE vet_id = :vetId")
  fun deleteByVetId(@Param("vetId") vetId: Int)

  @Modifying
  @Query("DELETE FROM vet_specialties WHERE specialty_id = :specialtyId")
  fun deleteBySpecialtyId(@Param("specialtyId") specialtyId: Int)

}
