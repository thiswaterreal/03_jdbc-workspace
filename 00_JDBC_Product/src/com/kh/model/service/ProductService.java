package com.kh.model.service;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;

import com.kh.model.dao.ProductDao;
import com.kh.model.vo.Product;

public class ProductService {


	/** 2.
	 * @param p
	 * @return
	 */
	public int insertProduct(Product p) {
		
		// 1) jdbc driver 등록
		// 2) Connection 객체 생성
		Connection conn = /*JDBCTemplate.*/getConnection();
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
	
	
}
