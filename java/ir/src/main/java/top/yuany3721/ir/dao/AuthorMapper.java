package top.yuany3721.ir.dao;

import top.yuany3721.ir.entity.Author;

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
public interface AuthorMapper extends BaseMapper<Author> {
    void addPassage(@Param("passage") String passage, @Param("author") String author);

    List<String> getPassageByAuthor(String author);

}
