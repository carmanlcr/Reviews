package com.selenium.reviews.controller;

import javax.swing.JOptionPane;

import org.openqa.selenium.ElementNotInteractableException;

public class IniciaSesion {
	private String username;
	private String password;
	private DriverController dr;
	
	
	
	public IniciaSesion(DriverController dr,String username, String password) {
		this.dr = dr;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Iniciar sesiÃ³n en twitter, ingresando el usuario y la contraseÃ±a y presionando el boton
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 * @throws InterruptedException
	 */
	public boolean init() throws InterruptedException {
		
		Thread.sleep(1250);
		if(dr.searchElement(3, "gb_70") != 0) {
			dr.clickButton(3, "gb_70","Inicio de sesion");
		}else {
			dr.clickButton(1, "/html/body/div/div[3]/div/div/div/div[2]/div[2]/div/a", "Inicio de sesion xpath");
		}
		while(dr.searchElement(3, "identifierId") == 0);
		Thread.sleep(1251);
		//Insertar el usuario
		dr.inputWrite(3,"identifierId",username);
		//Darle click a siguiente
		dr.clickButton(3, "identifierNext","Boton de siguiente");
		Thread.sleep(1250);
		if(dr.searchElement(1, "//div[text()[contains(.,'No se ha podido encontrar tu cuenta de Google')]]") != 0){
			return false;
		}else if(dr.searchElement(1, "/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div/div[2]/div[2]/div/span/svg") != 0) {
			return false;
		}else {
			while(dr.searchElement(2, "password") == 0);
			//Insertar el password
			Thread.sleep(1250);
			try{
				try {
					dr.clickButton(1, "//input[@type='password']", "contraseña input");
					dr.inputWrite(1,"//input[@type='password']",password);
				}catch(ElementNotInteractableException e1) {

					dr.clickButton(1, "/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div[1]/div/div/div/div/div[1]/div/div[1]/input", "Contraseña xpath");
					dr.inputWrite(1,"/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div[1]/div/div/div/div/div[1]/div/div[1]/input",password);
				}
			}catch(ElementNotInteractableException e) {
				dr.clickButton(2, "password", "Contraseña name");
				dr.inputWrite(2,"password",password);
			}
			
			//Presionar el boton de sesion
			dr.clickButton(3, "passwordNext","passwordNext inicio de sesion");
			if(dr.searchElement(1, "/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div[2]/div[2]") != 0) {
				return false;
			}
			while(dr.searchElement(3, "ca") != 0) {
				int inputCat = JOptionPane.showConfirmDialog(null, "Debe ingresar el captcha... DARLE SI SOLO CUANDO SE ESCRIBA");
				if(inputCat == 0) {
					try{
						try {
							dr.clickButton(1, "//input[@type='password']", "contraseña input");
							dr.inputWrite(1,"//input[@type='password']",password);
						}catch(ElementNotInteractableException e1) {

							dr.clickButton(1, "/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div[1]/div/div/div/div/div[1]/div/div[1]/input", "Contraseña xpath");
							dr.inputWrite(1,"/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div[1]/div/div/div/div/div[1]/div/div[1]/input",password);
						}
					}catch(ElementNotInteractableException e) {
						dr.clickButton(2, "password", "Contraseña name");
						dr.inputWrite(2,"password",password);
					}
					dr.clickButton(3, "passwordNext","passwordNext inicio de sesion");
					
					Thread.sleep(2500);
					if(dr.searchElement(1, "/html/body/div[1]/div[1]/div[2]/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div[2]/div[2]") != 0) {
						return false;
					}
				}
			}
			
			return true;
		}
		
			
		
		

		
	}

}
