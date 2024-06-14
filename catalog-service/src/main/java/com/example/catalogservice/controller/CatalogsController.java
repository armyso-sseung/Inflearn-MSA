package com.example.catalogservice.controller;

import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogsController {
    private final Environment env;

    private final CatalogService catalogService;

    /**
     * @title   : 상태체크
     * @author  : 정승현
     * @since   : 2024/06/14
     * @param   : HTTP Request
     * @return  : 서버 포트 메시지
     */
    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return String.format("It's Working in Catalog Service on PORT %s", request.getServerPort());
    }

    /**
     * @title   : 카탈로그 목록 조회
     * @author  : 정승현
     * @since   : 2024/06/14
     * @return  : 카탈로그 목록
     */
    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogList() {
        List<ResponseCatalog> catalogList = catalogService.getCatalogByAll();
        return ResponseEntity.ok().body(catalogList);
    }
}
