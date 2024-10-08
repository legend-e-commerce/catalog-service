package com.example.catalogservice.controller;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog-service")
public class CatalogController {

    private final CatalogService catalogService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("건강해요 PORT: %s", env.getProperty("local.server.port"));
    }
    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        Iterable<CatalogEntity> catelogEntities = catalogService.getAllCatalogs();

        ArrayList<ResponseCatalog> result = new ArrayList<>();

        catelogEntities.forEach(catalog -> result.add(new ModelMapper().map(catalog, ResponseCatalog.class)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
