package com.example.catalogservice.service;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private final Environment env;

    private final CatalogRepository catalogRepository;

    /**
     * @title       : 카탈로그 목록 조회
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 카탈로그 목록을 조회 한다.
     */
    @Override
    public List<ResponseCatalog> getCatalogByAll() {
        Iterable<CatalogEntity> dataList = catalogRepository.findAll();

        ModelMapper mapper = new ModelMapper();
        List<ResponseCatalog> catalogList = StreamSupport.stream(dataList.spliterator(), false)
                .map(catalog -> mapper.map(catalog, ResponseCatalog.class))
                .collect(Collectors.toList());

        return catalogList;
    }

    /**
     * @title       : 카탈로그 조회 ( 조건 : 카탈로그 번호 )
     * @author      : 정승현
     * @since       : 2024/06/14
     * @description : 카탈로그 번호를 통해서 카탈로그를 조회 한다.
     */
    @Override
    public ResponseCatalog getCatalogByProductId(String productId) {
        CatalogEntity catalog = catalogRepository.findByProductId(productId);

        ModelMapper mapper = new ModelMapper();
        return mapper.map(catalog, ResponseCatalog.class);
    }
}
