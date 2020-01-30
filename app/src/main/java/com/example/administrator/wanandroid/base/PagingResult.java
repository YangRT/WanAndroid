package com.example.administrator.wanandroid.base;

public class PagingResult {

    private boolean isEmpty;
    private boolean isFirst;
    private boolean hasNextPage;


    public PagingResult(boolean isEmpty,boolean isFirst,boolean hasNextPage){
        this.isEmpty = isEmpty;
        this.isFirst = isFirst;
        this.hasNextPage = hasNextPage;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean hasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean nextPage) {
        hasNextPage = nextPage;
    }
}
