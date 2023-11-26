package com.bankets.bankets.repo;

import com.bankets.bankets.models.CompositeKey;
import com.bankets.bankets.models.Favorite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends CrudRepository<Favorite, CompositeKey> {

    @Query("SELECT e FROM Favorite e WHERE e.id.customer_id = :customer_id AND e.id.banquet_halls_id = :banquet_halls_id")
    List<Favorite> findByCompositeKey(@Param("customer_id") Long customer_id, @Param("banquet_halls_id") Long banquet_halls_id);

}


