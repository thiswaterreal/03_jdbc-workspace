package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.ProductService;
import com.kh.model.vo.Product;
import com.kh.view.ProductMenu;

public class ProductController {

	/** 1.
	 * 전체 상품 조회
	 */
	public void selectList() {
		
		ArrayList<Product> list = new ProductService().selectList();
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData("조회된 상품이 없습니다.");
		}else {
			new ProductMenu().displayProductList(list);
		}
		
	}
	
	/** 2.
	 *  상품 추가 하기
	 * @param productId
	 * @param pName
	 * @param price
	 * @param description
	 * @param stock
	 */
	public void insertProduct(String pId, String pName, String price, String description, String stock) {
		
		Product p = new Product(pId, pName, Integer.parseInt(price), description, Integer.parseInt(stock));
		
		int result = new ProductService().insertProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품이 성공적으로 추가되었습니다.");
		}else {
			new ProductMenu().displayFail("상품 추가 실패하였습니다.");
		}
	
	}
	
	/** 3.
	 * 상품 수정 하기
	 * @param pId
	 * @param pName
	 * @param price
	 * @param description
	 * @param stock
	 */
	public void updateProduct(String pId, String pName, String price, String description, String stock) {
		
		Product p = new Product(pId, pName, Integer.parseInt(price), description, Integer.parseInt(stock));
		
		int result = new ProductService().updateProduct(p);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품 정보가 성공적으로 수정되었습니다.");
		}else {
			new ProductMenu().displayFail("상품 정보 수정 실패하였습니다..");
		}
	}
	
	/** 4.
	 * 상품 삭제 하기
	 * @param pId
	 */
	public void deleteProduct(String pId) {
		
		int result = new ProductService().deleteProduct(pId);
		
		if(result > 0) {
			new ProductMenu().displaySuccess("상품이 성공적으로 삭제되었습니다.");
		}else {
			new ProductMenu().displayFail("상품 삭제 실패하였습니다..");
		}
		
	}
	
	/** 5.
	 * 상품 검색 하기 (상품ID 키워드로)
	 * @param keyword
	 */
	public void searchProduct(String keyword) {
		
		ArrayList<Product> list =  new ProductService().searchProduct(keyword);
		
		if(list.isEmpty()) {
			new ProductMenu().displayNoData(keyword + "로 조회된 상품이 없습니다..");
		}else {
			new ProductMenu().displayProductList(list);
		}
	}
	
}
