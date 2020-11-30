import com.cao.youth.util.CommonUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author 曹学习
 * @description test
 * @date 2020/8/24 12:03
 */

public class test {
    public static void main(String[] args) {
        BigDecimal prePrice=new BigDecimal("100.25");
        String price= CommonUtil.yuanToFenPlainString(prePrice);
        String s=null;
        if(s==null){
            System.out.println(price);
        }
    }
}
