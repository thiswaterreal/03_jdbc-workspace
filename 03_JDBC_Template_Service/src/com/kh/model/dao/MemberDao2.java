package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;


public class MemberDao2 {
	

	
	/** 1.
	 * 회원 추가하는 메소드
	 * @param m : (9개)
	 * @return result : 처리된 행수
	 */
	public int insertMember(Member m) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			pstmt = conn.prepareStatement(sql);	
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			result = pstmt.executeUpdate(); 
			
			// 여기서 SQLException 날 수도 있기 때문에 뒤에 커밋과 롤백이 있으면 실행이 안됨!
			// 문제가 생기면 무조건 롤백을 해야함
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			try {
				if(pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if(pstmt != null && !pstmt.isClosed()) {
					conn.close();	
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		if(result > 0) {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.commit();				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.rollback();				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	
	/** 2.
	 * 회원 전체 조회 메소드
	 * @return
	 */
	public ArrayList<Member> selectList() {
		// select문(여러행) => ResultSet 객체
		
		ArrayList<Member> list = new ArrayList<Member>(); // 텅 빈 리스트
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
		
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Member(rset.getInt("userno"),
									rset.getString("userid"),
									rset.getString("userpwd"),
									rset.getString("username"),
									rset.getString("gender"),
									rset.getInt("age"),
									rset.getString("email"),
									rset.getString("phone"),
									rset.getString("address"),
									rset.getString("hobby"),
									rset.getDate("enrolldate")
									));
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
		
	}
	
	
	/** 3.
	 * 회원 아이디를 받아 회원 정보 검색해주ㅠ는 메소드
	 * @param userId : 회원이 검색하고자 하는 아이디
	 * @return m : 그 아이디를 가진 회원
	 */
	public Member selectByUserId(String userId) {
		// select문 => 한 행 => ResultSet객체 => Member 객체(list필요없음)
		
		Member m = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId); //이제야 비로소 완벽
			
			rset = pstmt.executeQuery();
			
			//한행조회니까 while아님 (3:10)
			if(rset.next()) {
				m = new Member(rset.getInt("userno"),
								rset.getString("userid"),
								rset.getString("userpwd"),
								rset.getString("username"),
								rset.getString("gender"),
								rset.getInt("age"),
								rset.getString("email"),
								rset.getString("phone"),
								rset.getString("address"),
								rset.getString("hobby"),
								rset.getDate("enrolldate")
								);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m;
	}
	
	
	
	/** 4.
	 * 사용자의 이름으로 키워드 검색 요청시 처리해주는 메소드
	 * @param keyword
	 * @return
	 */
	public ArrayList<Member> selectByUserName(String keyword) {
		// select문 수행 (여러행) => ResultSet
		// ArrayList로 짜야함
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, "%" + keyword + "%"); // '%뫄%'
			
			rset = pstmt.executeQuery(); 

			while(rset.next()) {
				list.add(new Member(rset.getInt("userno"),
									rset.getString("userid"),
									rset.getString("userpwd"),
									rset.getString("username"),
									rset.getString("gender"),
									rset.getInt("age"),
									rset.getString("email"),
									rset.getString("phone"),
									rset.getString("address"),
									rset.getString("hobby"),
									rset.getDate("enrolldate")
									));
				
			}
			

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list; 
		
	}
	
	
	/** 5.
	 * 사용자가 입력한 아이디의 정보 변경 요청 처리해주는 메소드
	 * @param m
	 * @return
	 */
	public int updateMember(Member m) {
		// update문 => 처리된 행수(int) => 트랜젝션 처리
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ? WHERE USERID = ?";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getUserId());
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return result;
		
	}
	
	

	/** 6.
	 * 사용자가 입력한 아이디의 회원 정보를 삭제하는 메소드
	 * @param userId : 사용자가 입력한 아이디
	 * @return result : 처리된 행수
	 */
	public int deleteMember(String userId2) {
		// delete문 => 처리된 행수(int) => 트랜젝션 처리
		
		int result = 0;	
		
		Connection conn = null;	
		PreparedStatement pstmt = null;
		
		// DELETE FROM MEMBER WHERE USERID = ?
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId2);
			
			result = pstmt.executeUpdate();

			if(result > 0) { // 성공시, 커밋
				conn.commit();
			}else { // 실패시, 롤백
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result; 
		
	}
	
	
	
	
	
}
