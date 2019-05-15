package com.example.pca.lucene.impl;

import com.example.pca.lucene.ISearch;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public abstract class AbstractSearch implements ISearch {

    protected Analyzer analyzer;

    @Override
    public boolean createIndex(String indexPath, String resourcePath) throws IOException {
        /* Step1：创建IndexWrite对象
         * （1）定义词法分析器
         * （2）确定索引存储位置-->创建Directory对象
         * （3）得到IndexWriterConfig对象
         * （4）创建IndexWriter对象
         */

        Directory directory = FSDirectory.open(new File(indexPath).toPath());
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
            // 清除以前的索引
            indexWriter.deleteAll();

            // 得到txt后缀的文件集合
            Collection<File> txtFiles = FileUtils.listFiles(new File(resourcePath), new String[]{"txt"}, true);
            for (File file : txtFiles) {
                String fileName = file.getName();
                String content = FileUtils.readFileToString(file, "UTF-8");
                /*
                 * Step2：创建Document对象，将Field地下添加到Document中
                 * Field第三个参数选项很多，具体参考API手册
                 */
                Document document = new Document();
                document.add(new Field("fileName", fileName, TextField.TYPE_STORED));
                document.add(new Field("content", content, TextField.TYPE_STORED));

                /*
                 *Step4：使用 IndexWrite对象将Document对象写入索引库，并进行索引。
                 */
                indexWriter.addDocument(document);
            }
        }
        return false;
    }

    @Override
    public TopDocs queryIndex(String indexPath, String keyword) throws IOException {
        TopDocs topDocs = null;
        /*
         * Step1：创建IndexSearcher对象
         * （1）创建Directory对象
         * （2）创建DirectoryReader对象
         * （3）创建IndexSearcher对象
         */
        Directory directory = FSDirectory.open(new File(indexPath).toPath());

        try (DirectoryReader reader = DirectoryReader.open(directory)) {
            IndexSearcher indexSearcher = new IndexSearcher(reader);

            /*
             * Step2：创建TermQuery对象，指定查询域和查询关键词
             */
            Term fTerm = new Term("fileName", keyword);
            Term cTerm = new Term("content", keyword);
            TermQuery query1 = new TermQuery(fTerm);
            TermQuery query2 = new TermQuery(cTerm);

            /*
             * Step3：创建Query对象
             */
            Query booleanBuery = new BooleanQuery.Builder().add(query1, BooleanClause.Occur.SHOULD).add(query2,
                    BooleanClause.Occur.SHOULD)
                    .build();

            topDocs = indexSearcher.search(booleanBuery, 100);
            System.out.println("共找到 " + topDocs.totalHits + " 个文件匹配");


            //打印结果
            printTopDocs(topDocs.scoreDocs, indexSearcher, getHighlighter(booleanBuery));

        } catch (InvalidTokenOffsetsException e) {
            e.printStackTrace();
        }

        return topDocs;
    }

    /**
     * 返回高亮对象
     *
     * @param query
     * @return
     */
    private Highlighter getHighlighter(Query query) {
        // 格式化器
        Formatter formatter = new SimpleHTMLFormatter("<em>", "</em>");

        //算分
        QueryScorer scorer = new QueryScorer(query);

        // 准备高亮工具
        Highlighter highlighter = new Highlighter(formatter, scorer);

        //显示得分高的片段,片段字符长度fragmentSize=1000
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, 200);

        //设置片段
        highlighter.setTextFragmenter(fragmenter);
        return highlighter;
    }

    private void printTopDocs(ScoreDoc[] scoreDocs, IndexSearcher indexSearcher, Highlighter highlighter) throws IOException, InvalidTokenOffsetsException {

        for (ScoreDoc scoreDoc : scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            String fileName = doc.get("fileName");
            System.out.println("fileName=" + fileName);

            if (null != highlighter) {
                //高亮处理
                String content = doc.get("content");
                System.out.println(content);
                System.out.println("====================");
                String hContent = highlighter.getBestFragment(analyzer, "content", content);
                System.out.println(hContent);
            }
            System.out.println();
        }
    }
}
