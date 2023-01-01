package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventorycontrolsystem.IVCS.model.Damage;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockIn;
import com.inventorycontrolsystem.IVCS.model.Warehouse;

public interface StockInRepository extends JpaRepository<StockIn,Integer> {
	
	@Query("select s from StockIn s where s.code=?1")
	List<StockIn> findByCode(String code);
	
	@Query("select s from StockIn s where s.qty=?1")
	StockIn findByQty(Integer qty);
	
	@Query("select s from StockIn s where s.warehouse=?1")
	List<StockIn> findByWarehouseCode(Warehouse warehouse);
	
	@Query( value = "select * from stock_in group by code", nativeQuery = true)
	List<StockIn> receiveList();
	
	@Query("select s from StockIn s where s.stock=?1 and s.code=?2")
	StockIn findByStockCode(Stock stock,String receiveItem);
	
	
	@Query("select s from StockIn s where s.stock=?1 and s.warehouse=?2")
	StockIn findByWarehouseByStock(Stock stock,Warehouse warehouse);
	
	@Query("select s from StockIn s where s.code=?1 group by s.code")
	List<StockIn> receiveViewList(String code);
	
	@Query("select s from StockIn s where s.code=?1 ")
	List<StockIn> receiveViewListByStock(String code);
	
	@Query("select count(s.id), sum(s.qty), sum(s.subTotalAmount) from StockIn s where s.code=?1")
	List<StockIn> receiveViewListByTotal(String code);
	
	@Query("select sum(s.subTotalAmount) from StockIn s where s.date between ?1 and ?2")
	Integer selectForDashboard(String start,String end);
	
//	@Query("select s.code from stock_in group by s.code")
//	List<StockIn> viewList();
	
	
	
	@Query("SELECT s FROM StockIn s GROUP BY s.code ORDER BY s.date DESC")
	Page<StockIn> findAll(Pageable pageable);

	@Query("select s from StockIn s where s.exchangeRate=?1")
	StockIn findByExchangeRate(double exchangeRate);

	 @Query("select s from StockIn s where s.stock=?1")
	Page<StockIn> findByStock(Stock stk, Pageable pageable);

	 @Query("select s from StockIn s where s.warehouse=?1")
	Page<StockIn> findByWarehouseCode(Warehouse wh, Pageable pageable);

	 @Query("select s from StockIn s where s.date between ?1 and ?2")
	Page<StockIn> findByDate(String start, String end, Pageable pageable);

	 @Query("select s from StockIn s where s.stock=?1 and s.warehouse=?2 and s.date between ?3 and ?4")
	Page<StockIn> findByAllCond(Stock stk, Warehouse wh, String start, String end, Pageable pageable);

	 @Query("select s from StockIn s where s.stock=?1  and s.warehouse=?2")
	Page<StockIn> findByStkAndWh(Stock stk, Warehouse wh, Pageable pageable);

	 @Query("select s from StockIn s where s.stock=?1 or s.warehouse=?2 or (s.date between ?3 and ?4)")
	    Page<StockIn> findByAll(Stock stock,Warehouse warehouse,String start,String end,Pageable pageable);
}
