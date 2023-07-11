package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : view를 통해서 사용자가 요청한 기능에 대해서 처리(추가, 조회 등)하는 담당
//				해당 메소드로 전달된 데이터 [가공처리 한 후] Dao로 전달하면서 호출
//				Dao로부터 반환받은 결과에 따라 성공인지, 실패인지 판단 후 응답화면 결정 (view 메소드 호출)
public class MemberController {

	//private MemberMenu mm = new MemberMenu();
	//이렇게 하면 stack overflow 발생해서 안됨!!
	
	/** 1.
	 * 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId ~ hobby : 사용자가 입력했던 정보들이 담겨있는 매개변수
	 */
	public void insertMember(String userId, String userPwd, String userName, String gender, String age, String email, String phone, String address, String hobby) {
		
		// 매개변수 생성자로..(9개) / int는 형변환!!
		// 가공하고, 담아서, dao호출해
		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		int result = new MemberDao().insertMember(m);
	
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원추가 되었습니다.");
		}else {
			new MemberMenu().displayFail("회원추가 실패했습니다");
		}
	}
	
	
	/** 2.
	 * 사용자의 회원 전체 조회 요청을 처리해주는 메소드
	 */
	public void selectList() {
		
		ArrayList<Member> list = new MemberDao().selectList();
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData("조회된 결과가 없습니다.");
		}else {
			new MemberMenu().displayMemberList(list); //list보내면 list 출력잘될것임
		}
		
	}
	
	
	/** 3.
	 * 사용자의 아이디로 회원 검색 요청을 처리해주는 메소드
	 * @param userId : 사용자가 입력한 검색하고자 하는 회원 아이디값
	 */
	public void selectByUserId(String userId) {
		Member m = new MemberDao().selectByUserId(userId);
		
		if(m == null) {
			new MemberMenu().displayNoData(userId + "에 해당하는 검색결과가 없습니다");
		}else {
			new MemberMenu().displayMember(m); //m보내면 m출력잘될것임
		}
	}
	
	
	
	/** 4.
	 * 이름으로 키워드 검색 요청시 처리해주는 메소드
	 * @param keyword
	 */
	public void selectByUserName(String keyword) {
		ArrayList<Member> list = new MemberDao().selectByUserName(keyword);
		
		if(list.isEmpty()) {
			new MemberMenu().displayNoData(keyword);
		}else {
			new MemberMenu().displayMemberList(list);
		}
		
	}
	
	
	
	/** 5.
	 * 정보 변경 요청 처리해주는 메소드
	 * @param userId	: 변경하고자 하는 회원 아이디
	 * @param userPwd	: 변경할 비밀번호
	 * @param email		: 변경할 이메일
	 * @param phone		: 변경할 전화번호
	 * @param address	: 변경할 주소
	 */
	public void updateMember(String userId, String userPwd, String email, String phone, String address) {
		
		Member m = new Member();	// 기본생성자로(11:20)
		
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		m.setEmail(email);
		m.setPhone(phone);
		m.setAddress(address);
		
		int result = new MemberDao().updateMember(m); //5개 들고 dao로~~
		
		if(result > 0) {
			new MemberMenu().displaySuccess("성공적으로 회원 정보가 변경되었습니다.");
		}else {
			new MemberMenu().displayFail("회원 정보 변경에 실패했습니다.");
		}
	}
	
	
	/** 6.
	 * 회원 탈퇴 요청 처리해주는 메소드
	 * @param userId2 : 사용자가 입력한 탈퇴시키고자 하는 회원 아이디값
	 */
	public void deleteMember(String userId2) { //userId도 가능 타입만 맞으면 됨
		
		int result = new MemberDao().deleteMember(userId2);
		
		if(result > 0) { // 성공
			new MemberMenu().displaySuccess("회원 탈퇴 완료되었습니다.");
		}else { // 실패
			new MemberMenu().displayFail("회원 탈퇴 실패했습니다..");
		}
		
	}

	
	
	
	
	
	
	
	
	
	
}
