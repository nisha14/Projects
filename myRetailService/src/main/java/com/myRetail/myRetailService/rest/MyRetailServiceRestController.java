package com.myRetail.myRetailService.rest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.myRetailService.model.CurrentPrice;
import com.myRetail.myRetailService.model.Product;
import com.myRetail.myRetailService.service.CurrentPriceService;

@RestController
@RequestMapping("/rest")
public class MyRetailServiceRestController {

	@Autowired
	private CurrentPriceService service;

	@GetMapping("/products/{productId}")
	public Product getProduct(@PathVariable int productId) {
		Optional<Product> product = findRemoteProduct(productId);

		if (product.isPresent()) {
			Optional<CurrentPrice> p = service.findById(productId);
			if (p.isPresent()) {
				product.get().setPrice(p.get());
			}
			return product.get();
		}

		return null;
	}

	@PutMapping("/products/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable(value = "productId") int productId,
			@RequestBody Product reqProduct) throws Exception {
		Optional<Product> product = findRemoteProduct(productId);
		if (product.isPresent() && reqProduct != null) {
			CurrentPrice price = reqProduct.getPrice();
			if (price != null) {
				service.update(productId, price.getValue(), price.getCurrency());
			}
		}

		return ResponseEntity.ok("Current Price saved");
	}

	private Optional<Product> findRemoteProduct(int productId) {
		if (productId > 0) {
			StringBuffer buf = new StringBuffer("https://redsky.target.com/v2/pdp/tcin/");
			buf.append(productId).append(
					"?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,"
							+ "question_answer_statistics");

			try {
				URL url = new URL(buf.toString());
				HttpURLConnection conection = (HttpURLConnection) url.openConnection();
				conection.setRequestMethod("GET");

				int responseCode = conection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					JsonNode jsonNode = new ObjectMapper().readTree(conection.getInputStream());
					JsonNode id = jsonNode.path("product").path("available_to_promise_network").get("product_id");
					JsonNode title = jsonNode.path("product").path("item").path("product_description").get("title");
					String name = StringEscapeUtils.unescapeHtml4(title.asText()); // format html chars - &nbsp etc
					return Optional.ofNullable(new Product(id.asInt(), name, null));
				}
			} catch (Exception e) {
				System.out.println("Error Fetching Product:" + productId);
			}
		}
		return Optional.empty();
	}
}
