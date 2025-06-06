package io.zenika.ismaildrissi.distributeur_automatique_backend.controller.rest;

import io.zenika.ismaildrissi.distributeur_automatique_backend.application.dto.ProductDTO;
import io.zenika.ismaildrissi.distributeur_automatique_backend.application.service.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing product-related operations in the vending machine system.
 *
 * <p>This controller provides HTTP endpoints for retrieving product information
 * from the vending machine. It serves as the entry point for client applications
 * to access product data and integrates with the application service layer.</p>
 *
 * <p>The controller is configured to handle CORS requests from the frontend
 * application running on localhost:3000, enabling seamless integration with
 * web-based user interfaces.</p>
 *
 * <p>All endpoints return JSON responses containing product information suitable
 * for display in client applications.</p>
 *
 * @author Ismail Drissi
 * @since 1.0
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    /** Service for handling product-related business logic */
    private final ProductService productService;

    /**
     * Constructs a new ProductController with the specified ProductService.
     *
     * @param productService the service for handling product operations
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves all products available in the vending machine.
     *
     * <p>This endpoint returns a complete list of all products currently
     * available in the vending machine, including their names, prices,
     * and quantities.</p>
     *
     * @return a list of ProductDTO objects representing all available products
     */
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.listProducts();
    }
}