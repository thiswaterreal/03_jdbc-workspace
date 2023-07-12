package com.kh.controller;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductMenu;

public class ProductController {

	/** 2.
	 *  상품 추가 하기
	 * @param productId
	 * @param pName
	 * @param price
	 * @param description
	 * @param stock
	 */
	public void insertProduct(String productId, String pName, int price, String description, int stock) {
		
		Product p = new Product(productId, pName, price, description, stock);
		
		int result = new ProductService().insertProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품이 성공적으로 추가되었습니다.");
		}else {
			new ProductMenu().displayFail("상품 추가 실패하였습니다.");
		}
	
	}
}
