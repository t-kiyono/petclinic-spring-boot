package net.devfit.petclinic.domain.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import net.devfit.petclinic.domain.model.Pet;

@Mapper
@Repository
public interface PetsRepository {

  @Select({
    "select",
    "id, owner_id, name, birth_date",
    "from pets",
    "where id= #{id,jdbcType=INTEGER}"
  })
  @Results(id = "pet", value = {
    @Result(id = true, column = "id", property = "id"),
    @Result(column = "owner_id", property = "ownerId"),
    @Result(column = "name", property = "name"),
    @Result(column = "birth_date", property = "birthDate"),
  })
  Pet findById(Integer id);

  @Select({
    "select",
    "id, owner_id, name, birth_date",
    "from pets",
    "where owner_id= #{ownerId,jdbcType=INTEGER}"
  })
  @ResultMap("pet")
  List<Pet> findByOwnerId(Integer ownerId);
}
