package com.example.pca.lucene.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Configuration
public class AnalyzerConfig {

    @Bean("standardAnalyzer")
    public Analyzer getStandardAnalyzer() {
        // 使用标准分词器,但对于中文颇为无力
        //lucene自带分词器，SmartChineseAnalyzer()
        //缺点：扩展性差，扩展词库、禁用词库和同义词库等不好处理

       /* 第三方分词器
       paoding：庖丁解牛，但是其最多只支持到Lucene3，已经过时，不推荐使用。
       mmseg4j：目前支持到Lucene6 ，目前仍然活跃，使用mmseg算法。  参考：https://www.jianshu.com/p/03f4a906cfb5
       IK-Analyzer：开源的轻量级的中文分词工具包，官方支持到Lucene5。
        */
        return new StandardAnalyzer();
    }

    @Bean("iKAnalyzer")
    public Analyzer getIKAnalyzer() {
        //IK分词，试了下，也可以支持中文
        return new IKAnalyzer();
    }
}
