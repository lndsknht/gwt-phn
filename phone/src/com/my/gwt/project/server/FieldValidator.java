package com.my.gwt.project.server;

import java.util.regex.Pattern;

/**
 * ����� ������������ ��� �������� �������� �������� ��� � �������
 * @author landsknecht
 * */
public class FieldValidator {

	/**
	 * ����� ��� �������� ������������ ��������� �����. 
	 * ���������� false, ���� ��� �������� ����� �������, ����� ���� ��� ����� ����� < 3
	 * @param name ��� ��� ���������
	 * @return boolean
	 * */
	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		
		return Pattern.matches("[a-zA-Z]+", name) && name.length() >= 3;
	}
	
	/**
	 * ����� ��� �������� ������������ ��������� ������. 
	 * ���������� false, ���� ����� ������ 2� ��������
	 * @param number ����� ��� ���������
	 * @return boolean
	 * */
	public static boolean isValidNumber(String number) {
		if (number == null) {
			return false;
		}
		return number.length() >= 2;
	}
}
