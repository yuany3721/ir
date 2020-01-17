package com.ir.site.dao;

import com.ir.site.entity.Author;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lcl
 * @since 2019-11-23
 */
public interface AuthorMapper extends BaseMapper<Author> {
    void addPassage(@Param("passage") String passage, @Param("author") String author);
    List<String> getPassageByAuthor(String author);
}
