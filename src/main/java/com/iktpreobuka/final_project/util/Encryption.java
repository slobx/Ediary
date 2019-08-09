package com.iktpreobuka.final_project.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class Encryption {

	
public static String getPassEncoded(String pass) { 
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(); 
		return bCryptPasswordEncoder.encode(pass); 
		}
	
	
	public static void main(String[] args) { 
		System.out.println("admin: " + getPassEncoded("admin")); 
		System.out.println("ivan: " + getPassEncoded("ivan"));
		System.out.println("milan: " + getPassEncoded("milan"));
		System.out.println("marko: " + getPassEncoded("marko"));
		System.out.println("zoran: " + getPassEncoded("zoran"));
		System.out.println("ivana: " + getPassEncoded("ivana"));
		
	
	}
}
