package com.bookstore.productservice.controller;
import com.bookstore.productservice.dto.ProductDto;
import com.bookstore.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api") @Tag(name="Products")
public class ProductController {
    @Autowired private ProductService productService;
    @GetMapping("/products") @Operation(summary="List all products paginated")
    public ResponseEntity<Page<ProductDto.Response>> getAll(Pageable pageable){ return ResponseEntity.ok(productService.getAll(pageable)); }
    @GetMapping("/products/{id}") @Operation(summary="Get product by ID")
    public ResponseEntity<ProductDto.Response> getById(@PathVariable Long id){ return ResponseEntity.ok(productService.getById(id)); }
    @GetMapping("/products/search") @Operation(summary="Search products")
    public ResponseEntity<List<ProductDto.Response>> search(@RequestParam String q){ return ResponseEntity.ok(productService.search(q)); }
    @GetMapping("/products/category/{id}") @Operation(summary="Filter by category")
    public ResponseEntity<List<ProductDto.Response>> byCategory(@PathVariable Long id){ return ResponseEntity.ok(productService.getByCategory(id)); }
    @PostMapping("/products") @PreAuthorize("hasRole('ADMIN')") @Operation(summary="Create product")
    public ResponseEntity<ProductDto.Response> create(@Valid @RequestBody ProductDto.Request req){ return ResponseEntity.status(201).body(productService.create(req)); }
    @PutMapping("/products/{id}") @PreAuthorize("hasRole('ADMIN')") @Operation(summary="Update product")
    public ResponseEntity<ProductDto.Response> update(@PathVariable Long id, @Valid @RequestBody ProductDto.Request req){ return ResponseEntity.ok(productService.update(id,req)); }
    @DeleteMapping("/products/{id}") @PreAuthorize("hasRole('ADMIN')") @Operation(summary="Delete product")
    public ResponseEntity<Void> delete(@PathVariable Long id){ productService.delete(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/categories") @Operation(summary="List categories")
    public ResponseEntity<List<ProductDto.CategoryResponse>> getCategories(){ return ResponseEntity.ok(productService.getAllCategories()); }
    @PostMapping("/categories") @PreAuthorize("hasRole('ADMIN')") @Operation(summary="Create category")
    public ResponseEntity<ProductDto.CategoryResponse> createCategory(@Valid @RequestBody ProductDto.CategoryRequest req){ return ResponseEntity.status(201).body(productService.createCategory(req)); }
}