package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

// Controller : view를 통해서 사용자가 요청한 기능에 대해서 처리(추가, 조회 등)하는 담당
//				해당 메소드로 전달된 데이터 [가공처리 한 후] Dao로 전달하면서 호출
//				Dao로부터 반환받은 결과에 따라 성공인지, 실패인지 판단 후 응답화면 결정 (view 메소드 호출)
public class MemberController {

	
	/** 1.
	 * 사용자의 회원 추가 요청을 처리해주는 메소드
	 * @param userId ~ hobby : 사용자가 입력했던 정보들이 담겨있는 매개변수
	 */
	public void insertMember(String userId, String userPwd, String userName, String gender, String age, String email, String phone, String address, String hobby) {
		
		// 받은 값들을 데이터를 직접적으로 처리해주는 DAO로 넘기기!
		// => 어딘가에 주섬주섬 담아서 전달!
		// 어딘가? => VO객체 == Member 객체!
		
		// 담는 방법 1) 기본생성자로 생성한 후 각 필드에 setter 메소드 통해서 일일히 담는 방법 => 이건 매개변수가 몇개 없을 때..
		// 담는 방법 2) 아싸리 매개변수 생성자를 통해서 생성과 동시에 담는 방법
		
		// 방법2..
		Member m = new Member(userId, userPwd, userName, gender, Integer.parseInt(age), email, phone, address, hobby);
		// 여기서 나이를 반드시!! int 형으로 바꿔야함 (가공처리)
		// (age 입력받을땐 String 으로 받아놓고)age를 int 형으로 만들었기 때문에
		// String => int 로 변경하는 방법? parseInt();
		
		// new 부터 빨간줄 뜨는 경우 => 100% 그 데이터 타입을 받는 매개변수 생성자가 없다는 것
		// System.out.println(m);
		
		// m에 담은 값들을 Dao로 전달
		int result = new MemberDao().insertMember(m);
		//		담아 MemberDao의 insultMember를 호출(m을 들고 == 전달값:m)
		
		// System.out.println(result); 확인용. 이대로 돌려서 입력하면 오라클에 회원정보추가되어있음
		
		// 팝업창 뜨는 것처럼 사용자에게 문구 보여주기
		if(result > 0) { // 성공
			new MemberMenu().displaySuccess("성공적으로 회원 추가 되었습니다.");
			// MemberMenu의 displaySuccess 메소드를 호출
		}else { // 실패
			new MemberMenu().displayFail("회원 추가 실패했습니다..");
		}
		
	}
	
	
	/** 2. (2:12)
	 * 사용자의 회원 전체 조회 요청을 처리해주는 메소드
	 */
	public void selectList() {
		ArrayList<Member> list = new MemberDao().selectList(); // Dao 호출
		//System.out.println(list);
		
		// 조회결과가 있는지 없는지 판단 한 후 사용자가 보게될 응답화면 지정
		if(list.isEmpty()) { // 텅 비어있을 경우 == 조회된 데이터 없었을 경우
			new MemberMenu().displayNoData("전체 조회결과가 없습니다."); //(3:09)
		}else { // 뭐라도 조회된 데이터가 있을 경우
			new MemberMenu().displayMemberList(list); // 여러행
		}
		
		
	}
	
	
	/** 3. 
	 * @param userId
	 */
	public void selectByUserId(String userId) {
		Member m = new MemberDao().selectByUserId(userId); // Dao 호출 (3:30) => Member m 에 담아 (3:50)
		//System.out.println(m);
		
		if(m == null) { // 검색 결과가 없을 경우  (조회된 데이터 없음)
			new MemberMenu().displayNoData(userId + "에 해당하는 검색결과가 없습니다.");
		}else {
			new MemberMenu().displayMember(m);
		}
	}
	
	
	/** 4.
	 * 이름으로 키워드 검색 요청시 처리해주는 메소드
	 * @param keyword
	 */
	public void selectByUserName(String keyword) {
		ArrayList<Member> list = new MemberDao().selectByUserName(keyword);
		// 이제 결과를 list에 담아
		
		if(list.isEmpty()) { // 텅 빈 리스트일 경우 == 검색결과 없음
			new MemberMenu().displayNoData(keyword + "에 해당하는 검색 결과가 없습니다.");
		}else { // 그게 아닐 경우 => 검색결과 있음
			new MemberMenu().displayMemberList(list); // 여러행
		}
	}
	
	/** 9.
	 * 이름으로 풀네임 검색 요청시 처리해주는 메소드
	 * @param keyword
	 */
	public void selectByUserNameAll(String userName) {
		ArrayList<Member> list = new MemberDao().selectByUserName(userName);
		// 이제 결과를 list에 담아
		
		if(list.isEmpty()) { // 텅 빈 리스트일 경우 == 검색결과 없음
			new MemberMenu().displayNoData(userName + "에 해당하는 검색 결과가 없습니다.");
		}else { // 그게 아닐 경우 => 검색결과 있음
			new MemberMenu().displayMemberList(list); // 여러행
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
