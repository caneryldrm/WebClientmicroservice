package com.example.barcodeMicroService.repository;

import com.example.barcodeMicroService.model.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Integer> {

}
