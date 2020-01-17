package com.ir.site.dao;

import com.ir.site.entity.Doc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lcl
 * @since 2019-11-23
 */
public interface DocMapper extends BaseMapper<Doc> {
    void setPassage(Doc doc);
    List<Doc> getAll();
    Doc getById(String id);
}
