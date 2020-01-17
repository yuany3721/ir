package com.ir.site.dao;

import com.ir.site.entity.TitleInverted;
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
public interface TitleInvertedMapper extends BaseMapper<TitleInverted> {
    void addPassage(@Param("word") String word, @Param("passage") String passage);
    List<String> getPassageByWord(String word);
}
