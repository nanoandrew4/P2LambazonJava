package com.openclassrooms.shop.repository;

import com.openclassrooms.shop.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**

 * @author stanlick
 *
 */

@Repository
public class ProductRepository {
    private static List<Product> products;

    public ProductRepository()
    {
        products = new ArrayList<>();
        generateProductData();
    }

    /**
     * Generate the default list of products
     */
    private void generateProductData()
    {
        long id = 0;
        products.add(new Product(++id, 10, 92.50, "Echo Dot", "(2nd Generation) - Black"));
        products.add(new Product(++id, 20, 9.99, "Anker 3ft / 0.9m Nylon Braided", "Tangle-Free Micro USB Cable"));
        products.add(new Product(++id, 30, 69.99, "JVC HAFX8R Headphone", "Riptidz, In-Ear"));
        products.add(new Product(++id, 40, 32.50, "VTech CS6114 DECT 6.0", "Cordless Phone"));
        products.add(new Product(++id, 50, 895.00, "NOKIA OEM BL-5J", "Cell Phone "));
    }

    /**
     * @return All products from the inventory
     */
	public List<Product> findAll()
    {
		return products.stream().filter(p -> p.getStock() > 0).sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
    }

	/**
	 * Finds a product by the ID it was assigned in {@link #generateProductData()}. Since IDs start at 1, and array indices
	 * at 0, the supplied product ID must be greater than 0.
	 *
	 * @param productId Product ID of the product to retrieve, must be greater than 0
	 * @return The product matching the supplied ID, or an empty product if no product was found
	 */
	public Product findProductById(final Long productId) {
		if (productId > 0 && productId <= products.size())
			return products.get(productId.intValue() - 1);
		return new Product();
	}

    /**
     * @param productId ID of the getProductById
     * @param quantityToRemove Quantity of the getProductById
     */
    public void updateProductStocks(Long productId, int quantityToRemove) {
        Product product = products.stream().filter(p -> p.getId().longValue() == productId.longValue()).findFirst().get();
        product.setStock(product.getStock() - quantityToRemove);

        if (product.getStock() == 0){
            products.remove(product);
        }
    }
}
