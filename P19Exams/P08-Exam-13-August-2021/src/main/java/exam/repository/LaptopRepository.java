package exam.repository;

import exam.model.dto.LaptopExportDTO;
import exam.model.entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop,Long> {

    Optional<Laptop> findByMacAddress(String macAddress);

    @Query("SELECT new exam.model.dto.LaptopExportDTO(l.macAddress, l.cpuSpeed, l.ram, l.storage, l.price, l.shop.name, l.shop.town.name) " +
            "FROM Laptop l " +
            "ORDER BY l.cpuSpeed DESC, l.ram DESC, l.storage DESC, l.macAddress ")
    List<LaptopExportDTO> findByMacAddressCpuSpeedRamStoragePriceShopAndTownName();

}
