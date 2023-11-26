package com.bankets.bankets.repo;

import com.bankets.bankets.models.BanquetHalls;
import com.bankets.bankets.models.Roles;
import com.bankets.bankets.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolesRepository extends CrudRepository<Roles,Long>{
    Optional<Roles> findByPosition(String position);
}
