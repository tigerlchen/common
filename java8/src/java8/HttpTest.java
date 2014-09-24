package java8;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class HttpTest {
    
    public static void main(String[] args) throws Exception {
        
        File writeFile = new File("D://coupon_gather.txt");
        if (!writeFile.exists()) {
            writeFile.createNewFile();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(writeFile));
        test(out);
        
        out.flush();
        out.close();
    }

    
    
     public static void test(BufferedWriter out) throws Exception  
    {  
        // step 1: 获得dom解析器工厂（工作的作用是用于创建具体的解析器）  
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
          
//      System.out.println("class name: " + dbf.getClass().getName());  
          
        // step 2:获得具体的dom解析器  
        DocumentBuilder db = dbf.newDocumentBuilder();  
          
//      System.out.println("class name: " + db.getClass().getName());  
          
        // step3: 解析一个xml文档，获得Document对象（根结点）  
        
        Document document = db.parse("http://10.232.22.180:8010/select/?q=businessUnit:2&cf.not.in=status:(-1)&start=0&rows=600&cf.in=couponType:(0,1,8)&cf.range=endTime:(1325214671000%20TO%201451445071000]&sort=endTime%20desc");  
          
        NodeList list = document.getElementsByTagName("doc");  
          
        for(int i = 0; i < list.getLength(); i++)  
        {  
            String supplierId = null;
            String templateCode = null;
            String couponType = null;
            Boolean mobile = null;
            Element element = (Element)list.item(i);
            NodeList nodeList2 = element.getElementsByTagName("long");
            for (int j = 0; j < nodeList2.getLength(); j++) {
                Element element2 = (Element)nodeList2.item(j);
                if (element2.getAttribute("name").equals("templateCode")) {
                    templateCode = element2.getTextContent();
                }
                
                if (element2.getAttribute("name").equals("supplierId")) {
                    supplierId = element2.getTextContent();
                }
            }
            
            NodeList nodeList3 = element.getElementsByTagName("int");
            for (int k = 0; k < nodeList3.getLength();k++) {
                
                Element element2 = (Element)nodeList3.item(k);
                if (element2.getAttribute("name").equals("couponType")) {
                    couponType = element2.getTextContent();
                }
                
            }
            
            NodeList nodeList4 = element.getElementsByTagName("str");
            for (int k = 0; k < nodeList4.getLength();k++) {
                
                Element element2 = (Element)nodeList4.item(k);
                if (element2.getAttribute("name").equals("features")) {
                    String txt = element2.getTextContent();
                    if (txt.contains("useAt:2")) {
                        mobile = true;
                    } else {
                        mobile = false;
                    }
                }
                
            }
            Random random = new Random();
            String isMobile = mobile ? "Y":"N";
            Integer typeId = random.nextInt(6) + 1;
//            System.out.println(typeId + "," + supplierId  + "," +templateCode +"," +couponType +"," + isMobile +"\r\n");
            
            out.write(typeId + "^" + supplierId  + "^" +templateCode +"^" +couponType +"^" + isMobile +"\r\n");
        }  
    }  
}
