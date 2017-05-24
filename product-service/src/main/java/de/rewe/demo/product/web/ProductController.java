package de.rewe.demo.product.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.rewe.demo.product.domain.Product;
import de.rewe.demo.product.exceptions.NotFoundException;
import de.rewe.demo.product.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Responsible for REST operations involving products. Reading, creating,
 * searching, etdi, etc.
 */
@Api(tags = "students")
@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	/**
	 * Get a product by ID.
	 * 
	 * @param studentId
	 * @return
	 */
	@ApiOperation("Retrieves a product by the given ID.")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Product not found."),
			@ApiResponse(code = 200, message = "OK") })
	@RequestMapping(method = RequestMethod.GET, value = "/api/products/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Product getProductById(
			@ApiParam(name="product", value = "The ID of the product.", required = true) 
			@PathVariable Integer productId) {
		return requireNotNull(productRepository.findOne(productId), productId);

	}

	private static Product requireNotNull(Product p, Integer productId) {
		return Optional.ofNullable(p).orElseThrow(()->new NotFoundException("Product with Id " + productId
				+ " not found!")); 
	}

}