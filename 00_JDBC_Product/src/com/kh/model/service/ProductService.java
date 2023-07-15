package com.kh.model.service;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.model.dao.ProductDao;
import com.kh.model.vo.Product;

public class ProductService {

	/** 1.
	 * 전체 상품 조회
	 * @return
	 */
	public ArrayList<Product> selectList() {
		
		Connection conn = /*JDBCTemplate.*/getConnection();
		ArrayList<Product> list = new ProductDao().selectList(conn);
		
		close(conn);
		
		return list;
		
	}
	
	/** 2.
	 * 상품 추가 하기
	 * @param p
	 * @return
	 */
	public int insertProduct(Product p) {
		
		// 1) jdbc driver 등록
		// 2) Connection 객체 생성
		Connection conn = getConnection();
		int result = new ProductDao().insertProduct(conn, p);
		
		// 6) 트랜젝션
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		// 7) 반납
		close(conn);
		
		// 8) 결과 반환
		return result;
		
	}
	
	/** 3.
	 * 상품 수정 하기
	 * @param p
	 * @return
	 */
	public int updateProduct(Product p) {
		
		Connection conn = getConnection();
		int result = new ProductDao().updateProduct(conn, p);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	/** 4.
	 * 상품 삭제 하기
	 * @param pId
	 * @return
	 */
	public int deleteProduct(String pId) {
		
		Connection conn = getConnection();
		int result = new ProductDao().deleteProduct(conn, pId);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	/** 5.
	 * 상품 검색 하기 (상품ID 키워드로)
	 * @param keyword
	 * @return
	 */
	public ArrayList<Product> searchProduct(String keyword) {
		
		Connection conn = getConnection();
		ArrayList<Product> list = new ProductDao().searchProduct(conn, keyword);
		
		close(conn);
		
		return list;
		
	}
	
}
