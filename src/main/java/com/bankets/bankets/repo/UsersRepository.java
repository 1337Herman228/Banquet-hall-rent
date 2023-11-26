package com.bankets.bankets.repo;

import com.bankets.bankets.models.BanquetHalls;
import com.bankets.bankets.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users,Long>{
    Optional<Users> findByLogin(String login);
}
