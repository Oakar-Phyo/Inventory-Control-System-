package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventorycontrolsystem.IVCS.model.Adjustment;
import com.inventorycontrolsystem.IVCS.model.Damage;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockOut;
import com.inventorycontrolsystem.IVCS.model.Warehouse;

@Repository
public interface StockOutRepository extends JpaRepository<StockOut, Integer>{

	@Query( value = "select * from stock_out group by code", nativeQuery = true)
	List<StockOut> receiveReturnList();
	
	@Query("SELECT s FROM StockOut s GROUP BY s.code ORDER BY s.date DESC")
	Page<StockOut> findAll(Pageable pageable);
	
	@Query("select s from StockOut s where s.code=?1 group by s.code")
	List<StockOut> receiveViewList(String code);
	
	@Query("select s from StockOut s where s.code=?1 ")
	List<StockOut> receiveViewListByStock(String code);
	
	@Query("select s from StockOut s where s.stock=?1 or s.warehouse=?2 or (s.date between ?3 and ?4)")
	Page<StockOut> findReceiveReport(Stock stock, Warehouse warehouse, String startDate, String endDate, Pageable pageable);

	 @Query("select s from StockOut s where s.stock=?1 and s.warehouse=?2 and s.date between ?3 and ?4")
	Page<StockOut> findByAllCond(Stock stock, Warehouse warehouse, String startDate, String endDate, Pageable pageable);

	 @Query("select s from StockOut s where s.stock=?1  and s.warehouse=?2")
	Page<StockOut> findByStkAndWh(Stock stock, Warehouse warehouse, Pageable pageable);

	 @Query("select s from StockOut s where s.code=?1")
	List<StockOut> findByreturnNo(String nrenNo);
	 
}
