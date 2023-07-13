package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.ProductController;
import com.kh.model.vo.Product;

public class ProductMenu {

	private Scanner sc = new Scanner(System.in);
	
	private ProductController pc = new ProductController();
	
	
	/**
	 * 메인화면
	 */
	public void mainMenu() {
		
		while(true) {
			
			System.out.println("\n== 상품 관리 프로그램 ==");
			System.out.println("1. 전체 조회 하기");
			System.out.println("2. 상품 추가 하기");
			System.out.println("3. 상품 수정 하기 (상품id로 조회하고 수정)");
			System.out.println("4. 상품 삭제 하기 (상품id로 조회해서 삭제)");
			System.out.println("5. 상품 검색 하기 (상품 이름으로 키워드 검색)");
			System.out.println("0. 프로그램 종료하기");
			
			System.out.println(" >> 메뉴 선택 : ");
			int menu = sc.nextInt();
			
			sc.nextLine();
			
			switch(menu) {
			case 1: break;
			case 2: inputProduct(); break;
			case 3: break;
			case 4: break;
			case 5: break;
			case 0: System.out.println("이용해주셔서 감사합니다."); return;
			default : System.out.println("메뉴를 잘못입력하셨습니다. 다시 입력해주세요.");

			}
			
		}

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
		public void displayMemberList(ArrayList<Product> list) {
			System.out.println("\n 조회된 데이터는 다음과 같습니다.");
		
			// 단순 for문
			/*
			for(int i=0; i<list.size(); i++) {
				System.out.println(list.get(i));
			}
			*/
			
			// 향상된 for문
			for(Product p : list) {
				System.out.println(p);
			}
		}
		
		/**
		 * 조회 서비스 요청시 조회결과가 '한 행'일 경우 사용자가 보게 될 응답화면
		 * @param m
		 */
		public void displayMember(Product p) {
			System.out.println("\n 조회된 데이터는 다음과 같습니다.");
			System.out.println(m);
		}

	
	
}
