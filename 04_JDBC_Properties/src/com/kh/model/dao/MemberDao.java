package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.model.vo.Member;

// ***** 가장 중요!! *****
// DAO(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 SQL문 실행 후 결과 받기 (JDBC작업)
//							 결과를 Controller 로 다시 리턴
public class MemberDao {
	
	/*
	 * 기존의 방식 : DAO 클래스가 사용자가 요청할 때마다 실행해야되는 SQL문을 자바소스코드 내에 명시적으로 작성 => 정적코딩방식
	 * 
	 * > 문제점  : SQL문을 수정해야 될 경우 자바소스코드를 수정해야됨 => 수정된 내용을 반영시키고자 한다면 프로그램을 재구동 해야됨
	 * 
	 * > 해결방식 : SQL문들을 별도로 관리하는 외부파일(.xml)을 만들어서 실시간으로 그 파일에 기록된 sql문을 읽어들여서 실행 => 동적코딩방식
	 * 			 여려줄 쓸 수 있도록 => xml로 하는게 좋음
	 */
	
	// properties 를 적용해보자!
	private Properties prop = new Properties();
	
	public MemberDao() {
		try {
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/** 1.
	 * 회원 추가하는 메소드
	 * @param m : (9개)
	 * @return result : 처리된 행수
	 */
	public int insertMember(Connection conn, Member m) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		// conn 이미 서비스에서 생성되어 있음!
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertMember");
		
		try {
			
			// 3) PreparedStatement 객체 생성
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
			
			// 4,5) sql문 실행 및 결과 받기
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*JDBCTemplate.*/close(pstmt); // pstmt 는 다 썼으니까 반납
			// conn은 아직 반납하면 안됨!! (트랜젝션 처리 서비스가서 해야함)
		}
		
		return result;
	}
	
	
	/** 2.
	 * 회원 전체 조회 메소드
	 * @return
	 */
	public ArrayList<Member> selectList(Connection conn) {
		// select문(여러행) => ResultSet 객체
		ArrayList<Member> list = new ArrayList<Member>(); // 텅 빈 리스트
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
			
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
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
		
	}
	
	
	/** 3.
	 * 회원 아이디를 받아 회원 정보 검색해주ㅠ는 메소드
	 * @param userId : 회원이 검색하고자 하는 아이디
	 * @return m : 그 아이디를 가진 회원
	 */
	public Member selectByUserId(Connection conn, String userId) {
		// select문 => 한 행 => ResultSet객체 => Member 객체(list필요없음)
		
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectByUserId");
		
		try {
	
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m;
	}
	
	
	
	/** 4.
	 * 사용자의 이름으로 키워드 검색 요청시 처리해주는 메소드
	 * @param keyword
	 * @return
	 */
	public ArrayList<Member> selectByUserName(Connection conn, String keyword) {
		// select문 수행 (여러행) => ResultSet
		// ArrayList로 짜야함
		
		ArrayList<Member> list = new ArrayList<Member>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// SELECT * FROM MEMBER WHERE USERNAME LIKE '%?%'
		// 위의 sql문을 제시한 후 pstmt.setString(1, "뫄");
		// 이때 완성될 sql문 : SELECT * FROM MEMBER WHERE USERNAME LIKE '%'뫄'%';
		
		// 해결방법 1)
		//String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%' || ? || '%'";
		
		// 해결방법 2)
		String sql = prop.getProperty("selectByUserName");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			// 해결방법 1)의 sql문 일 경우
			//pstmt.setString(1, keyword); // '%뫄%'
			
			// 해결방법 2)의 sql문 일 경우
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
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			
		}
		
		return list; 
		
	}
	
	
	/** 5.
	 * 사용자가 입력한 아이디의 정보 변경 요청 처리해주는 메소드
	 * @param m
	 * @return
	 */
	public int updateMember(Connection conn, Member m) {
		// update문 => 처리된 행수(int) => 트랜젝션 처리
		
		int result = 0;

		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateMember");
		
		try {
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	

	/** 6.
	 * 사용자가 입력한 아이디의 회원 정보를 삭제하는 메소드
	 * @param userId : 사용자가 입력한 아이디
	 * @return result : 처리된 행수
	 */
	public int deleteMember(Connection conn, String userId) {
		// delete문 => 처리된 행수(int) => 트랜젝션 처리
		
		int result = 0;	
		
		PreparedStatement pstmt = null;
		
		// DELETE FROM MEMBER WHERE USERID = ?
		String sql = prop.getProperty("deleteMember");
		
		try {

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			result = pstmt.executeUpdate();


			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result; //사용자에게 (삭제)결과 알려주기
		
	}
	
	
	
	
	
}
