package com.example.pca.lucene;

import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public interface ISearch {
    /**
     * 创建索引
     * @param indexPath  索引文件路径
     * @param resourcePath  资源文件路径
     * @return
     */
    boolean createIndex(String indexPath, String resourcePath) throws IOException;

    /**
     * 关键字查询
     * @param indexPath 索引文件路径
     * @param keyword  关键字
     * @return
     * @throws IOException
     */
    TopDocs queryIndex(String indexPath, String keyword)throws IOException;
}
