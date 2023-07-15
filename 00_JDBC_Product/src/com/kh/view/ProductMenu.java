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
			case 1: pc.selectList(); break;
			case 2: inputProduct(); break;
			case 3: updateProduct(); break;
			case 4: deleteProduct(); break;
			case 5: searchProduct(); break;
			case 0: System.out.println("이용해주셔서 감사합니다."); return;
			default : System.out.println("메뉴를 잘못입력하셨습니다. 다시 입력해주세요.");

			}
			
		}

	}
	
	public String insertId() {
		System.out.print("상품 아이디 : ");
		String pId = sc.nextLine();
		return pId;
	}
	
	
	/** 2.
	 * 상품 추가 하기 
	 */
	public void inputProduct() {
		
		System.out.println("\n==== 상품 추가 하기 ====");
		String pId = insertId();
		// 상품명, 상품가격, 상품상세정보, 재고
		System.out.print("상품명 : ");
		String pName = sc.nextLine();
		System.out.print("상품가격 : ");
		String price = sc.nextLine();
		System.out.print("상품 상세 정보 : ");
		String description = sc.nextLine();
		System.out.print("재고 : ");
		String stock = sc.nextLine();
		
		pc.insertProduct(pId, pName, price, description, stock);
		
	}
	
	
	/** 3.
	 * 상품 수정 하기
	 */
	public void updateProduct() {
		
		System.out.println("\n===== 상품 수정 하기 =====");
		System.out.print("상품 아이디 : ");
		String pId = sc.nextLine();
		System.out.print("변경할 상품명 : ");
		String pName = sc.nextLine();
		System.out.print("변경할 상품가격 : ");
		String price = sc.nextLine();
		System.out.print("변경할 상품 상세 정보 : ");
		String description = sc.nextLine();
		System.out.print("변경할 재고 : ");
		String stock = sc.nextLine();
		
		pc.updateProduct(pId, pName, price, description, stock);
		
	}
	
	/** 4.
	 * 상품 삭제 하기
	 */
	public void deleteProduct() {
		
		System.out.println("\n===== 상품 삭제 하기 =====");
		System.out.print("상품 아이디 : ");
		String pId = sc.nextLine();
		
		pc.deleteProduct(pId);
		
	}
	
	/** 5.
	 * 상품 검색 하기 (상품Id 키워드로)
	 */
	public void searchProduct() {
		
		System.out.println("\n===== 키워드로 상품 검색 =====");
		System.out.print("검색할 상품 ID (키워드) : ");
		String keyword = sc.nextLine();
		
		pc.searchProduct(keyword);
		
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
		public void displayProductList(ArrayList<Product> list) {
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
		public void displayProduct(Product p) {
			System.out.println("\n 조회된 데이터는 다음과 같습니다.");
			System.out.println(p);
		}

	
	
}
