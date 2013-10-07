import java.io.*;
import java.util.*;
import java.net.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Image.*;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class getCat_getProd extends HttpServlet{


private String ui = "****";
static public String[][] category;
static public String[][] products;


public void getCategories(String id)
{
	try{

            int lock=0;
			int lock2=0;
			int lock3=0;
			int lock4=0;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new URL("http://services.e-prices.gr/getCategoriesWS?ui="+ui+"&shop_id="+id).openStream());
			doc.getDocumentElement().normalize();
			NodeList k = doc.getElementsByTagName("category");
			
			category = new String[k.getLength()][2]; //array of shops with id and name 
			
			for(int i = 0; i<k.getLength(); i++){
				
				Node m = k.item(i);
				Element element = (Element) m;
				NodeList nodelist;
				Element element1;
				NodeList fstNm;
			
				nodelist = element.getElementsByTagName("categoryid");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
			    if(!(fstNm.item(0)).getNodeValue().equals("302") || lock == 1){
				 
					if(!(fstNm.item(0)).getNodeValue().equals("306")){
					
						if(!(fstNm.item(0)).getNodeValue().equals("985")|| lock3 == 1){
					
							if(!(fstNm.item(0)).getNodeValue().equals("986") || lock4 == 1){
						
								category[i-lock-lock2-lock3-lock4][0]=(fstNm.item(0)).getNodeValue();
			
								nodelist = element.getElementsByTagName("name");
								element1 = (Element) nodelist.item(0);
								fstNm = element1.getChildNodes();
						
								String valueISO = fstNm.item(0).getNodeValue();
								
								category[i-lock-lock2-lock3-lock4][1]=valueISO;//URLEncoder.encode((fstNm.item(0)).getNodeValue(), "UTF-8");
						
							else{ lock4 = 1;}
					
						}else{ lock3 = 1;}
					
					}else{ lock2 = 1;}
				
				}else{lock=1;}

			}
	}
	catch (Exception ex){
			
		System.out.println(ex);
			
	}

}

public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
	
	String paramId = "";
	PrintWriter out = response.getWriter();
	Enumeration ids = request.getParameterNames();
	int counter=0;	
	
	while(ids.hasMoreElements())
	{
		paramId =(String)ids.nextElement();
		//out.println(paramId);
	}
	
	getCategories(paramId);
	
	request.setAttribute("shop_id",paramId);
	request.setAttribute("category", category);
	request.getRequestDispatcher("/category_list.jsp").forward(request, response);

}

}
