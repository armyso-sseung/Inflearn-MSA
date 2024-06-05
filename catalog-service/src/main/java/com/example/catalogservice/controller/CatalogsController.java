package com.example.catalogservice.controller;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogsController {
    private final Environment env;

    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return String.format("It's Working in Catalog Service on PORT %s", request.getServerPort());
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogList() {
        Iterable<CatalogEntity> catalogList = catalogService.getCatalogByAll();

        ModelMapper mapper = new ModelMapper();
        List<ResponseCatalog> catalogs = StreamSupport.stream(catalogList.spliterator(), false)
                .map(catalog -> mapper.map(catalog, ResponseCatalog.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(catalogs);
    }
}
