package net.devfit.petclinic.domain.repository;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import net.devfit.petclinic.domain.model.Owner;

@Mapper
@Repository
public interface OwnersRepository {

  // jpa のように Eager Loading や Lazy Loading で関連テーブルの情報を取得する方法
  /*
  @Select({
    "select",
    "id, first_name, last_name, address, city, telephone",
    "from owners",
    "where id= #{id,jdbcType=INTEGER}"
  })
  @Results(id = "owner", value = {
    @Result(id = true, column = "id", property = "id"),
    @Result(column = "first_name", property = "firstName"),
    @Result(column = "last_name", property = "lastName"),
    @Result(column = "address", property = "address"),
    @Result(column = "city", property = "city"),
    @Result(column = "telephone", property = "telephone"),
    @Result(column = "id", property = "pets", many = @Many(select = "net.devfit.petclinic.domain.repository.PetsRepository.findByOwnerId", fetchType = FetchType.EAGER))
  })
  Owner findById(Integer id);
  */

  // テーブル結合した SQL で関連テーブルの情報を取得する方法
  @Select({
    "select",
    "o.id, o.first_name, o.last_name, o.address, o.city, o.telephone,",
    "p.id as pets_id, p.owner_id as pets_owner_id, p.name as pets_name, p.birth_date as pets_birth_date",
    "from owners as o left outer join pets as p on o.id = p.owner_id",
    "where o.id= #{id,jdbcType=INTEGER}"
  })
  @Results(id = "owner", value = {
    @Result(id = true, column = "id", property = "id"),
    @Result(column = "first_name", property = "firstName"),
    @Result(column = "last_name", property = "lastName"),
    @Result(column = "address", property = "address"),
    @Result(column = "city", property = "city"),
    @Result(column = "telephone", property = "telephone"),
    @Result(property = "pets", many = @Many(resultMap = "net.devfit.petclinic.domain.repository.PetsRepository.pet", columnPrefix = "pets_"))
  })
  Owner findById(Integer id);

}
