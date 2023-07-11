package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*;  // 이렇게 하면 JDBCTemplate에 있는 모든 스테틱 메소드 모두 호출 가능
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {
	
	public int insertMember(Member m) {
		
		// 1) jdbc driver 등록
		// 2) Connection 객체 생성
		Connection conn = /*JDBCTemplate.*/getConnection();
		int result = new MemberDao().insertMember(conn,m);	// pstmt 만들려면 conn 필요함
		
		// 6) 트랜젝션 처리
		if(result > 0) {
			/*JDBCTemplate.*/commit(conn);
		}else {
			/*JDBCTemplate.*/rollback(conn);
		}
		
		// 7) 다 쓴 자원 반납처리
		/*JDBCTemplate.*/close(conn);
		
		// 8) 결과 반환
		return result;
		
	}
	
	
	public ArrayList<Member> selectList() {
		
		Connection conn = getConnection();
		ArrayList<Member> list = new MemberDao().selectList(conn);
		
		close(conn);
		
		return list;
		
	}
	
	
}
