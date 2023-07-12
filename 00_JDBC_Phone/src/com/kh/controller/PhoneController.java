package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.PhoneService;
import com.kh.model.vo.Phone;
import com.kh.view.PhoneMenu;


public class PhoneController {

	/** 1.
	 * 전화번호 등록
	 * @param userName
	 * @param age
	 * @param address
	 * @param phoneNum
	 */
	public void insertPhone(String userName, String age, String address, String phoneNum) {
		
		Phone p = new Phone(userName, Integer.parseInt(age), address, phoneNum);
		
		int result = new PhoneService().insertPhone(p);
		
		if(result > 0) {
			new PhoneMenu().displaySuccess("성공적으로 회원추가 되었습니다.");
		}else {
			new PhoneMenu().displayFail("회원추가 실패했습니다");
		}
		
	}
	
	
	/** 3.
	 * 전화번호 모두 보기
	 * 
	 */
	public void selectList() {
		ArrayList<Phone> list = new PhoneService().selectList();
		
		if(list.isEmpty()) {
			new PhoneMenu().displayNoData("조회된 결과가 없습니다.");
		}else {
			new PhoneMenu().displayMemberList(list); //list보내면 list 출력잘될것임
		}
	}
	
	
}
