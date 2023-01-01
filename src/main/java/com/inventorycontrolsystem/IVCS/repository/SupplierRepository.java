package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventorycontrolsystem.IVCS.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer>{

	@Query("select s from Supplier s where s.supplierCode= :supplierCode ")
	public List<Supplier> findByCode(@Param("supplierCode") String supplierCode);

	@Query("select s from Supplier s where s.supplierCode=?1")
	 Supplier findBySupplierCode(String code);
}