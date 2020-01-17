package com.ir.site.util.ir;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Doc {
    //id
    public String id;
    //标题
    public String title;
    //作者
    public List<String> author;
    //刊物
    public String publication;
    //检索词
    public List<Word> word;
    //参考文献
//    public Doc reference;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    public void setAuthor(List<String> author){
        this.author = author;
    }
    public List<String> getAuthor(){
        return this.author;
    }
    public void addAuthor(String author){
        this.author.add(author);
    }

    public void setPublication(String publication){
        this.publication = publication;
    }
    public String getPublication(){
        return this.publication;
    }

    public void setWords(List<Word> words){
        this.word = words;
    }
    public List<Word> getWords(){
        return this.word;
    }
    public void addWord(Word word){
        this.word.add(word);
    }
    public void sortWord(){
        Collections.sort(this.word, new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                return o2.weight.compareTo(o1.weight);//降序
            }
        });
    }

//    public void setReference(Doc reference){
//        this.reference = reference;
//    }
//    public Doc getReference(){
//        return this.reference;
//    }
}
