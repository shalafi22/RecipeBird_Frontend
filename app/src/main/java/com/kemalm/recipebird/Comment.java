package com.kemalm.recipebird;

import java.io.Serializable;

public class Comment implements Serializable {
    private String content;
    private String authorUser;
    Comment() {}
    Comment(String _content, String _authorUser) {
        this.authorUser = _authorUser;
        this.content = _content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorUser() {
        return authorUser;
    }

    public void setAuthorUser(String authorUser) {
        this.authorUser = authorUser;
    }
}
