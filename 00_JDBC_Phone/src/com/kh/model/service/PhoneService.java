package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import static com.kh.common.JDBCTemplate.*; // 이제 편해요~

import com.kh.model.dao.MemberDao;
import com.kh.model.dao.PhoneDao;
import com.kh.model.vo.Member;
import com.kh.model.vo.Phone;

public class PhoneService {

	/** 1.
	 * 전화번호 등록
	 * @param p
	 * @return
	 */
	public int insertPhone(Phone p) {
		
		// 1) jdbc driver 등록
		// 2) Connection 객체 생성
		Connection conn = /*JDBCTemplate.*/getConnection();
		int result = new PhoneDao().insertPhone(conn,p);
		
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
	 *  전화번호 모두 보기
	 * @return
	 */
	public ArrayList<Phone> selectList() {
		
		Connection conn = getConnection();
		ArrayList<Phone> list = new PhoneDao().selectList(conn);
		
		close(conn);
		
		return list;
	
	
	
}
