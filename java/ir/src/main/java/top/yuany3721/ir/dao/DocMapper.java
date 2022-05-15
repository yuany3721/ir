package top.yuany3721.ir.dao;

import top.yuany3721.ir.entity.Doc;

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
public interface DocMapper extends BaseMapper<Doc> {

    void setPassage(Doc doc);

    List<Doc> getAll();

    Doc getById(String id);
}
