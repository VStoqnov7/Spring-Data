package com.example.modelmapper_json_xml.repositories;

import com.example.modelmapper_json_xml.entiti.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {
}
