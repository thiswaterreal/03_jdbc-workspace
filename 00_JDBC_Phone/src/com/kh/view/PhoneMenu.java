package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.PhoneController;
import com.kh.model.vo.Phone;

public class PhoneMenu {

	private Scanner sc = new Scanner(System.in);
	
	private PhoneController pc = new PhoneController();
	
	
	
	/**
	 * 메인화면
	 */
	public void mainMenu() {
		
		while(true) {
			
			System.out.println("\n== 전화번호부 v1.0 ==");
			System.out.println("1. 전화번호 등록");
			System.out.println("2. 전화번호 검색");
			System.out.println("3. 전화번호 모두 보기");
			System.out.println("4. 종료");
			
			System.out.print("메뉴 >> ");
			int menu = sc.nextInt();
			
			sc.nextLine();
			
			switch(menu) {
			case 1: inputPhone(); break;
			case 2: pc.selectList();break;
			case 3: break;
			case 4: System.out.println("종료하겠습니다. 감사합니다."); return;
			default : System.out.println("메뉴를 잘못입력하셨습니다. 다시 입력해주세요.");
			}	
		}
		
	}
	
	/** 1.
	 * 전화번호 등록 창
	 */
	public void inputPhone() {
		System.out.println("\n== 전화번호 등록 ==");
		
		// 이름, 나이, 주소, 전화번호 입력 받기
		System.out.print("이름 : ");
		String userName = sc.nextLine();
		
		System.out.print("나이 : ");
		String age = sc.nextLine();		// 웹처럼 나이 String으로 받음
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		System.out.print("전화번호 : ");
		String phoneNum = sc.nextLine();
		
		pc.insertPhone(userName, age, address, phoneNum);
	}
	
	
	
	

	//---------------------------------------- 응답화면 ------------------------------------------
	
	/**
	 * 서비스 요청 처리 후 '성공' 했을 경우 사용자가 보게 될 응답화면
	 * @param message 성공메시지
	 */
	public void displaySuccess(String message) {
		System.out.println("\n 서비스 요청 성공 : " + message);
	}
	
	/**
	 * 서비스 요청 처리 후 '실패' 했을 경우 사용자가 보게 될 응답화면
	 * @param message 실패메시지
	 */
	public void displayFail(String message) {
		System.out.println("\n 서비스 요청 실패 : " + message);
	}
	
	
	/**
	 * 조회 서비스 요청시 조회결과가 '없을' 경우 사용자가 보게 될 응답화면
	 * @param message
	 */
	public void displayNoData(String message) {
		System.out.println(message + "\n");
	}
	
	/**
	 * 조회 서비스 요청시 조회결과가 '여러 행'일 경우 사용자가 보게 될 응답화면
	 * @param list
	 */
	public void displayMemberList(ArrayList<Phone> list) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
	
		// 단순 for문
		/*
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
		*/
		
		// 향상된 for문
		for(Phone p : list) {
			System.out.println(p);
		}
	}
	
	/**
	 * 조회 서비스 요청시 조회결과가 '한 행'일 경우 사용자가 보게 될 응답화면
	 * @param p
	 */
	public void displayMember(Phone p) {
		System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		System.out.println(p);
	}
	
	
	
}
