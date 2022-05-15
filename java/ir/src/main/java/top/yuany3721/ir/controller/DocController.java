package top.yuany3721.ir.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import top.yuany3721.ir.util.ir.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import top.yuany3721.ir.entity.Doc;
import top.yuany3721.ir.service.DocService;
import top.yuany3721.ir.util.Response;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lcl
 * @since 2022-05-15
 */
@Slf4j
@RestController
@RequestMapping()
public class DocController {
    @Autowired
    public DocService docService;

    @PostMapping("/uploadFile")
    public Response<?> upload(MultipartFile file) throws IOException {
        if (file != null) {
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getContentType());
            InputStream input = file.getInputStream();
            // FileOutputStream output = new
            // FileOutputStream("E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\txt\\"
            // + file.getOriginalFilename());
            FileOutputStream output = new FileOutputStream("/home/yuany3721/ir/irfiles/" + file.getOriginalFilename());
            byte[] b = file.getBytes();
            for (byte btye : b) {
                output.write(btye);
            }
            input.close();
            output.close();
        } else {
            log.info("uploadFile=null");
        }
        return Response.success(1);
    }

    @GetMapping("/getAllDoc")
    public Response<?> getAll() {
        return Response.success(docService.getAll());
    }

    @PostMapping("/refreshDoc")
    public Response<?> refresh() {
        main func = new main();
        func.main();
        // String filepath =
        // "E:\\website_IR\\src\\main\\java\\com\\ir\\site\\util\\ir\\txt\\toRead";
        String filepath = "/home/yuany3721/ir/irfiles/toRead";
        File file = new File(filepath);
        File[] fileList = file.listFiles();
        if (fileList.length == 0) {
            return Response.success(0);
        }
        for (File filename : fileList) {
            docService.refresh(filename);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return Response.success(0);
            }
        }
        return Response.success(1);
    }

    @PostMapping("/search")
    public Response<List<Doc>> search(String type, String have, String unsure, String nothave) {
        log.info("have: " + have);
        log.info("unsure: " + unsure);
        log.info("nothave: " + nothave);
        log.info("type: " + type);
        if (have.equals("") && unsure.equals("")) {
            return null;
        }
        List<String> resIn = new ArrayList<>();
        List<String> res = new ArrayList<>();
        main func = new main();
        if (!unsure.equals("")) {
            List<String> words = new ArrayList<>();
            if (type.equals("author")) {
                words.add(unsure);
            } else {
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
                    log.info("title:" + words.toString());
                    res = docService.titleRetrieval(words);
                    break;
                case "author":
                    log.info("author:" + words.toString());
                    res = docService.authorRetrieval(words);
                    break;
                case "passage":
                    log.info("passage:" + words.toString());
                    res = docService.passageRetrieval(words);
                    break;
            }
        }

        if (!have.equals("")) {
            List<String> wordsInHave = new ArrayList<>();
            List<Doc> temp = null;
            wordsInHave.addAll(func.handleSentence(have, true));
            for (int i = 0; i < wordsInHave.size(); i++) {
                if (i == 0) {
                    temp = search(type, "", wordsInHave.get(i), "").getData();
                } else {
                    if (!temp.isEmpty()) {
                        temp.retainAll(search(type, "", wordsInHave.get(i), "").getData());
                    } else {
                        return null;
                    }
                }
            }
            for (Doc t : temp) {
                resIn.add(t.getId());
            }
        }
        if (!nothave.equals("")) {
            List<Doc> tempDoc = search(type, "", nothave, "").getData();
            List<String> tempString = new ArrayList<>();
            for (Doc t : tempDoc) {
                tempString.add(t.getId());
            }
            res.removeAll(tempString);
        }
        if (!resIn.isEmpty()) {
            if (res.isEmpty()) {
                return Response.success(docService.getByIds(resIn));
            }
            res.retainAll(resIn);
            List<String> finalIds = new ArrayList<>();
            for (String r : res) {
                finalIds.add(r);
            }
            return Response.success(docService.getByIds(finalIds));
        }
        return Response.success(docService.getByIds(res));
    }

}
