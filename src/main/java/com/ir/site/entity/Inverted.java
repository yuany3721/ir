package com.ir.site.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author lcl
 * @since 2019-11-23
 */
@TableName("inverted")
public class Inverted implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 检索词
     */
    private String word;

    /**
     * 文献id
     */
    private String passage;

    /**
     * 权重
     */
    private BigDecimal weight;


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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Inverted{" +
        "word=" + word +
        ", passage=" + passage +
        ", weight=" + weight +
        "}";
    }
}
