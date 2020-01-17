package com.ir.site.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lcl
 * @since 2019-11-23
 */
@TableName("title_inverted")
public class TitleInverted implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 检索词
     */
    private String word;

    /**
     * 文献id
     */
    private String passage;


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPassage() {
        return passage;
    }

    public void setPassage(String passage) {
        this.passage = passage;
    }

    @Override
    public String toString() {
        return "TitleInverted{" +
        "word=" + word +
        ", passage=" + passage +
        "}";
    }
}
