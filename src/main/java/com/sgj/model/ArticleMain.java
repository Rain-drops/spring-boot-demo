package com.sgj.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class ArticleMain {
//    @Id
    private String articleId;
    private String articleTitle;
    private String articleClass;
    private String articleAuthor;
    private String articleUrl;
    private String articleContent;
    private String articleDate;
    private Date createdDate;
    private String articlePage;
    private String sourcePlatform;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleClass() {
        return articleClass;
    }

    public void setArticleClass(String articleClass) {
        this.articleClass = articleClass;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getArticlePage() {
        return articlePage;
    }

    public void setArticlePage(String articlePage) {
        this.articlePage = articlePage;
    }

    public String getSourcePlatform() {
        return sourcePlatform;
    }

    public void setSourcePlatform(String sourcePlatform) {
        this.sourcePlatform = sourcePlatform;
    }
}
