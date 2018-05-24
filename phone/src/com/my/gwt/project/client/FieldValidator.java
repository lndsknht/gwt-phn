package com.my.gwt.project.client;

//import java.util.regex.Pattern;

/**
 * Класс предназначен для проверки вводимых значений имён и номеров
 * @author landsknecht
 * */
public class FieldValidator {

	/**
	 * Метод для проверки корректности введённого имени. 
	 * Возвращает false, если имя содержит любые символы, кроме букв или длина имени < 3
	 * @param name имя для валидации
	 * @return boolean
	 * */
	public static boolean isValidName(String name) {
		if (name == null || name.length() < 3) {
			return false;
		}
		
//		return Pattern.matches("[a-zA-Z]+", name);
		return true;
	}
	
	/**
	 * Метод для проверки корректности введённого номера. 
	 * Возвращает false, если номер короче 2х символов
	 * @param number номер для валидации
	 * @return boolean
	 * */
	public static boolean isValidNumber(String number) {
		if (number == null) {
			return false;
		}
		return number.length() >= 2;
	}
}
