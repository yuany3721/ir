package com.ir.site.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ir.site.dao.AuthorMapper;
import com.ir.site.dao.DocMapper;
import com.ir.site.dao.InvertedMapper;
import com.ir.site.dao.TitleInvertedMapper;
import com.ir.site.entity.Doc;
import com.ir.site.entity.Inverted;
import com.ir.site.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lcl
 * @since 2019-11-23
 */
@Service
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc> implements DocService {
    @Autowired
    public DocMapper docMapper;
    @Autowired
    public InvertedMapper invertedMapper;
    @Autowired
    public AuthorMapper authorMapper;
    @Autowired
    public TitleInvertedMapper titleInvertedMapper;

    @Override
    public void refresh(File filename) {
        BufferedReader reader = null;
        String line = "";
        Doc newDoc = new Doc();
        try {
            reader = new BufferedReader(new FileReader(filename));
            //读取id
            if((line = reader.readLine()) == null){
                return;
            }
            newDoc.setId(line);
            //刊物
            line = reader.readLine();
            newDoc.setPublication(line);
            //标题
            line = reader.readLine();
            newDoc.setTitle(line);
            //作者
            line = reader.readLine();
            newDoc.setAuthor(line);
            List<String> authors = newDoc.getAuthors();
            for(String author :authors){
                authorMapper.addPassage(newDoc.getId(), author);
            }
            //检索词
            line = reader.readLine();
            newDoc.setWord(line);
            docMapper.setPassage(newDoc);
            while ((line = reader.readLine()) != null){
                Inverted temp = new Inverted();
                temp.setPassage(newDoc.getId());
                temp.setWord(line);
                line = reader.readLine();
                temp.setWeight(new BigDecimal(line).setScale(2));
                invertedMapper.addPassage(temp);
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
        filename.delete();
//        filename = new File("E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\txt\\" + newDoc.getId() +  "标题分词.txt");
        filename = new File("/usr/website/irfiles/" + newDoc.getId() +  "divide.txt");
        try {
            reader = new BufferedReader(new FileReader(filename));
            while ((line = reader.readLine()) != null){
                titleInvertedMapper.addPassage(line, newDoc.getId());
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
        filename.delete();
    }

    @Override
    public List<Doc> getAll() {
        return docMapper.getAll();
    }

    @Override
    public List<String> titleRetrieval(List<String> value) {
        List<String> res = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        for(String word :value){
            List<String> passages = titleInvertedMapper.getPassageByWord(word);
            for(String passage : passages){
                if(map.containsKey(passage)){
                    map.put(passage, map.get(passage)+1);
                }
                else {
                    map.put(passage, 1);
                }
            }
        }
        //按Value降序排序
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(Map.Entry l : list){
            res.add(l.getKey().toString());
        }
        return res;
    }

    @Override
    public List<String> authorRetrieval(List<String> value) {
        List<String> res = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        for(String author :value){
            List<String> passages = authorMapper.getPassageByAuthor(author);
            for(String passage : passages){
                if(map.containsKey(passage)){
                    map.put(passage, map.get(passage)+1);
                }
                else {
                    map.put(passage, 1);
                }
            }
        }
        //按Value降序排序
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(Map.Entry l : list){
            res.add(l.getKey().toString());
        }
        return res;
    }

    @Override
    public List<String> passageRetrieval(List<String> value) {
        List<String> res = new ArrayList<>();
        HashMap<String, BigDecimal> map = new HashMap<>();
        for(String word :value){
            List<Inverted> inverteds = invertedMapper.getPassageByWord(word);
            for(Inverted inverted : inverteds){
                if(map.containsKey(inverted.getPassage())){
                    map.put(inverted.getPassage(), map.get(inverted.getPassage()).add(inverted.getWeight()));
                }
                else {
                    map.put(inverted.getPassage(), inverted.getWeight());
                }
            }
        }
        //按Value降序排序
        List<Map.Entry<String, BigDecimal>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, BigDecimal>>() {
            @Override
            public int compare(Map.Entry<String, BigDecimal> o1, Map.Entry<String, BigDecimal> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(Map.Entry l : list){
            res.add(l.getKey().toString());
        }
        return res;
    }

    @Override
    public List<String> titleHave(String value) {
        return titleInvertedMapper.getPassageByWord(value);
    }

    @Override
    public List<String> authorHave(String value) {
        return authorMapper.getPassageByAuthor(value);
    }

    @Override
    public List<String> passageHave(String value) {
        return invertedMapper.getPassageNameByWord(value);
    }

    @Override
    public List<Doc> getByIds(List<String> ids) {
        List<Doc> res = new ArrayList<>();
        for(String id : ids){
            res.add(docMapper.getById(id));
        }
        return res;
    }
}
