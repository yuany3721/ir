package top.yuany3721.ir.dao;

import top.yuany3721.ir.entity.Inverted;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lcl
 * @since 2022-05-15
 */
public interface InvertedMapper extends BaseMapper<Inverted> {
    void addPassage(Inverted inverted);

    List<Inverted> getPassageByWord(String word);

    List<String> getPassageNameByWord(String word);

}
