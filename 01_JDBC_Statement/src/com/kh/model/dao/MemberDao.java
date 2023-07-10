package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

// ***** 가장 중요!! *****
// DAO(Data Access Object) : DB에 직접적으로 접근해서 사용자의 요청에 맞는 SQL문 실행 후 결과 받기 (JDBC작업)
//							 결과를 Controller 로 다시 리턴
public class MemberDao {
	/*
	 * * JDBC용 객체
	 * - Connection				: DB의 연결정보를 담고 있는 객체
	 * - [Prepared]Statement	: 연결된 DB에 SQL문 전달해서 실행하고 그 결과를 받아내는 객체 ***
	 * - ResultSet				: SELECT문 실행 후 조회된 결과물들이 담겨있는 객체
	 * 
	 * * JDBC 과정 (순서 중요!)
	 * 1) jdbc driver 등록
	 * 2) Connection 객체 생성
	 * 3) Statement 객체 생성
	 * 4) sql문 전달하면서 실행
	 * 5) 결과 받기
	 * 		> select 문	=> ResultSet 객체 (조회된 데이터들이 담겨 있음)	=> 6_1
	 * 		> dml문		=> int (처리된 행 수)						=> 6_2
	 * 
	 * 6_1) ResultSet에 담겨있는 데이터들을 하나씩 vo 객체에 주섬주섬 옮겨 담기
	 * 6_2) 트랜젝션 처리
	 * 
	 * 7) 다 사용한 JDBC 객체를 반드시 자원 반납! 안하면 디비 락 걸림!! (close) => 생성된 역순으로
	 */
	
