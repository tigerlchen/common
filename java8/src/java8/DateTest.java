package java8;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateTest {
    
    public static void main(String[] args) throws ParseException {
        String todayStart = "2099-12-31 23:59:59";// 今天结束时间
        
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format2.parse(todayStart).getTime());
        
        System.out.println(new Date(1411434247000L));
    }

}
