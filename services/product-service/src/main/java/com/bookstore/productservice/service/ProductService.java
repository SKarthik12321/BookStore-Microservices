package com.bookstore.productservice.service;
import com.bookstore.productservice.dto.ProductDto;
import com.bookstore.productservice.entity.Category;
import com.bookstore.productservice.entity.Product;
import com.bookstore.productservice.repository.CategoryRepository;
import com.bookstore.productservice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductService {
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    public Page<ProductDto.Response> getAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::toResponse);
    }
    public ProductDto.Response getById(Long id) {
        return toResponse(productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product not found: "+id)));
    }
    public List<ProductDto.Response> search(String q) {
        return productRepository.search(q).stream().map(this::toResponse).collect(Collectors.toList());
    }
    public List<ProductDto.Response> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream().map(this::toResponse).collect(Collectors.toList());
    }
    public ProductDto.Response create(ProductDto.Request req) {
        Category category = req.getCategoryId() != null ? categoryRepository.findById(req.getCategoryId()).orElse(null) : null;
        Product p = Product.builder().title(req.getTitle()).author(req.getAuthor()).isbn(req.getIsbn())
            .price(req.getPrice()).stockQuantity(req.getStockQuantity()).imageUrl(req.getImageUrl())
            .description(req.getDescription()).category(category).build();
        return toResponse(productRepository.save(p));
    }
    public ProductDto.Response update(Long id, ProductDto.Request req) {
        Product p = productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product not found: "+id));
        Category category = req.getCategoryId() != null ? categoryRepository.findById(req.getCategoryId()).orElse(null) : null;
        p.setTitle(req.getTitle()); p.setAuthor(req.getAuthor()); p.setIsbn(req.getIsbn());
        p.setPrice(req.getPrice()); p.setStockQuantity(req.getStockQuantity());
        p.setImageUrl(req.getImageUrl()); p.setDescription(req.getDescription()); p.setCategory(category);
        return toResponse(productRepository.save(p));
    }
    public void delete(Long id) { productRepository.deleteById(id); }
    public boolean reduceStock(Long id, int qty) {
        Product p = productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product not found"));
        if (p.getStockQuantity() < qty) return false;
        p.setStockQuantity(p.getStockQuantity() - qty);
        productRepository.save(p); return true;
    }
    // Category
    public List<ProductDto.CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(c->ProductDto.CategoryResponse.builder().id(c.getId()).name(c.getName()).description(c.getDescription()).build()).collect(Collectors.toList());
    }
    public ProductDto.CategoryResponse createCategory(ProductDto.CategoryRequest req) {
        Category c = Category.builder().name(req.getName()).description(req.getDescription()).build();
        c = categoryRepository.save(c);
        return ProductDto.CategoryResponse.builder().id(c.getId()).name(c.getName()).description(c.getDescription()).build();
    }
    private ProductDto.Response toResponse(Product p) {
        return ProductDto.Response.builder().id(p.getId()).title(p.getTitle()).author(p.getAuthor())
            .isbn(p.getIsbn()).price(p.getPrice()).stockQuantity(p.getStockQuantity())
            .imageUrl(p.getImageUrl()).description(p.getDescription())
            .categoryId(p.getCategory()!=null?p.getCategory().getId():null)
            .categoryName(p.getCategory()!=null?p.getCategory().getName():null)
            .createdAt(p.getCreatedAt()).build();
    }
}