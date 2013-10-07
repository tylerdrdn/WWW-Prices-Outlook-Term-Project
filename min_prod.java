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

public class min_prod extends HttpServlet{


private String ui = "****";
static public String[][] category;
static public String[][] products;
static public String[][] prices;
static public ArrayList<struct> pricesarr = new ArrayList<struct>();
static public ArrayList<struct2> pricenam = new ArrayList<struct2>();
static public ArrayList<String> tmp1 = new ArrayList<String>();
static public ArrayList<String> tmp2 = new ArrayList<String>();
static public ArrayList<String> tmp3 = new ArrayList<String>();

public String fff = new String();
public String[][] shops;

public void getShopsOfAnArea()
{
	try{

	
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
				String valueISO = fstNm.item(0).getNodeValue();
				shops[i][1]=valueISO;
		   
			}
	}
	catch (Exception ex){
			
		System.out.println(ex);
			
	}

}

public void getMinPrice(String id)
{
	try{


			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new URL("http://services.e-prices.gr/getMinimumPriceOfACategoryAtPlaceWS?ui="+ui+"&categoryid="+id+"&areaid=704").openStream());
			doc.getDocumentElement().normalize();
			NodeList k = doc.getElementsByTagName("product");
			
		
			
			for(int i = 0; i<k.getLength(); i++){
				
				Node m = k.item(i);
				Element element = (Element) m;
				NodeList nodelist;
				Element element1;
				NodeList fstNm;
			    
				nodelist = element.getElementsByTagName("shop");
				String[][] temp = new String[nodelist.getLength()][2];
				
				for(int j=0; j<nodelist.getLength(); j++){
				element1 = (Element) nodelist.item(j);
				temp[j][0] = element1.getAttribute("shopid");
				temp[j][1] = element1.getAttribute("minPrice");
				}
				struct2 tmp = new struct2(element.getAttribute("productid"),temp);
				pricenam.add(tmp);
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

	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
	String paramId = "";
	String paramValue = "";
	String temp = "";
	String temp_id = "";
	String[][] new_products;
	Object[] name_pro;
	Object[] min_pri;
	Object[] name_shop;
	int lock=0;;
	pricenam.clear();
	tmp1.clear();
	tmp2.clear();
	tmp3.clear();
	PrintWriter out = response.getWriter();
	Enumeration ids = request.getParameterNames();
	
	while(ids.hasMoreElements())
	{
      paramId =(String)ids.nextElement();

	 String[] val = request.getParameterValues(paramId);
	 out.println(val[0]);
	 getMinPrice(val[0]); 
	 getProd(val[0]); //products[][0]-->id
	 getShopsOfAnArea(); //shops[][0]-->id
	 
	}

	for(int i=0; i<pricenam.size(); i++){

		for(int kar=0; kar<products.length; kar++){
  
			if(products[kar][0].equals(pricenam.get(i).id) ){  lock=kar;  }
  
		}
 
		for(int j=0; j<pricenam.get(i).price_name.length; j++){
 
			for(int kar2=0; kar2<shops.length; kar2++){
	
				if (shops[kar2][0].equals(pricenam.get(i).price_name[j][0])){ tmp1.add(products[lock][1]); tmp2.add(shops[kar2][1]);}
	
			}
	
			tmp3.add(pricenam.get(i).price_name[j][1]);
	
		}

	}
	out.println(tmp1.size());
	out.println(tmp2.size());
	out.println(tmp3.size());
	name_pro = tmp1.toArray();
	min_pri = tmp3.toArray();
	name_shop = tmp2.toArray();

 	request.setAttribute("name_pro",name_pro);
	request.setAttribute("min_pri", min_pri);
	request.setAttribute("name_shop", name_shop);
	request.getRequestDispatcher("/mini_list.jsp").forward(request, response);

}

}
