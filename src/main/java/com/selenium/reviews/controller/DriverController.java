package com.selenium.reviews.controller;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * 
 * Clase donde se maneja todas las interaciones con el navegador
 * 
 * @author Usuario
 * @vesion 1.0.0
 */
public class DriverController {
	
	private  WebDriver driver;
	private static String userLogueado = System.getProperty("user.name");
	private static String exePath = "C:\\Users\\"+userLogueado+"\\workspace\\chromedriver.exe";
	
	
	
	/**
	 * Escribe en un input o campo de texto en un elemento del dom
	 * 
	 * 
	 * @param typeElement si 0 = className, si 1 = xpath
	 * 					  si 2 = name, si 3 = id
	 * 					  si 4 = cssSelector
	 * @param name Nombre del elemento en el dom donde se escribir�
	 * @param inputData La data que se escribir� 
	 */
	public void inputWrite(int typeElement,String name, String inputData) throws ElementNotInteractableException{
		WebElement element = null;
		try {
			
			if(typeElement == 0) {
				element = driver.findElement(By.className(name));
			}else if(typeElement == 1) {
				element = driver.findElement(By.xpath(name));
			}else if(typeElement == 2) {
				element = driver.findElement(By.name(name));
			}else if(typeElement == 3) {
				element = driver.findElement(By.id(name));
			}else if(typeElement == 4) {
				element = driver.findElement(By.cssSelector(name));
			}
		
			for(int i = 0; i < inputData.length(); i++) {

				element.sendKeys(inputData.substring(i, i+1));
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (NoSuchElementException e1) {
					System.err.println("El elemento no pudo ser encontrado en el DOM");
				} catch (ElementNotInteractableException e2) {
					System.out.println("No se puede hacer click sobre este elemento");
				}

			}
		}catch(NoSuchElementException e) {
			System.err.println("No se puede escribir en este elemento");
		}
		
		
	}
	


	/**
	 * Escribe en un input o campo de texto en un elemento del dom
	 * 
	 * 
	 * @param typeElement si 0 = className, si 1 = xpath
	 * 					  si 2 = name, si 3 = id
	 * 					  si 4 = cssSelector
	 * @param name Nombre del elemento en el dom donde se escribir�
	 * @param inputData La data que se escribir� 
	 */
	public void inputWriteFile(int typeElement,String name, String inputData) throws NoSuchElementException{
		WebElement element = null;
		try {
			
			if(typeElement == 0) {
				element = driver.findElement(By.className(name));
			}else if(typeElement == 1) {
				element = driver.findElement(By.xpath(name));
			}else if(typeElement == 2) {
				element = driver.findElement(By.name(name));
			}else if(typeElement == 3) {
				element = driver.findElement(By.id(name));
			}else if(typeElement == 4) {
				element = driver.findElement(By.cssSelector(name));
			}
		
			element.sendKeys(inputData);
		}catch(NoSuchElementException e) {
			System.err.println("No se puede escribir en este elemento");
		}
		
		
	}
	
	/**
	 * Hacer click en un boton del dom
	 * 
	 * 
	 * @param typeElement si 0 = className, si 1 = xpath
	 * 					  si 2 = name, si 3 = id
	 * 					  si 4 = cssSelector
	 * 					  si 5 = partialLinkText
	 * @param name Nombre del elemento en el dom donde se har� click
	 */
	public void clickButton(int typeElement, String name, String elemento) throws ElementClickInterceptedException,NoSuchElementException{
		WebElement element = null;

			if(typeElement == 0) {
				element = driver.findElement(By.className(name));
			}else if(typeElement == 1) {
				element = driver.findElement(By.xpath(name));
			}else if(typeElement == 2) {
				element = driver.findElement(By.name(name));
			}else if(typeElement == 3) {
				element = driver.findElement(By.id(name));
			}else if(typeElement == 4) {
				element = driver.findElement(By.cssSelector(name));
			}else if(typeElement == 5) {
				element = driver.findElement(By.partialLinkText(name));
			}
			try {
				element.click();
			}catch (ElementClickInterceptedException e) {
				System.err.println("No se puede hacer click en este elemento "+elemento);
			}

	}
	
	
	
