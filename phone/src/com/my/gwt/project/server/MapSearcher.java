package com.my.gwt.project.server;

import java.util.*;

public class MapSearcher implements ISearcher {

    public MapSearcher() {
    }

    private Map<String, Long> map = new TreeMap<>();

    public void refresh(String[] classNames, long[] modificationDates) {
        assert classNames.length == modificationDates.length : "Both arrays should be equal size!";

        for (int i = 0; i < classNames.length; i++) {
            map.put(classNames[i], modificationDates[i]);
        }

        map = MapValueSorter.sortByValue(map);
    }

    public String[] guess(String start) {
        Iterator<Map.Entry<String, Long>> iterator = map.entrySet().iterator();

        List<String> resultList = new ArrayList<>();

        while (iterator.hasNext() && resultList.size() < 12) {
            Map.Entry<String, Long> entry = iterator.next();
            if (entry.getKey().startsWith(start)) {
                resultList.add(entry.getKey());
            }
        }

        String[] resultArr = new String[resultList.size()];
        resultArr = resultList.toArray(resultArr);

        return resultArr;
    }
}