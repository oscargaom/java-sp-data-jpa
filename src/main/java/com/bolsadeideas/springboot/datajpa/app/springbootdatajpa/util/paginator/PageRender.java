package com.bolsadeideas.springboot.datajpa.app.springbootdatajpa.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

    private String url;

    private Page<T> page;

    private List<PageItem> paginas;

    private int totalPages;

    private int itemsOnPage;

    private int currentPage;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();

        totalPages = page.getTotalPages();
        itemsOnPage = page.getSize();
        currentPage = page.getNumber() + 1;

        int end;
        int start;

        if (totalPages <= itemsOnPage) {
            start = 1;
            end = totalPages;
        } else {
            if (currentPage <= itemsOnPage / 2) {
                start = 1;
                end = itemsOnPage;
            } else if (currentPage >= totalPages - itemsOnPage / 2) {
                start = totalPages - itemsOnPage + 1;
                end = itemsOnPage;
            } else {
                start = currentPage - itemsOnPage / 2;
                end = itemsOnPage;
            }
        }

        for (int i = 0; i < end; i++) {
            paginas.add(new PageItem(start + i, currentPage == start + i));
        }
    }

    public String getUrl() {
        return url;
    }

    public Page<T> getPage() {
        return page;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }

    public boolean isHasPrevious(){
        return page.hasPrevious();
    }
}
