package com.ir.site.util.ir;

import java.util.List;

public class Inverted {
    //检索词
    public String word;
    //文献索引
    public List<Word> ids;//存文献索引+权重
    //ids.size()为绝对词频DF

    public void setWord(String word){
        this.word = word;
    }
    public String getWord(){
        return this.word;
    }

    public void setIds(List<Word> ids){
        this.ids = ids;
    }
    public List<Word> getIds(){
        return this.ids;
    }

    public void addId(Word obj){
        this.ids.add(obj);
    }
}
