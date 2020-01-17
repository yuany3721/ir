package com.ir.site.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ir.site.entity.Doc;

import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lcl
 * @since 2019-11-23
 */
public interface DocService extends IService<Doc> {
    void refresh(File filename);

    List<Doc> getAll();

    List<String> titleRetrieval(List<String> value);
    List<String> authorRetrieval(List<String> value);
    List<String> passageRetrieval(List<String> value);

    List<String> titleHave(String value);
    List<String> authorHave(String value);
    List<String> passageHave(String value);

    List<Doc> getByIds(List<String> ids);
}
