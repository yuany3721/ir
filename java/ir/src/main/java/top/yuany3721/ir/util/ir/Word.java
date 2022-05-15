package top.yuany3721.ir.util.ir;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Word {
    // 检索词
    String word;
    // 绝对词频
    Integer times = 0;
    // 当篇文章权重
    BigDecimal weight = new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP);

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Word))
            return false;
        if (obj == this)
            return true;
        return this.word == ((Word) obj).word;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (times != null ? times.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "{" + word + "," + times + "," + weight.toString() + "}";
    }
}
