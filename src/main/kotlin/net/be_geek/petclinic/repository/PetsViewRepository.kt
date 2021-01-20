package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.PetsView
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.query.Param

interface PetsViewRepository {

  companion object {
    const val FIND_ALL_PETS_VIEW_SQL = """
SELECT
  p.id as pets_id,
  p.name as pets_name,
  p.birth_date as pets_birth_date,
  p.type_id as pets_type_id,
  p.owner_id as pets_owner_id,
  t.id as types_id,
  t.name as types_name,
  v.id as visits_id,
  v.pet_id as visits_pet_id,
  v.visit_date as visits_visit_date,
  v.description as visits_description
FROM
  pets as p
INNER JOIN
  types as t
ON
  p.type_id = t.id
LEFT OUTER JOIN
  visits as v
ON
  p.id = v.pet_id
WHERE
  p.owner_id = :ownerId
"""
    const val FIND_PETS_VIEW_BY_ID_SQL = FIND_ALL_PETS_VIEW_SQL + "AND p.id = :petId"
  }

  @Query(FIND_ALL_PETS_VIEW_SQL)
  fun findAllPetsView(@Param("ownerId") ownerId: Int): Iterable<PetsView>

  @Query(FIND_PETS_VIEW_BY_ID_SQL)
  fun findPetsViewById(@Param("ownerId") ownerId: Int, @Param("petId") petId: Int): Iterable<PetsView>

}