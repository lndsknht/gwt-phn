package com.my.gwt.project.server;

import static com.my.gwt.project.server.FieldValidator.isValidName;
import static com.my.gwt.project.server.FieldValidator.isValidNumber;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

public class DAOContact {
	
    private static Trie<String, String> contactsTrie = new PatriciaTrie<>();

	private Trie<String, String> getContacts() 
	{
		return contactsTrie;
	}

	/**
	 * —оздать и добавить контакт в систему
	 * @param contactName им€ контакта
	 * @param phoneNumber номер контакта в свободной форме
	 * @throws IllegalArgumentException
	 * */
	public static void addContact(String contactName, String phoneNumber) 
	{
		String noramlizedNum = normalizeNum(phoneNumber);
		if (isValidName(contactName) && isValidNumber(noramlizedNum))
		{
			contactsTrie.put(noramlizedNum.toString(), contactName);
		}
		else
		{
			throw new IllegalArgumentException(String.format(" онтакт с именем %s и номером %s не может быть добавлен!", contactName, phoneNumber));
		}
	} 
	
	private ContactInfo getContact(String nameOrNumber)
	{
		return null;		
	}
	
	/**
	 * ”далить все лишние символы из номера, оставив только цифры
	 * @param phoneNumber номер дл€ редактировани€
	 * @return отредактированный номер
	 * */
	private static String normalizeNum(String phoneNumber)
	{
		return phoneNumber.replaceAll("[^0-9]", "");		
	}
	
    public static String[] guess(String start) {
        String[] resultArray;
        Map<String, String> resultMap = MapValueSorter.sortByValue(contactsTrie.prefixMap(start));
        if (resultMap.size() >= 12) {
            resultArray = new String[12];
        } else {
            resultArray = new String[resultMap.size()];
        }

        Iterator<Map.Entry<String, String>> iterator = resultMap.entrySet().iterator();

        for (int i = 0; i < resultArray.length; i++) {
            if (iterator.hasNext()) {
                resultArray[i] = iterator.next().getKey();
            }
        }
        return resultArray;
    }
}
