package com.my.gwt.project.server;

import static com.my.gwt.project.server.FieldValidator.isValidName;
import static com.my.gwt.project.server.FieldValidator.isValidNumber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DAOContact {

	private static List<String> names = new ArrayList<>();
	private static List<String> phoneNumbers = new ArrayList<>();
	private static boolean lineIsNum = false;

	/**
	 * Создать и добавить контакт в систему
	 * @param contactName имя контакта
	 * @param phoneNumber номер контакта в свободной форме
	 * @throws IllegalArgumentException
	 */
	public static void addContact(String contactName, String phoneNumber) {
		String noramlizedNumber = normalizeNum(phoneNumber);
		if (isValidName(contactName) && isValidNumber(noramlizedNumber)) {
			if (!phoneNumbers.contains(noramlizedNumber)) {
				names.add(contactName);
				phoneNumbers.add(noramlizedNumber);
			} else {
				throw new IllegalArgumentException(String.format("Контакт с номером %s уже существует!", phoneNumber));
			}
		} else {
			throw new IllegalArgumentException(String.format("Контакт с именем %s и номером %s не может быть добавлен!",
					contactName, phoneNumber));
		}
	}

	/**
	 * Удалить все лишние символы из номера, оставив только цифры
	 * @param phoneNumber номер для редактирования
	 * @return отредактированный номер
	 */
	private static String normalizeNum(String phoneNumber) {
		if (phoneNumber == null)
			return "";
		return phoneNumber.replaceAll("[^0-9]", "");
	}

	/**
	 * Метод для определения, содержит ли строка только цифры
	 * @param textToCheck проверяемая строка
	 * @return true - если только цифры, false - если строка состоит только из
	 *         букв или же из букв и цифр
	 */
	private static boolean isNumber(String textToCheck) {
		return textToCheck.matches("\\d+");
	}

	private static String[] getEntries(String numOrName) {
		lineIsNum = isNumber(numOrName);
		Stream<String> stream = lineIsNum ? phoneNumbers.stream() : names.stream();
		return stream.filter(strings -> strings.contains(numOrName)).toArray(String[]::new);
	}

	/**
	 * Получить найденные совпадения в виде мапы (номер-имя)
	 * */
	public static Map<String, String> guess(Object numOrName) {
		assert numOrName instanceof String;

		String lineToSearch = (String) numOrName;
		String[] entries = getEntries(lineToSearch);
		Map<String, String> foundedLines = new HashMap<>();
		if (entries.length == 0) return foundedLines;

		if (lineIsNum) {
			for (int i = 0; i < entries.length; i++) 
			{
				foundedLines.put(entries[i], names.get(phoneNumbers.indexOf(entries[i])));
			}
		} else {
			for (int i = 0; i < entries.length; i++) 
			{
				foundedLines.put(phoneNumbers.get(names.indexOf(entries[i])), entries[i]);
			}
		}
		return foundedLines;
	}
}
