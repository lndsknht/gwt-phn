package com.my.gwt.project.server;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.util.*;

public class TrieSearcher implements ISearcher {

    private Trie<String, Long> trie = new PatriciaTrie<>();

    public TrieSearcher() {
    }

    @Override
    public void refresh(String[] classNames, long[] modificationDates) {
        assert classNames.length == modificationDates.length : "Both arrays should be equal size!";

        for (int i = 0; i < classNames.length; i++) {
            trie.put(classNames[i], modificationDates[i]);
        }
    }

    @Override
    public String[] guess(String start) {
        String[] resultArray;
        Map<String, Long> resultMap = MapValueSorter.sortByValue(trie.prefixMap(start));
        if (resultMap.size() >= 12) {
            resultArray = new String[12];
        } else {
            resultArray = new String[resultMap.size()];
        }

        Iterator<Map.Entry<String, Long>> iterator = resultMap.entrySet().iterator();

        for (int i = 0; i < resultArray.length; i++) {
            if (iterator.hasNext()) {
                resultArray[i] = iterator.next().getKey();
            }
        }
        return resultArray;
    }
}
