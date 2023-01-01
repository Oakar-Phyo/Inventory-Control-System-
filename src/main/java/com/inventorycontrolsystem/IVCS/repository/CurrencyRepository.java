package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventorycontrolsystem.IVCS.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

	@Query("SELECT c FROM Currency c WHERE c.name =:name or c.sname= :sname")
    public List<Currency> getCurrencyByCode(@Param("name")String name, @Param("sname") String sname);

	@Query("select c from Currency c where c.currencyCode=?1")
	 Currency findByCurrencyCode(String code);
}
