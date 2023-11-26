package com.bankets.bankets.repo;

import com.bankets.bankets.models.BanquetHalls;
import com.bankets.bankets.models.Purchases;
import org.springframework.data.repository.CrudRepository;
public interface PurchasesRepository extends CrudRepository<Purchases,Long>{
}
