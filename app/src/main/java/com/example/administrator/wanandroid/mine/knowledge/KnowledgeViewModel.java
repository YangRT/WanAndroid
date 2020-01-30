package com.example.administrator.wanandroid.mine.knowledge;

import com.example.administrator.wanandroid.base.MvvmBaseViewModel;

public class KnowledgeViewModel extends MvvmBaseViewModel<KnowledgeModel,KnowledgeInfo.Data> {

    public KnowledgeViewModel(){
        super();
        model = new KnowledgeModel();
        model.register(this);
        model.getCachedDataAndLoad();
    }
}
