package Com.cine.ms_sucursales.repository;

import org.springframework.stereotype.Repository;
import Com.cine.ms_sucursales.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

}