	/** 1.
	 * 사용자가 입력한 정보들을 추가시켜주는 메소드
	 * @param m		: 사용자가 입력한 값들이 주섬주섬 담겨있는 Member 객체
	 */
	public int insultMember(Member m) {//(12:16)
		// insert문 => 처리된 행수(int)	=> 트랜젝션 처리
		
		// 필요한 변수들 먼저 셋팅
		int result = 0;			// 처리된 결과 (처리된 행수)를 받아 줄 변수
		Connection conn = null;	// 연결된 db의 연결정보를 담는 객체
		Statement stmt = null;	// '완성된 sql(실제 값이 다 채워진 상태로)'문 전달해서 곧바로 실행 후 결과를 받는 객체
		
		// 실행할 sql문 (완성된 형태로 만들어 두기!!)
		// INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, 'XXX', 'XXX', 'XXX', 'X', XX, 'XXX', 'XX', 'XX', 'XX', SYSDATE)
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL, "
										 + "'" + m.getUserId() + "', "
										 + "'" + m.getUserPwd() + "', "
										 + "'" + m.getUserName() + "', "
										 + "'" + m.getGender() + "', "
										 	   + m.getAge() + ", "
										 + "'" + m.getEmail() + "', "
										 + "'" + m.getPhone() + "', "
										 + "'" + m.getAddress() + "', "
										 + "'" + m.getHobby() + "', SYSDATE)";
		
		// System.out.println(sql);	// 콘솔에 찍어서 쿼리문 맞는지 오라클가서 확인!!
		
		try {
			// 1) jdbc driver 등록
			//    (ojdbc6.jar 는 이미 등록해뒀으니까 여기서는 패스~)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 객체 생성 == db에 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 객체 생성
			stmt = conn.createStatement();
			
			// 4,5) sql문 전달하면서 실행 후 결과 받기
			result = stmt.executeUpdate(sql);	//(12:41)
			
			// 6) 트랜젝션 처리
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
			// 7) 다 쓴 jdbc용 객체 반납
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result; // 올라가서 void가 아닌 int 형으로 수정!
		
	}
	
	
	/** 2.
	 * 사용자가 요청한 회원 전체 조회를 처리해주는 메소드
	 * @return 조회된 결과가 있었으면 조회된 결과들이 담겨있는 list | 조회된 결과가 없으면 텅 빈 list 가 리턴됨
	 */
	public ArrayList<Member> selectList() {
		// select문(여러행 조회) => ResultSet객체 => ArrayList에 차곡차곡 담기 (여러 행이 나올것이기 때문에)
	
		// 필요한 변수들 셋팅
		ArrayList<Member> list = new ArrayList<Member>();	// [] 현재 상태는 텅 비어있는 상태
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;	// select문 실행시 조회된 결과값들이 최초로 담기는 객체
		
		// 실행할 SQL문
		String sql = "SELECT * FROM MEMBER"; // 오라클에서 써서 확인해보고 데려오기
		
		try {
			// 1) jdbc driver 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2) Connection 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			// 3) Statement 생성
			stmt = conn.createStatement();
			
			// 4,5) sql문 실행 및 결과 받기
			rset = stmt.executeQuery(sql);	// 이제 전체조회표가 rset에 담김
			
			// 6) ResultSet으로부터 데이터 하나씩 뽑아서 vo객체에 주섬주섬 담고 + list에 vo객체 추가
			
			while(rset.next()) {
				
				// 현재 rset의 커서가 가리키고 있는 한 행의 데이터들을 싹 다 뽑아서 Member 객체에 주섬주섬 담기
				Member m = new Member();	// 기본생성자로 Member 만들기(따라서 현재 null, null, ..)
				
				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLLDATE"));
				// 현재 참조하고 있는 행에 대한 모든 컬럼에 대한 데이터들을 한 Member m 객체에 담기
				
				list.add(m);	// 리스트에 해당 회원 객체 차곡차곡 담기 (2:40)
				
			}
			
			// 반복문이 다 끝난 시점에
			// 만약에 조회된 데이터가 없었다면 list가 텅 빈 상태일 것임
			// 만약에 조회된 데이터가 있었다면 list에 뭐라도 담겨 있을 것임
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;	// 텅 빈 리스트 | 뭐라도 담겨있는 리스트 // 올라가서 반환형 맞춰주기
	}
	
	
	/** 3.
	 * 사용자의 아이디로 회원검색 요청 처리해주는 메소드
	 * @param userId	: 사용자가 입력한 검색하고자 하는 회원 아이디값
	 * @return			: 검색된 결과가 있으면 생성된 Member객체 | 결과가 없으면 null
	 */
	public Member selectByUserId(String userId) {
		// select문 (한 행) => ResultSet 객체
		// 그럼 굳이 ArrayList 필요 없음! 멤버 객체 한 개만 있으면 될 듯
		
		// 필요한 변수들 셋팅
		Member m = null; // 조회 결과가 있을 수도 있고, 없을 수도 있으니까
		
		// JDBC 객체
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = '" + userId + "'"; // 앞에서 받아온 ID 가 담겨있을것임
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(sql);
			
			if(rset.next()) { // 한 행이라도 조회됐을 때
				// 조회됐다면 해당 조회 된 컬럼값들을 쏙쏙 뽑아서 한 Member 객체의 각 필드에 담기
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
							   rset.getDate("enrolldate"));
			}
			
			// 위의 조건문 다 끝난 시점에
			// 만약에 조회된 데이터가 없었을 경우 => m은 null
			// 만약에 조회된 데이터가 있었을 경우 => m은 생성 후 뭐라도 담겨 있음
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return m; // 반환형 Member
		
	}

	
	/** 6.
	 * @param m
	 * @return
	 */
	public int deleteMember(Member m) {
		// insert문 => 처리된 행수(int)	=> 트랜젝션 처리
		
		// 필요한 변수들 먼저 셋팅
		int result = 0;			
		Connection conn = null;	
		Statement stmt = null;
		
		// 실행할 sql문 (완성된 형태로 만들어 두기!!)
		// DELETE FROM MEMBER WHERE USERID = USERID AND USERPWD = USERPWD AND USERNAME = USERNAME;
		String sql = "DELETE FROM MEMBER WHERE USERID = " + m.getUserId();
		
		
		// System.out.println(sql);	// 콘솔에 찍어서 쿼리문 맞는지 오라클가서 확인!!
		
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "JDBC", "JDBC");
			
			stmt = conn.createStatement();
			
			result = stmt.executeUpdate(sql);

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
			// 7) 다 쓴 jdbc용 객체 반납
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	
	
	
	
}
