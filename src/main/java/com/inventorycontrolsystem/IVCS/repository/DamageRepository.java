package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventorycontrolsystem.IVCS.model.Damage;
import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.Warehouse;


public interface DamageRepository extends JpaRepository<Damage, Integer> {

	@Query("select d from Damage d where d.damageCode=?1")
    public List<Damage> findBydamageNo(String damageNo);
	
	
	@Query ("select d from Damage d group by d.damageCode")
	public List<Damage>findAllList();
	
	//page
	@Query("SELECT d FROM Damage d GROUP BY d.damageCode ORDER BY d.date DESC")
	Page<Damage> findAllPage(Pageable pageable);

	@Query("select d from Damage d where d.damageCode=?1 group by d.damageCode")
	List<Damage> receiveViewList(String code);
	
	//DamageViewbyStock
	@Query("select d from Damage d where d.damageCode=?1")
	List<Damage>receiveViewListByStock(String code);
	
	
	
	//Report
	

    @Query("select d from Damage d")
    Page<Damage> findAllNormal(Pageable pageable);
    
    @Query("select d from Damage d where d.stock=?1")
    Page<Damage> findByStock(Stock stock,Pageable pageable);
    
    @Query("select d from Damage d where d.warehouse=?1 ")
    Page<Damage> findByWarehouseCode(Warehouse warehouse,Pageable pageable);
    
    @Query("select d from Damage d where d.date between ?1 and ?2")
    Page<Damage> findByDate(String start,String end,Pageable pageable);
    
    @Query("select d from Damage d where d.stock=?1 or d.warehouse=?2 or (d.date between ?3 and ?4)")
    Page<Damage> findByAll(Stock stock,Warehouse warehouse,String start,String end,Pageable pageable);
    
    @Query("select d from Damage d where d.stock=?1 and d.warehouse=?2 and d.date between ?3 and ?4")
    Page<Damage> findByAllCond(Stock stock,Warehouse warehouse,String start,String end,Pageable pageable);
    
    
    @Query("select d from Damage d where d.stock=?1  and d.warehouse=?2")
    Page<Damage> findByStkAndWh(Stock stock,Warehouse warehouse,Pageable pageable);
	
	
	
	
	
	
}
