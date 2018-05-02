package com.my.gwt.project.server;

public interface ISearcher {
    void refresh(String[] classNames, long[] modificationDates);
    String[] guess(String start);
}
