package com.bankets.bankets.repo;

import com.bankets.bankets.models.PurchaseItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
public interface PurchaseItemsRepository extends CrudRepository<PurchaseItems,Long> {
}
