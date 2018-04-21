package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Sale;

public interface SaleRepository extends JpaRepository<Sale,Integer>{

	
}
