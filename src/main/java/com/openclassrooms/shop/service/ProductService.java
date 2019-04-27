package com.openclassrooms.shop.service;

import com.openclassrooms.shop.domain.Product;
import com.openclassrooms.shop.repository.OrderRepository;
import com.openclassrooms.shop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	private ProductRepository productRepository;
	private OrderRepository orderRepository;

	@Autowired
	public ProductService(ProductRepository repository, OrderRepository orderRepository) {
		this.productRepository = repository;
		this.orderRepository = orderRepository;
	}

	/**
	 * @return all products from the inventory
	 */
	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}

	/**
	 * Retrieves a product by id. If the product is not found, an empty product is created and returned.
	 *
	 * @param productId Id of the product
	 * @return a product form the inventory, or an empty product instance if it was not found
	 */
	public Product getProductById(Long productId)
	{
		return productRepository.findAll().stream().filter(p -> productId.longValue() == p.getId().longValue()).findFirst().orElseGet(Product::new);
	}

	/**
	 * Update the quantities left for each product in the inventory depending of ordered the quantities
	 * @param productId ID of the product to be updated
	 */
	public void updateProductQuantities(Long productId, int quantity)
	{
		productRepository.updateProductStocks(productId, quantity);
	}
}
