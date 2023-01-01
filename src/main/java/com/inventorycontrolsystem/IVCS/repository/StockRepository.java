package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.inventorycontrolsystem.IVCS.model.Stock;

public interface StockRepository extends JpaRepository<Stock,Integer> {
	
	@Query("select stk from Stock stk where stk.name= :stockName")
	public List<Stock> findByname(@Param("stockName") String stk1 );

	/*
	 * @Query("select s from Stock s where s.stockCode=?1") Stock findByCode(String
	 * code);
	 * 
	 * @Query("select s.stockCode, s.name, u.name from Stock s, Unit u where s.id=u.id"
	 * )
	 * 
	 * 
	 * @Query("SELECT l FROM Location l JOIN l.user u")
	 */
	@Query("select s from Stock s join s.unit u where s.stockCode=?1")
	Stock findByCode(String code);
	
	@Query("select s from Stock s where s.stockCode=?1")
	Stock findByCodeForReturn(String code);
	

}
