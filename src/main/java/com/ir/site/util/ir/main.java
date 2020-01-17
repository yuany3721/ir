package com.ir.site.util.ir;


import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class main {
    private Set<String> stopWords = getWords("stop.txt");
    private Set<String> segmentWords = getWords("divide.txt");
    Doc doc = new Doc();

    boolean isReference = false;//判定参考文献读取

    private Set<String> getWords(String file){
//        File segment = new File("E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\" + file);
        File segment = new File("/usr/website/irfiles/words/" + file);
        BufferedReader reader = null;
        Set<String> Words = new HashSet<String>();
        String word = "";
        try {
            reader = new BufferedReader(new FileReader(segment));
            while ((word = reader.readLine()) != null){
                Words.add(word);
//                System.out.println(word);
            }
        }catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Words;
    }
    private List<String> removeStop(List<String> list){
        for(int i = 0; i < list.size(); i++){
            if(stopWords.contains(list.get(i))){
                list.remove(i);
            }
        }
        return list;
    }
    public List<String> handleSentence(String sentence, boolean order){
        int lastIndex = sentence.length();
        int beginIndex = 0;
        if(order){
            //逆向最大匹配
            List<String> segementReOrder = new ArrayList<>();
            while (beginIndex < lastIndex){
                String temp = sentence.substring(beginIndex, lastIndex);
                if(temp.equals("，") || temp.equals("；") || temp.equals("：") || temp.equals("《") || temp.equals("》") || temp.equals("“") || temp.equals("”") || temp.equals("、")){
                    lastIndex--;
                    beginIndex = 0;
                    continue;
                }
//                System.out.println("now" + temp);
                if(segmentWords.contains(temp)){
                    lastIndex = beginIndex;
                    beginIndex = 0;
                    if(temp.length() == 1 && segementReOrder.size() != 0){
                        //单字存在错分情况很大，用正向最大匹配重新分两个词
                        String reSeg = temp + segementReOrder.remove(0);
                        List<String> inOrder = handleSentence(reSeg, false);
                        for(int i = inOrder.size()-1; i >= 0; i--){
                            segementReOrder.add(0, inOrder.get(i));
                        }
                    }
                    else {
                        segementReOrder.add(0,temp);
                    }
                }
                else {
                    beginIndex++;
                }
            }
            segementReOrder = removeStop(segementReOrder);
            return segementReOrder;
        }
        else {
            //正向最大匹配
            List<String> segementInOrder = new ArrayList<>();
            while (beginIndex < lastIndex){
                String temp = sentence.substring(beginIndex, lastIndex);
                if(temp.equals("，") || temp.equals("；") || temp.equals("：") || temp.equals("《") || temp.equals("》") || temp.equals("“") || temp.equals("”") || temp.equals("、")){
                    lastIndex = sentence.length();
                    beginIndex++;
                    continue;
                }
//                System.out.println("now" + temp);
                if(segmentWords.contains(temp)){
                    beginIndex = lastIndex;
                    lastIndex = sentence.length();
                    segementInOrder.add(temp);
                }
                else {
                    lastIndex--;
                }
            }
            segementInOrder = removeStop(segementInOrder);
            return segementInOrder;
        }

    }
    private void handleWords(BigDecimal weight, List<String> words){
        for(String word : words){
            if(doc.word != null){
                int i;
                for(i =0; i < doc.word.size(); i++){
                    if(doc.word.get(i).word.equals(word)){
                        doc.word.get(i).times++;
                        doc.word.get(i).weight = doc.word.get(i).weight.add(weight);
                        break;
                    }
                }
                if(i == doc.word.size()){
                    Word temp = new Word();
                    temp.word = word;
                    temp.times++;
                    temp.weight = temp.weight.add(weight);
                    doc.word.add(temp);
                }
            }
            else {
                Word temp = new Word();
                temp.word = word;
                temp.times++;
                temp.weight = temp.weight.add(weight);
                List<Word> set = new ArrayList<>();
                set.add(temp);
                doc.setWords(set);
            }
        }
    }
    private void handlePublication(String line){
        doc.setPublication(line);
    }
    private void handleTitle(String line){
        doc.setTitle(line);
        handleWords(new BigDecimal("2.00").setScale(2), handleSentence(line, true));//标题权重2.00
        try {
//            File file = new File("E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\txt\\" + doc.id + "标题分词.txt");
            File file = new File("/usr/website/irfiles/" + doc.id + "divide.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            for(Word word : doc.word){
                bw.write(word.word + "\n");
            }
            bw.close();
        }
        catch (IOException e){
            System.out.println("打开顺排档失败");
        }
    }
    private void handleAuthor(String line){
        String authors[] = line.split("，");
        for(String author : authors){
//            System.out.println(author);
            if(doc.author != null && !doc.author.isEmpty()){
                doc.addAuthor(author);
            }
            else {
                List<String> temp = new ArrayList<>();
                temp.add(author);
                doc.setAuthor(temp);
            }
        }
    }
    private void handleAbstract(String line){
        String sentences[] = line.split("：")[1].split("。|？|！");
        for(String sentence : sentences){
//            System.out.println(sentence);
//            List<String> forprint = handleSentence(sentence, true);
//            for(String print : forprint){
//                System.out.println(print);
//                System.out.println("----------");
//            }
            handleWords(new BigDecimal("1.60").setScale(2), handleSentence(sentence, true));//摘要中权重1.60
        }
    }
    private void handleKeyWords(String line){
        String sentences[] = line.split("：")[1].split("；");
        handleWords(new BigDecimal("2.10").setScale(2), new ArrayList<>(Arrays.asList(sentences)));//关键词权重2.10
    }
    private void handlePassage(String line){
         if(isReference){
             //不做参考文献的检索了，暂时注释掉
//             Doc refer = new Doc();
//             String authors[] = line.split("]")[1].split(",|等|.");
//             for(String author : authors){
//                 if(refer.author != null && !refer.author.isEmpty()){
//                     refer.author.add(author);
//                 }
//                 else {
//                     List<String> temp = new ArrayList<>();
//                     temp.add(author);
//                     refer.setAuthor(temp);
//                 }
//             }
             String title = line.split("\\[")[0].split(".")[0];
//             refer.setTitle(title);
             handleWords(new BigDecimal("0.50").setScale(2), handleSentence(title, true));//参考文献权重0.50
//             String publication = line.split(".")[2].split(",|.")[0];
//             refer.setPublication(publication);
         }
         else {
             String checkTitle = line.length() >= 4 ? line.substring(0, 4) : line;//检测是否为标题
             if(checkTitle.contains("（") || checkTitle.contains("(") || checkTitle.contains("、") || checkTitle.contains(".")){
                 //前5个字符内含有中英文括号 顿号 英文句点的认为是小标题
                 String subTitle = line.split("[()（）、.]")[1];
                 handleWords(new BigDecimal("1.80").setScale(2), handleSentence(subTitle, true));//小标题权重1.80
             }
             else {
                 String Sentence[] = line.split("。|\\s+");
                 if (Sentence.length == 0)
                     return;
                 //处理首句
                 handleWords(new BigDecimal("1.10").setScale(2), handleSentence(Sentence[0], true));//首句权重1.10
                 if (Sentence.length == 1)
                     return;
                 //处理尾句
                 handleWords(new BigDecimal("1.10").setScale(2), handleSentence(Sentence[Sentence.length-1], true));//尾句权重1.10
                 if (Sentence.length == 2)
                     return;
                 for(int i = 1; i < Sentence.length-1; i++){
                     //处理其他句
                     handleWords(new BigDecimal("1.00").setScale(2), handleSentence(Sentence[i], true));//正文权重1.00
                 }
             }

         }
    }
    private void removeSurplus(){
        doc.sortWord();
        List<Word> newWords = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            newWords.add(doc.word.get(i));
        }
        doc.setWords(newWords);
    }
    private void print(){
        try {
//            File file = new File("E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\txt\\toRead\\" + doc.id + ".txt");
            File file = new File("/usr/website/irfiles/toRead/" + doc.id + ".txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(doc.id + "\n");
            bw.write(doc.publication + "\n");
            bw.write(doc.title + "\n");
            bw.write(doc.author.toString() + "\n");
            bw.write(doc.word.toString() + "\n");
            for(Word word : doc.word){
                bw.write(word.word + "\n");
//                bw.write(word.times + "\n");
                bw.write(word.weight + "\n");
            }
            bw.close();
        }
        catch (IOException e){
            System.out.println("打开顺排档失败");
        }
    }
    public void newMain(File filename){
        doc = new Doc();
        BufferedReader reader = null;
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(filename));
            Date id = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");//以24小时制年月日时分秒作为id
            doc.setId(ft.format(id));
            //读取刊名
            line = reader.readLine();
            handlePublication(line);
            //读取标题
            line = reader.readLine();
            handleTitle(line);
            //读取作者
            line = reader.readLine();
            handleAuthor(line);
            //读取摘要
            line = reader.readLine();
            handleAbstract(line);
            //读取关键词
            line = reader.readLine();
            handleKeyWords(line);
            //读取正文
            while ((line = reader.readLine()) != null){
                handlePassage(line);
            }
            removeSurplus();
            print();
        } catch (FileNotFoundException e) {
            System.out.println("找不到指定文件");
        } catch (IOException e) {
            System.out.println("读取文件失败");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        filename.renameTo(new File("E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\txt\\toShow\\" + doc.id + ".txt"));
        filename.renameTo(new File("/usr/website/irfiles/toShow/" + doc.id + ".txt"));
    }

    public void main() {
        //为了方便调用，去掉static  String[] args使之变为非静态方法
        main func = new main();
//        String filepath = "E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\txt";
        String filepath = "/usr/website/irfiles";
        File file = new File(filepath);
        File[] fileList = file.listFiles();
        for(File filename : fileList){
            if(filename.isFile()){
                func.newMain(filename);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    System.out.println("WRONG");
                }
            }
        }
    }
}
