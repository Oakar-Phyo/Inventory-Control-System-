package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventorycontrolsystem.IVCS.model.IssueReturn;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Warehouse;

@Repository
public interface IssueReturnRepository extends JpaRepository<IssueReturn, Integer>{
	
	 @Query("select i from IssueReturn i where i.code=?1")
     public List<IssueReturn> findByIssueNo(String issueReturnNo);
	 
	 @Query("SELECT i FROM IssueReturn i GROUP BY i.code ORDER BY i.date DESC")
		Page<IssueReturn> findAll(Pageable pageable);
	 
	 @Query("select i from IssueReturn i group by i.code")
	 //@Query( value = "select * from IssueReturn group by code", nativeQuery = true)
		List<IssueReturn> issueReturnList();

	 @Query("select i from IssueReturn i where i.code=?1 group by i.code")
		List<IssueReturn> issueReturnViewList(String code);
	 
	 @Query("select i from IssueReturn i where i.code=?1 ")
		List<IssueReturn> issueReturnViewListByStock(String code);
	 
	 @Query("select i from IssueReturn i where i.code=?1")
		List<IssueReturn> findByCode(String code);
	 
	 @Query("select i from IssueReturn i where i.stock=?1 and i.warehouse=?2 and i.date between ?3 and ?4")
	 Page findByAllCond(Stock stock,Warehouse warehouse,String start,String end,Pageable pageable); 
	 
	 @Query("select i from IssueReturn i where i.stock=?1 and i.warehouse=?2") 
	 Page findByStkAndWh(Stock stock,Warehouse warehouse,Pageable pageable); 
	 
	 @Query("select i from IssueReturn i where i.stock=?1 or i.warehouse=?2 or (i.date between ?3 and ?4)") 
	 Page findByAll(Stock stock,Warehouse warehouse,String start,String end,Pageable pageable);
	 
}
