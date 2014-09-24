package java8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Test {
	
	public static void main(String[] arge) throws Exception {
		// fixDimensionDBAndTair("D://test22.txt");
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
		    int randInt = random.nextInt(60);
		    int randInt2 = random.nextInt(60);
		    String randomString = null;
		    String randomString2 = null;
		    if (randInt < 10) {
		        randomString = "0" + randInt;
		    } else {
		        randomString = String.valueOf(randInt);
		    }
		    if (randInt2 < 10) {
                randomString2 = "0" + randInt2;
            } else {
                randomString2 = String.valueOf(randInt2);
            }
		    
		    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        String todayStart = format.format(new Date()) + " 09:"+ randomString +":"+ randomString2;// 今天结束时间
	        
	        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        System.out.println(format2.parse(todayStart));
		}
	}
	
	 public static String fixDimensionDBAndTair(String filePath) {
	        FileReader fr = null;
	        BufferedReader bw = null;
	        try {
	            File file = new File(filePath);
	            fr = new FileReader(file);
	            bw = new BufferedReader(fr);
	            String sellerIdString = null;
	            while ((sellerIdString = bw.readLine()) != null) {
	            	if (sellerIdString.contains("c.a.ccenter.imt.CachePreheatImt")) {
	            		int index = sellerIdString.indexOf('-') + 2;
	            		System.out.println(sellerIdString.substring(index));
	            	}
	            }
	        } catch (Exception e) {
	        } finally {
	            try {
	                if (fr != null) {
	                    fr.close();
	                }
	                if (bw != null) {
	                    bw.close();
	                }
	            } catch (IOException e) {
	            }
	        }
	        return "Success!";
	    }

}
