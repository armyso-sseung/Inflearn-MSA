package com.example.catalogservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
    /**
     * @title   : 카탈로그 조회 ( 조건 : 카탈로그 번호 )
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    CatalogEntity findByProductId(String productId);
}
