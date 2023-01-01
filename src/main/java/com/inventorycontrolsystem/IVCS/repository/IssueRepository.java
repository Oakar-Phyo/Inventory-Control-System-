package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventorycontrolsystem.IVCS.model.Issue;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Warehouse;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer>{
	
	 @Query("select i from Issue i where i.issueCode=?1")
	 public List<Issue> findByissueNo(String issueNo);
		
	 
	 @Query("select i from Issue i where i.issueCode=?1")
	 List<Issue> findByCode(String code);
	 
	 @Query("select i from Issue i where i.stock=?1 and i.issueCode=?2")
	 Issue findByStockCode(Stock stock,String issueItem);
	 
		@Query("SELECT i FROM Issue i GROUP BY i.issueCode ORDER BY i.date DESC")
		Page<Issue> findAll(Pageable pageable);

		@Query("select i from Issue i where i.issueCode=?1 group by i.issueCode")
		List<Issue> receiveViewList(String code);

		@Query("select i from Issue i where i.issueCode=?1 ")
		List<Issue> receiveViewListByStock(String code);

		 @Query("select i from Issue i where i.stock=?1 and i.warehouse=?2 and i.date between ?3 and ?4")
		 Page<Issue> findByAllCond(Stock stk, Warehouse wh, String start, String end, Pageable pageable);

		 @Query("select i from Issue i where i.stock=?1  and i.warehouse=?2")
		 Page<Issue> findByStkAndWh(Stock stk, Warehouse wh, Pageable pageable);

		 @Query("select i from Issue i where i.stock=?1 or i.warehouse=?2 or (i.date between ?3 and ?4)")
		    Page<Issue> findByAll(Stock stk,Warehouse wh,String start,String end,Pageable pageable);
		  
		  @Query( value = "select * from Issue group by issue_code", nativeQuery = true)
			List<Issue> issueGroupedList();
		
}
