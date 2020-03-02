package com.selenium.reviews.controller;


import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import Controller.RobotController;

public class IniciaSesion {
	
	private final String PATH_IMAGE_SIKULI ="C:\\ImagenesSikuli\\";
	private String username;
	private String password;
	private Screen s;
	private RobotController robot;
	
	
	
	public IniciaSesion(Screen s,RobotController robot, String username, String password) {
		this.s = s;
		this.robot = robot;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Iniciar sesión en twitter, ingresando el usuario y la contraseña y presionando el boton
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 * @throws InterruptedException
	 * @throws FindFailed 
	 */
	public boolean init() throws InterruptedException, FindFailed {
		
		Thread.sleep(1250);
		
		try{
			s.click(new Pattern(PATH_IMAGE_SIKULI+"iniciar_sesion-Google.png").exact());
		}catch (IllegalThreadStateException e) {
			// TODO: handle exception
		}
		
		
		Thread.sleep(5000);
		
		robot.inputWrite(username);
		Thread.sleep(850);
		robot.enter();
		Thread.sleep(5000);

		
		if(s.exists(PATH_IMAGE_SIKULI+"error_init_session-Google.png") != null) {
			return false;
		}else {
			robot.inputWrite(password);
			robot.enter();
			Thread.sleep(5000);
			if(s.exists(PATH_IMAGE_SIKULI+"error_init_session2-Google.png") != null){
				System.out.println("La contrase�a es incorrecta");
				return false;
			}
			
			if(s.exists(PATH_IMAGE_SIKULI+"error_init_session3-Google.png") != null) {
				System.out.println("Pide verificacion por telefono");
				return false;
			}
			
			if(s.exists(PATH_IMAGE_SIKULI+"error_init_session4-Google.png") != null) {
				System.out.println("Error en deteccion");
				return false;
			}
			
			if(s.exists(PATH_IMAGE_SIKULI+"google-com.png") != null) {
				System.out.println("El usuario ingreso");
				return true;
			}
		}
		
		
		
		
		return true;
		
			
		
		

		
	}

}
