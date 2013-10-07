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
import org.apache.commons.io.IOUtils;
import org.w3c.dom.*;

public class getShopsOfAnArea extends HttpServlet{


private String ui = "****";
public String[][] shops;

public void getShopsOfAnArea()
{
	try{

	
			File f;
			f=new File("/usr/share/apache-tomcat-7.0.32/webapps/super/data/eleos10.xml");
			f.createNewFile();
			FileWriter fstream = new FileWriter("/usr/share/apache-tomcat-7.0.32/webapps/super/data/eleos10.xml");
			BufferedWriter out1 = new BufferedWriter(fstream);
	
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new URL("http://services.e-prices.gr/getShopsOfAnAreaWS?ui="+ui+"&areaid=704").openStream());
			
			doc.getDocumentElement().normalize();
			NodeList k = doc.getElementsByTagName("shop");
			
			shops = new String[k.getLength()][2]; //array of shops with id and name 
			
			for(int i = 0; i<k.getLength(); i++){
				
				Node m = k.item(i);
				Element element = (Element) m;
				NodeList nodelist;
				Element element1;
				NodeList fstNm;
			
				nodelist = element.getElementsByTagName("id");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
			
				shops[i][0]=(fstNm.item(0)).getNodeValue();
			
				nodelist = element.getElementsByTagName("name");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
		
				out1.write(URLEncoder.encode((fstNm.item(0)).getNodeValue(), "UTF-8")+"\n");
				
				String valueISO = fstNm.item(0).getNodeValue();
				shops[i][1]=valueISO;
		   
			}out1.close();
	}
	catch (Exception ex){
			
		System.out.println(ex);
			
	}

}


public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
	
	response.setCharacterEncoding("UTF-8");
	PrintWriter out = response.getWriter();
	String act = request.getParameter("act");

	if (act.equals("list")){
		
		getShopsOfAnArea();	
		
		try{

			request.setAttribute("shops", shops);
			request.getRequestDispatcher("/supermarkets.jsp").forward(request, response);
			
		}
		catch(Exception e){}
			
	}
}

}
