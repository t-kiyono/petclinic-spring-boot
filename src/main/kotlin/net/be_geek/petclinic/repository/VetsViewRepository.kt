package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.VetsView
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.query.Param

interface VetsViewRepository {

  companion object {
    const val FIND_ALL_VETS_VIEW_SQL = """
SELECT
  v.id as vets_id,
  v.first_name as vets_first_name,
  v.last_name as vets_last_name,
  s.id as specialties_id,
  s.name as specialties_name
FROM
  vets as v
LEFT OUTER JOIN
  vet_specialties as vs
ON
  v.id = vs.vet_id
LEFT OUTER JOIN
  specialties as s
ON
  vs.specialty_id = s.id
"""
    const val FIND_VETS_VIEW_BY_ID = FIND_ALL_VETS_VIEW_SQL + "WHERE v.id = :vetId"
  }

  @Query(FIND_ALL_VETS_VIEW_SQL)
  fun findAllVetsView(): Iterable<VetsView>

  @Query(FIND_VETS_VIEW_BY_ID)
  fun findVetsViewById(@Param("vetId") vetId: Int): Iterable<VetsView>

}