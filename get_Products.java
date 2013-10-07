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

public class get_Products extends HttpServlet{


private String ui = "****";
static public String[][] category;
static public String[][] products;
static public String[][] prices;
static public ArrayList<struct> pricesarr = new ArrayList<struct>();



public void getPrice(String id,String id2)
{
	try{


			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new URL("http://services.e-prices.gr/getPriceOfAProductatShopWS?ui="+ui+"&shopid="+id+"&productid="+id2).openStream());
			doc.getDocumentElement().normalize();
			NodeList k = doc.getElementsByTagName("product");
			
			prices = new String[k.getLength()][2]; //array of shops with id and name 
			
			for(int i = 0; i<k.getLength(); i++){
				
				Node m = k.item(i);
				Element element = (Element) m;
				NodeList nodelist;
				Element element1;
				NodeList fstNm;
			
				nodelist = element.getElementsByTagName("productid");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();

				
				prices[i][0]=(fstNm.item(0)).getNodeValue();
				nodelist = element.getElementsByTagName("price");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
				prices[i][1]=(fstNm.item(0)).getNodeValue();

				struct a = new struct(prices[i][1],prices[i][0]);
				pricesarr.add(a);
			
			}
	}
	catch (Exception ex){
			
			System.out.println(ex);
			
	}

}

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
								String valueISO = new String((fstNm.item(0)).getNodeValue().getBytes("UTF-8"), "ISO-8859-1");
								category[i-lock-lock2-lock3-lock4][1]=valueISO;
						
							}else{ lock4 = 1;}
					
						}else{ lock3 = 1;}
					
					}else{ lock2 = 1;}
				
				}else{lock=1;}
		   
			}
	}
	catch (Exception ex){
			
		System.out.println(ex);
			
	}

}

public void getProd(String id)
{
	try{

            int lock=0;
			int lock2=0;
			int lock3=0;
			int lock4=0;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new URL("http://services.e-prices.gr/getProductsOfACategoryWS?ui="+ui+"&categoryid="+id).openStream());
			doc.getDocumentElement().normalize();
			NodeList k = doc.getElementsByTagName("product");
			
			products = new String[k.getLength()][2]; //array of shops with id and name 
			
			for(int i = 0; i<k.getLength(); i++){
				
				Node m = k.item(i);
				Element element = (Element) m;
				NodeList nodelist;
				Element element1;
				NodeList fstNm;
			
				nodelist = element.getElementsByTagName("id");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
				
				products[i][0]=(fstNm.item(0)).getNodeValue();
			
				nodelist = element.getElementsByTagName("name");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
				
				String valueISO = fstNm.item(0).getNodeValue();
				products[i][1]=valueISO;
		   
			}
	}
	catch (Exception ex){
			
		System.out.println(ex);
			
	}

}


public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{

	String paramId = "";
	String paramValue = "";
	String temp = "";
	String temp_id = "";
	String[][] new_products;
	pricesarr.clear();
	PrintWriter out = response.getWriter();
	Enumeration ids = request.getParameterNames();
	
	while(ids.hasMoreElements()){
	
		paramId =(String)ids.nextElement();
		//out.println(paramId);
		if(!paramId.equals("button")){  getCategories(paramId); paramValue=paramId; }
	}

	String[] val = request.getParameterValues(paramValue);
	for(int i=0; i<val.length; i++){

		//out.println(val[i]);
		if(!val[i].equals("submit")){  temp=val[i];   }
	}


	for(int i=0; i<category.length; i++){

		if (category[i][1].equals(temp)){   temp_id =  category[i][0]; break; }

	}

	getProd(temp_id);
		 
	for(int i=0; i<products.length; i++){
	
		getPrice(paramValue,products[i][0]);
		//out.println(i+") "+products[i][0]);
		
	}
	
	new_products = new String[pricesarr.size()][3];
		
	int count=0; 
	for(int i=0; i<products.length; i++){
		
		for(int j=0; j<pricesarr.size(); j++){
		
			if(products[i][0].equals(pricesarr.get(j).name)){ new_products[count][0] = pricesarr.get(j).name; new_products[count][1] = products[i][1]; new_products[count][2] = pricesarr.get(j).price;  count++;}
		
		}
	}
	
	request.setAttribute("shop_id",paramValue);
	request.setAttribute("products", new_products);
	request.getRequestDispatcher("/product_list.jsp").forward(request, response);

}

}
