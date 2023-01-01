package com.inventorycontrolsystem.IVCS.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.inventorycontrolsystem.IVCS.model.Stock;
import com.inventorycontrolsystem.IVCS.model.StockOut;
import com.inventorycontrolsystem.IVCS.model.Warehouse;
import com.inventorycontrolsystem.IVCS.model.WarehouseTransfer;

public interface WarehouseTransferRepository extends JpaRepository<WarehouseTransfer, Integer>{
      @Query("select w from WarehouseTransfer w where w.transferNo=?1")
      public List<WarehouseTransfer> findByTransferNo(String transferNo);
      
      @Query("select h from WarehouseTransfer h GROUP BY h.transferNo ORDER BY h.transferDate DESC")
      Page<WarehouseTransfer> findAll(Pageable pageable);
      
      @Query("select t from WarehouseTransfer t where t.transferNo=?1 group by t.transferNo")
      List<WarehouseTransfer> reviewListByTransferNo(String no);
      
      @Query("select t from WarehouseTransfer t where t.transferNo=?1")
      List<WarehouseTransfer> reviewListByTransferNoAll(String no);
      
      @Query("select s from WarehouseTransfer s where s.stock=?1 or s.fromWarehouse=?2 or s.toWarehouse=?3  or (s.transferDate between ?4 and ?5)")
  	Page<WarehouseTransfer> findByAll(Stock stock, Warehouse warehouse1,Warehouse warehouse2, String startDate, String endDate, Pageable pageable);
      
      @Query("select s from WarehouseTransfer s where s.stock=?1 and s.fromWarehouse=?2 and s.toWarehouse=?3 and s.transferDate between ?4 and ?5")
      Page<WarehouseTransfer> findByAllCond(Stock stock,Warehouse warehouse1,Warehouse warehouse2,String start,String end,Pageable pageable);
      
      @Query("select s from WarehouseTransfer s where s.stock=?1  and s.fromWarehouse=?2 and s.toWarehouse=?3")
      Page<WarehouseTransfer> findByStkAndWh(Stock stock,Warehouse warehouse1,Warehouse warehouse2,Pageable pageable);
}