	/**
	 * Hace scroll hac�a abajo en el dom
	 * 
	 * @author Luis Morales
	 * @param value la cantidad de scroll que har� hac�a abajo
	 * @throws InterruptedException
	 */
	public void scrollDown(int value) throws InterruptedException {
		value *= 600;
		((JavascriptExecutor)driver).executeScript("window.scrollTo (document.body.scrollHeight,"+value+")");
		Thread.sleep(3000);
	}
	
	/**
	 * Hace scroll hac�a arribo en el dom
	 * 
	 * @author Luis Morales
	 * @param value la cantidad de scroll que har� hac�a arriba
	 * @throws InterruptedException
	 */
	public void scrollUp(int value) throws InterruptedException {
		value *= -600;
		((JavascriptExecutor)driver).executeScript("window.scrollTo (document.body.scrollHeight,"+value+")");
		Thread.sleep(4000);
	}
	
	/**
	 * Buscar elemento en el DOM de la pagina
	 * 
	 * 
	 * @param typeElement si 0 = className, si 1 = xpath
	 * 					  si 2 = name, si 3 = id
	 * 					  si 4 = cssSelector
	 * @param name Nombre del elemento en el dom que se buscar�
	 */		
	public int searchElement(int typeElement, String name) {
		try {
			if(typeElement == 0) {
				if(driver.findElements(By.className(name)).size() != 0) {
					return 1;
				};
				
			}else if(typeElement == 1) {
				if(driver.findElements(By.xpath(name)).size() != 0) {
					return 1;
				}
			}else if(typeElement == 2) {
				if(driver.findElements(By.name(name)).size() != 0) {
					return 1;
				};
			}else if(typeElement == 3) {
				if(driver.findElements(By.id(name)).size() != 0) {
					return 1;
				};
				
			}else if(typeElement == 4) {
				if(driver.findElements(By.cssSelector(name)).size() != 0) {
					return 1;	
				}
			}
		}catch(Exception e) {
			driver.quit();
		}
		
		return 0;
	}
	
	/**
	 * Ir a la pagina 
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 * @param page Pagina a la que se ingresar� 
	 * @throws NullPointerException
	 */
	public void goPage(String page) {
		try {
			driver.get(page);
		}catch(NullPointerException e) {
			System.out.println("error");
			driver.quit();
		}
	}
	
	/**
	 * Darle todas las opciones al chrome, direcci�n donde se inicia el chrome driver
	 * agregarle las opciones de iniciar maximizado y modo incognito
	 * 
	 * @author Luis Morales
	 * @version 1.0.0
	 */
	public void optionsChrome() {
		
		//Ruta donde esta el archivo chromdrive	
		System.setProperty("webdriver.chrome.driver", exePath);
		
		//Cambiar opciones del Chrome
		ChromeOptions options = new ChromeOptions();
		//Poner en modo incognito
		options.addArguments("start-maximized");
		options.addArguments("--js-flags=--expose-gc");
		options.addArguments("--enable-precise-memory-info");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-default-apps");
		options.addArguments("disable-infobars");
		options.addArguments("incognito");
		
		driver = new ChromeDriver(options); //Instancia de Chroma Driver
	}
	
	public int getQuantityElements(int typeElement, String name) {
		try {
			if(typeElement == 0) {
				return driver.findElements(By.className(name)).size();
					
			}else if(typeElement == 1) {
				return driver.findElements(By.xpath(name)).size();
				
			}else if(typeElement == 2) {
				return driver.findElements(By.name(name)).size();
					
			}else if(typeElement == 3) {
				return driver.findElements(By.id(name)).size();
				
			}else if(typeElement == 4) {
				return driver.findElements(By.cssSelector(name)).size();
			}
		}catch(Exception e) {
			driver.quit();
		}
		
		return 0;
	}
	public void refresh() {
		driver.navigate().refresh();
	}
	
	public void close() {
		driver.close();
	}
	
	public void quit() {
		driver.quit();
	}
	
	public void back() {
		driver.navigate().back();
	}
}
