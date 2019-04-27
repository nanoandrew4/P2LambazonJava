package com.openclassrooms.shop.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Cart {
	private List<CartLine> cartLineList;

	public Cart() {
		cartLineList = new ArrayList<>();
	}

	/**
	 * @return the actual cartline list
	 */
	public List<CartLine> getCartLineList() {
		return cartLineList;
	}

	/**
	 * Adds a product to the cart or increments its quantity in the cart if it has already been added.
	 *
	 * @param product  The product to add to the cart
	 * @param quantity The quantity of the product that is desired. If the product already exists in the cart,
	 *                 this quantity will be added to the existing quantity
	 */
	public void addItem(Product product, int quantity) {
		final Predicate<CartLine> filterByProductId = cl -> cl.getProduct().getId().longValue() == product.getId().longValue();
		final CartLine cartLine = getCartLineList().stream().filter(filterByProductId).findFirst().orElse(null);

		if (cartLine == null && quantity > 0)
			getCartLineList().add(new CartLine(product, quantity));
		else if (quantity > 0)
			cartLine.setQuantity(cartLine.getQuantity() + quantity);
	}

	/**
	 * Removes a getProductById form the cart
	 *
	 * @param product the getProductById to be removed
	 */
	public void removeLine(Product product) {
		getCartLineList().removeIf(l -> l.getProduct().getId().equals(product.getId()));
	}


	/**
	 * @return total value of a cart
	 */
	public double getTotalValue() {
		return cartLineList.stream().map(CartLine::getSubtotal).reduce((a, b) -> a += b).orElse(0D);
	}

	/**
	 * @return Get average value of a cart
	 */
	public double getAverageValue() {
		double averageValue = 0d;
		long totalUnitsInCart = 0;

		for (CartLine cartLine : cartLineList) {
			averageValue += cartLine.getSubtotal();
			totalUnitsInCart += cartLine.getQuantity();
		}

		return totalUnitsInCart > 0 ? averageValue / totalUnitsInCart : 0;
	}

	/**
	 * @param productId the getProductById id to search for
	 * @return getProductById in the cart if it finds it
	 */
	public Product findProductInCartLines(Long productId) {
		final Predicate<Product> filterById = product -> product.getId().longValue() == productId.longValue();
		return cartLineList.stream().map(CartLine::getProduct).filter(filterById).findFirst().orElseGet(Product::new);
	}

	/**
	 * @param index index of the cartLine
	 * @return CartLine in that index
	 */
	public CartLine getCartLineByIndex(int index) {
		return getCartLineList().get(index);
	}

	/**
	 * Clears a the cart of all added products
	 */
	public void clear() {
		List<CartLine> cartLines = getCartLineList();
		cartLines.clear();
	}
}
