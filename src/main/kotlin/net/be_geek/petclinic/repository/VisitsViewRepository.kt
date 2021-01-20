package net.be_geek.petclinic.repository

import net.be_geek.petclinic.entity.VisitsView
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.query.Param

interface VisitsViewRepository {

  companion object {
    const val FIND_ALL_VISITS_VIEW_SQL = """
SELECT
  v.id as visits_id,
  v.pet_id as visits_pet_id,
  v.visit_date as visits_visit_date,
  v.description as visits_description
FROM
  visits as v
WHERE
  v.pet_id = :petId
"""
    const val FIND_VISITS_VIEW_BY_ID_SQL = FIND_ALL_VISITS_VIEW_SQL + "AND v.id = :visitId"
  }

  @Query(FIND_ALL_VISITS_VIEW_SQL)
  fun findAllVisitsView(@Param("petId") petId: Int): Iterable<VisitsView>

  @Query(FIND_VISITS_VIEW_BY_ID_SQL)
  fun findVisitsViewById(@Param("petId") petId: Int, @Param("visitId") visitId: Int): Iterable<VisitsView>

}