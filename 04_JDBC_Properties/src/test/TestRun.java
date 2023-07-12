package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class TestRun {

	public static void main(String[] args) {
	
		// Properties 복습
		/*
		 * * Properties 특징
		 * - Map 계열 컬렉션 (key + value 세트로 담는 특징)
		 * - key, value 모두 String(문자열)으로 담기
		 * 	 setProperty(String key, String value)
		 * 	 getProperty(String key) : String value
		 * 
		 * 	 -  주로 외부 파일(.properties / .xml)로 입출력 할 때 사용 => 환경설정 파일 같은거.. 개발자 아닌 사람이 보는거..
		 * 
		 */
		
		
		// << 출력 >>
		/* 
		Properties prop = new Properties();
		prop.setProperty("C", "INSERT");
		prop.setProperty("R", "SELECT");
		prop.setProperty("U", "UPDATE");
		prop.setProperty("D", "DELETE");
		
		// 순서 유지하지 않고 막 담김
		
		try {
			prop.store(new FileOutputStream("resources/test.properties"), "properties Test");
			prop.storeToXML(new FileOutputStream("resources/test.xml"), "properties Test");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		
		// << 입력 >>
		Properties prop = new Properties();	// 텅 비어있는 상태
		
		try {
			prop.load(new FileInputStream("resources/driver.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(prop.getProperty("driver"));
		System.out.println(prop.getProperty("url"));
		System.out.println(prop.getProperty("username"));
		System.out.println(prop.getProperty("password"));
		System.out.println(prop.getProperty("candy"));
		
		
	}

}
