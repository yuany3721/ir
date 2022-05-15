package top.yuany3721.ir.dao;

import top.yuany3721.ir.entity.TitleInverted;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lcl
 * @since 2022-05-15
 */
public interface TitleInvertedMapper extends BaseMapper<TitleInverted> {
    void addPassage(@Param("word") String word, @Param("passage") String passage);

    List<String> getPassageByWord(String word);

}
