package com.ir.site.dao;

import com.ir.site.entity.Inverted;
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
public interface InvertedMapper extends BaseMapper<Inverted> {
    void addPassage(Inverted inverted);
    List<Inverted> getPassageByWord(String word);
    List<String> getPassageNameByWord(String word);
}
