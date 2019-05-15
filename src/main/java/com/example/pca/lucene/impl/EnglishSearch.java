package com.example.pca.lucene.impl;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "englishSearch")
public class EnglishSearch extends AbstractSearch implements InitializingBean {

    @Resource(name = "standardAnalyzer")
    private Analyzer standardAnalyzer;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.analyzer = standardAnalyzer;
    }

}
