package top.yuany3721.ir.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author lcl
 * @since 2022-05-15
 */
@TableName("doc")
public class Doc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文献id（自动生成）
     */
    private String id;

    /**
     * 刊物
     */
    private String publication;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者List to String
     */
    private String author;

    /**
     * 检索词
     */
    private String word;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getAuthors() {
        List<String> res = new ArrayList<>();
        char temp[] = this.author.toCharArray();
        StringBuilder author = new StringBuilder();
        for (int i = 0; i < this.author.length(); i++) {
            if (temp[i] == '[' || temp[i] == ' ') {
                continue;
            }
            if (temp[i] == ']') {
                res.add(author.toString());
                break;
            }
            if (temp[i] == ',') {
                res.add(author.toString());
                author.delete(0, author.length());
                continue;
            }
            author.append(temp[i]);
        }
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Doc))
            return false;
        if (obj == this)
            return true;
        return this.id.equals(((Doc) obj).id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (publication != null ? publication.hashCode() : 0);
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Doc{" +
                "id=" + id +
                ", publication=" + publication +
                ", title=" + title +
                ", author=" + author +
                ", word=" + word +
                "}";
    }
}
