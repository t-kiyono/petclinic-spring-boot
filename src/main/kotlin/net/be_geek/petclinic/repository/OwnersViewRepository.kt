package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.OwnersView
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.query.Param

interface OwnersViewRepository {

  companion object {
    const val FIND_ALL_OWNERS_VIEW_SQL = """
SELECT
  o.id as owners_id,
  o.first_name as owners_first_name,
  o.last_name as owners_last_name,
  o.address as owners_address,
  o.city as owners_city,
  o.telephone as owners_telephone,
  p.id as pets_id,
  p.name as pets_name,
  p.birth_date as pets_birth_date,
  p.type_id as pets_type_id,
  p.owner_id as pets_owner_id,
  t.id as types_id,
  t.name as types_name
FROM
  owners as o
LEFT OUTER JOIN
  pets as p
ON
  o.id = p.owner_id
LEFT OUTER JOIN
  types as t
ON
  p.type_id = t.id
"""
    const val FIND_ALL_OWNERS_VIEW_BY_LAST_NAME_SQL = FIND_ALL_OWNERS_VIEW_SQL + "WHERE o.last_name LIKE concat(:lastName, '%')"
    const val FIND_OWNERS_VIEW_BY_ID_SQL = FIND_ALL_OWNERS_VIEW_SQL + "WHERE o.id = :ownerId"
  }

  @Query(FIND_ALL_OWNERS_VIEW_SQL)
  fun findAllOwnersView(): Iterable<OwnersView>

  @Query(FIND_ALL_OWNERS_VIEW_BY_LAST_NAME_SQL)
  fun findAllOwnersViewByLastName(@Param("lastName") lastName: String): Iterable<OwnersView>

  @Query(FIND_OWNERS_VIEW_BY_ID_SQL)
  fun findOwnersViewById(@Param("ownerId") ownerId: Int): Iterable<OwnersView>

}