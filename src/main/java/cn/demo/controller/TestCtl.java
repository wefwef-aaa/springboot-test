package cn.demo.controller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("demo")
public class TestCtl {


    @RequestMapping("test")
    public String test(){
        return "success";
    }


    public static void main(String [] args)     {
        List<User> users = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File("C:\\Users\\ASUS.LAPTOP-2CFRIB31\\Desktop\\test.xml")); // 读取XML文件,获得document对象
            Element root = document.getRootElement();
            // 遍历根节点下的子节点
            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
                Element element = it.next();
                // do something
                User user = new User();
                String s = element.attributeValue("id");
                String s1 = element.elementText("name");
                String s2 = element.elementText("date");
                user.setId(s);
                user.setName(s1);
                user.setDate(sdf.parse(s2));
                users.add(user);
            }

            Document document2 = DocumentHelper.createDocument();
            Element root2 = document2.addElement("dataList");
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                Element user1 = root2
                        .addElement("user")
                        .addAttribute("id", user.getId() + i);
                user1.addElement("name").addText( user.getName());
                user1.addElement("date").addText(sdf.format(user.getDate()));
            }

            FileWriter out = new FileWriter("C:\\Users\\ASUS.LAPTOP-2CFRIB31\\Desktop\\test1.xml");
            document2.write(out);
            out.close();

        } catch (DocumentException | ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    }


