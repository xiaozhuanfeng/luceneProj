package com.example.pca.lucene.constants;

public enum FilePkg {

    FILE_PKG("luceneResource\\"),
    INDEX_PKG("luceneIndex\\");

    private String pkgName;

    FilePkg(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
