package top.yuany3721.ir.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lcl
 * @since 2022-05-15
 */
@TableName("author")
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文献id
     */
    private String passage;

    /**
     * author
     */
    private String author;


    public String getPassage() {
        return passage;
    }

    public void setPassage(String passage) {
        this.passage = passage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Author{" +
        "passage=" + passage +
        ", author=" + author +
        "}";
    }
}
