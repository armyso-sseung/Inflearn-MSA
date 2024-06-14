package com.example.catalogservice.service;

import com.example.catalogservice.vo.ResponseCatalog;

import java.util.List;

public interface CatalogService {
    /**
     * @title   : 카탈로그 목록 조회
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    List<ResponseCatalog> getCatalogByAll();

    /**
     * @title   : 카탈로그 조회 ( 조건 : 카탈로그 번호 )
     * @author  : 정승현
     * @since   : 2024/06/14
     */
    ResponseCatalog getCatalogByProductId(String productId);
}
