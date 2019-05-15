package com.example.pca.lucene;

import com.example.pca.utils.ProjectPathUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.IOException;

import static com.example.pca.lucene.constants.FilePkg.FILE_PKG;
import static com.example.pca.lucene.constants.FilePkg.INDEX_PKG;

@RunWith(SpringRunner.class)
@SpringBootTest
public class luceneTest {

    /**
     * F:\xxxx\ideaProjects\luceneProj\luceneResource\
     */
    private static final String RESOURCE_PATH = ProjectPathUtils.getProjPath("luceneProj") + FILE_PKG.getPkgName();

    /**
     * F:\xxxx\ideaProjects\luceneProj\luceneIndex\
     */
    private static final String INDEX_PATH = ProjectPathUtils.getProjPath("luceneProj") + INDEX_PKG.getPkgName();

    @Resource(name = "englishSearch")
    private ISearch englishSearch;

    @Resource(name = "chineseSearch")
    private ISearch chineseSearch;

    @Test
    public void test1() {
        System.out.println(RESOURCE_PATH);
        System.out.println(INDEX_PATH);
    }

    @Test
    public void test2() {
        try {
            englishSearch.createIndex(INDEX_PATH + "US\\", RESOURCE_PATH +"US\\");
            englishSearch.queryIndex(INDEX_PATH +"US\\", "spring");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        try {
            chineseSearch.createIndex(INDEX_PATH +"EN\\", RESOURCE_PATH + "EN\\");
            chineseSearch.queryIndex(INDEX_PATH +"EN\\", "孙悟空");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        try {
            chineseSearch.createIndex(INDEX_PATH +"US\\", RESOURCE_PATH + "US\\");
            chineseSearch.queryIndex(INDEX_PATH +"US\\", "spring");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
