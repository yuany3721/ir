package com.ir.site.controller;


import com.ir.site.entity.Doc;
import com.ir.site.service.DocService;
import com.ir.site.util.ir.main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lcl
 * @since 2019-11-23
 */
@Controller
@RequestMapping("/doc")
public class DocController {
    @Autowired
    public DocService docService;

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public int upload(MultipartFile file) throws IOException{
        if(file!=null){
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getContentType());
            InputStream input = file.getInputStream();
//            FileOutputStream output = new FileOutputStream("E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\txt\\" + file.getOriginalFilename());
            FileOutputStream output = new FileOutputStream("/usr/website/irfiles/" + file.getOriginalFilename());
            byte[] b = file.getBytes();
            for (byte btye : b){
                output.write(btye);
            }
            input.close();
            output.close();
        }else{
            System.out.println("uploadFile=null");
        }
        return 1;
    }

    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public List<Doc> getAll(){
        return docService.getAll();
    }

    @ResponseBody
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public int refresh() {
        main func = new main();
        func.main();
//        String filepath = "E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\txt\\toRead";
        String filepath = "/usr/website/irfiles/toRead";
        File file = new File(filepath);
        File[] fileList = file.listFiles();
        if(fileList.length == 0){
            return 0;
        }
        for(File filename : fileList){
            docService.refresh(filename);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){
                return 0;
            }
        }
        return 1;
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Doc> search(String type, String have, String unsure, String nothave) {
        System.out.println(have);
        System.out.println(unsure);
        System.out.println(nothave);
        if (have.equals("") && unsure.equals("")) {
            return null;
        }
        List<String> resIn = new ArrayList<>();
        List<String> res = new ArrayList<>();
        main func = new main();
        if(!unsure.equals("")){
            List<String> words = new ArrayList<>();
            if(type.equals("author")){
                words.add(unsure);
            }
            else{
                String Sentences[] = unsure.split("\\s+");
                if (Sentences.length > 1) {
                    words = Arrays.asList(Sentences);
                } else if (Sentences.length == 1) {
                    String sentences[] = Sentences[0].split("。");
                    for (String sentence : sentences) {
                        words.addAll(func.handleSentence(sentence, true));
                    }
                }
            }
            switch (type) {
                case "title":
                    System.out.println("title:" + words.toString());
                    res = docService.titleRetrieval(words);
                    break;
                case "author":
                    System.out.println("author:" + words.toString());
                    res = docService.authorRetrieval(words);
                    break;
                case "passage":
                    System.out.println("passage:" + words.toString());
                    res = docService.passageRetrieval(words);
                    break;
            }
        }

        if (!have.equals("")) {
            List<String> wordsInHave = new ArrayList<>();
            List<Doc> temp = null;
            wordsInHave.addAll(func.handleSentence(have, true));
            for(int i = 0; i < wordsInHave.size(); i++){
                if(i == 0){
                    temp = search(type, "", wordsInHave.get(i), "");
                }
                else {
                    if (!temp.isEmpty()){
                        temp.retainAll(search(type, "", wordsInHave.get(i), ""));
                    }
                    else {
                        return null;
                    }
                }
            }
            for(Doc t : temp) {
                resIn.add(t.getId());
            }
        }
        if(!nothave.equals("")) {
            List<Doc> tempDoc = search(type, "", nothave, "");
            List<String> tempString = new ArrayList<>();
            for(Doc t : tempDoc) {
                tempString.add(t.getId());
            }
            res.removeAll(tempString);
        }
        if (!resIn.isEmpty()){
            if(res.isEmpty()){
                return docService.getByIds(resIn);
            }
            res.retainAll(resIn);
            List<String> finalIds = new ArrayList<>();
            for (String r : res) {
                    finalIds.add(r);
            }
            return docService.getByIds(finalIds);
        }
        return docService.getByIds(res);
    }

}

