package com.example.pca.lucene.impl;

import org.apache.lucene.analysis.Analyzer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.Resource;

@Service(value = "chineseSearch")
public class ChineseSearch extends AbstractSearch implements InitializingBean {
    @Resource(name = "iKAnalyzer")
    private Analyzer iKAnalyzer;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.analyzer = iKAnalyzer;
    }
}
