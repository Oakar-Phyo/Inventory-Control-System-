package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.inventorycontrolsystem.IVCS.model.Adjustment;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Warehouse;



@Repository
public interface AdjustmentRepository extends JpaRepository<Adjustment, Integer>{
	
	
	@Query("select a from Adjustment a where a.adjustmentCode=?1")
    public List<Adjustment> findByAdjusmentNo(String adjstmentNo);
	
	@Query("select a from Adjustment a group by a.adjustmentCode ")
	public List<Adjustment> findAll();
	
	//pagination
	@Query("SELECT a FROM Adjustment a GROUP BY a.adjustmentCode ORDER BY a.date DESC")
	Page<Adjustment> findAllPage(Pageable pageable);
	
	//adjustmentView
	@Query("select a from Adjustment a where a.adjustmentCode=?1 group by a.adjustmentCode")
	List<Adjustment> adjustmentViewList(String code);
	
	//adjustmentViewByStock
	@Query("select a from Adjustment a where a.adjustmentCode=?1 ")
	List<Adjustment> receiveViewListByStock(String code);
	
	
	//Adjustment Report

	@Query("select a from Adjustment a where a.stock=?1 or a.warehouse=?2 or (a.date between ?3 and ?4)")
	Page<Adjustment> searchAll (Stock stock,Warehouse warehouse, String startDate, String endDate, Pageable pageable);

	@Query("select a from Adjustment a where a.stock=?1 and a.warehouse=?2")
	Page<Adjustment> searchByStockAndWarhouse(Stock stock, Warehouse warehouse, Pageable pageable);
	
	 @Query("select a from Adjustment a where a.stock=?1 and a.warehouse=?2 and a.date between ?3 and ?4")
	 Page<Adjustment> findByAllCond(Stock stock, Warehouse warehouse, String startDate, String endDate,
			Pageable pageable);

	 @Query("select a from Adjustment a where a.stock=?1  and a.warehouse=?2")
	 Page<Adjustment> findByStkAndWh(Stock stock, Warehouse warehouse, Pageable pageable);
}
