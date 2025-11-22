package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.model.Inventario;


public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByProductoIdProductoAndTallaTalla(Long idProducto, String talla);

    @Modifying
    @Query("UPDATE Inventario i SET i.stock = i.stock - :cantidad WHERE i.idInventario = :idInventario AND i.stock >= :cantidad")
    int updateStockForVenta(@Param("idInventario") Long idInventario, @Param("cantidad") int cantidad);

    List<Inventario> findByProductoIdProducto(Long idProducto);

    @Modifying
    @Query("UPDATE Inventario i SET i.stock = i.stock + :cantidad WHERE i.idInventario = :idInventario")
    int reponerStockPorCancelacion(@Param("idInventario") Long idInventario, @Param("cantidad") int cantidad);
}