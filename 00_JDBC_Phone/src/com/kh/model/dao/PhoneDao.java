package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.kh.common.JDBCTemplate.*; // 이제 편해요~
import com.kh.model.vo.Phone;

public class PhoneDao {

	public int insertPhone(Connection conn, Phone p) {
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO PHONE VALUES(SEQ_USERNONO.NEXTVAL, ?, ?, ?, ?)";
		
		try {
			// 3) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getUserName());
			pstmt.setInt(2, p.getAge());
			pstmt.setString(3, p.getAddress());
			pstmt.setString(4, p.getPhoneNum());
			
			// 4,5) sql문 실행 및 결과 받기
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*JDBCTemplate.*/close(pstmt);
		}
		
		return result;
	}
	
	
}
