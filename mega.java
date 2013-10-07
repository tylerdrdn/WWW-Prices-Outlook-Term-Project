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

public class mega extends HttpServlet{

int mega_lock = 0;
private String ui = "****";
static public String[][] category1;
static public String[][] category2;
static public String[][] products2;
static public String[][] prices2;
static public ArrayList<struct> pricesarr = new ArrayList<struct>();
static public ArrayList<struct> price_name = new ArrayList<struct>();
static public String paramId1 = "";
static public String paramId2 = "";
static public String paramId3 = "";
static public String[][] new_products = null;
static public String paramValue = "";
public double price=0.0;
public static 	String [] klo =null;

//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================

public void getPrice2(String id,String id2)
{
	try{


			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new URL("http://services.e-prices.gr/getPriceOfAProductatShopWS?ui="+ui+"&shopid="+id+"&productid="+id2).openStream());
			doc.getDocumentElement().normalize();
			NodeList k = doc.getElementsByTagName("product");
			
			prices2 = new String[k.getLength()][2]; //array of shops with id and name 
			
			for(int i = 0; i<k.getLength(); i++){
				
				Node m = k.item(i);
				Element element = (Element) m;
				NodeList nodelist;
				Element element1;
				NodeList fstNm;
			
				nodelist = element.getElementsByTagName("productid");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();

				prices2[i][0]=(fstNm.item(0)).getNodeValue();
			
				nodelist = element.getElementsByTagName("price");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
			
				prices2[i][1]=(fstNm.item(0)).getNodeValue();//URLEncoder.encode((fstNm.item(0)).getNodeValue(), "UTF-8");

				struct a = new struct(prices2[i][1],prices2[i][0]);
				pricesarr.add(a);
			}
	}
	catch (Exception ex){
			
		System.out.println(ex);
			
	}

}

public void getCategories2(String id)
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
			
			category2 = new String[k.getLength()][2]; //array of shops with id and name 
			
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
						
								category2[i-lock-lock2-lock3-lock4][0]=(fstNm.item(0)).getNodeValue();
								nodelist = element.getElementsByTagName("name");
								element1 = (Element) nodelist.item(0);
								fstNm = element1.getChildNodes();
								String valueISO = fstNm.item(0).getNodeValue();
								category2[i-lock-lock2-lock3-lock4][1]=valueISO;
							
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

public void getProd2(String id)
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
			
			products2 = new String[k.getLength()][2]; //array of shops with id and name 
			
			for(int i = 0; i<k.getLength(); i++){
				
				Node m = k.item(i);
				Element element = (Element) m;
				NodeList nodelist;
				Element element1;
				NodeList fstNm;
			
				nodelist = element.getElementsByTagName("id");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();

				products2[i][0]=(fstNm.item(0)).getNodeValue();
			
				nodelist = element.getElementsByTagName("name");
				element1 = (Element) nodelist.item(0);
				fstNm = element1.getChildNodes();
						String valueISO = fstNm.item(0).getNodeValue();//new String((fstNm.item(0)).getNodeValue().getBytes("UTF-8"), "UTF-8");
				products2[i][1]=valueISO;
		   
			}
	}
	catch (Exception ex){
			
		System.out.println(ex);
			
	}

}

//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================

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
			
			category1 = new String[k.getLength()][2]; //array of shops with id and name 
			
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
						
								category1[i-lock-lock2-lock3-lock4][0]=(fstNm.item(0)).getNodeValue();
			
									nodelist = element.getElementsByTagName("name");
									element1 = (Element) nodelist.item(0);
									fstNm = element1.getChildNodes();
									String valueISO = (fstNm.item(0)).getNodeValue();
									category1[i-lock-lock2-lock3-lock4][1]=valueISO;
					
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

//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================

public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
	
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
	PrintWriter out = response.getWriter();
	Enumeration ids1 = request.getParameterNames();
	
	while(ids1.hasMoreElements()){
      
			paramId2 = (String)ids1.nextElement();
			//out.println(paramId);
			
			if(paramId2.equals("button")){
				
				mega_lock=3; 
			}
			else if (paramId2.equals("delete")){ mega_lock=3; }
	
	}
	
	if(mega_lock == 1){
	
		price_name.clear();
		price=0.0;
		pricesarr.clear();
		klo =null;
		new_products =null;
		Enumeration ids = request.getParameterNames();

		while(ids.hasMoreElements()){
	
			paramId1 =(String)ids.nextElement();
			//out.println(paramId);
		}
	
		getCategories(paramId1);
		//out.println(mega_lock);

	}

//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================	

	
	if(mega_lock == 2){
	
		String temp = "";
		String temp_id = "k";
		pricesarr.clear();
		Enumeration ids = request.getParameterNames();
	    
		while(ids.hasMoreElements()){
      
			paramId2 = (String)ids.nextElement();
			//out.println(paramId);
			
			if(!paramId2.equals("button")){ out.println(paramId2); getCategories2(paramId2); paramValue=paramId2; }
		
		}

		String[] val = request.getParameterValues(paramValue);
		 //out.println(category2.length);
		
		for(int i=0; i<val.length; i++){

			out.println(val[i]);
	        
			if(!val[i].equals("submit")){  temp=val[i];}
		
		}

		for(int i=0; i<category2.length-4; i++){

			if (category2[i][1].equals(temp)){   temp_id =  category2[i][0]; break; }

		}

		getProd2(temp_id);
		out.println(temp_id);
		
		 
		for(int i=0; i<products2.length; i++){
	           
			   getPrice2(paramValue,products2[i][0]);
			  
		
		}
		
		new_products = new String[pricesarr.size()][3];
		int count=0; 
		
		for(int i=0; i<products2.length; i++){
		
			for(int j=0; j<pricesarr.size(); j++){
		
				if(products2[i][0].equals(pricesarr.get(j).name)){ new_products[count][0] = pricesarr.get(j).name; new_products[count][1] = products2[i][1]; new_products[count][2] = pricesarr.get(j).price;  count++;}
			
			}
		}

	}
	
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================		
	
	if(mega_lock==3){
	
		
	String paramName = "";
	String temp_id = "";
	struct temp;
	
	Enumeration ids = request.getParameterNames();
	
	while(ids.hasMoreElements())
{
      paramId3 = (String)ids.nextElement();

		if(!paramId3.equals("button") && !paramId3.equals("delete") ){
		
	
		String[] val = request.getParameterValues(paramId3);
		temp = new struct(paramId3, val[0]);
		price_name.add(temp);
	
		price += Double.parseDouble(paramId3);
 
	   }else if(paramId3.equals("delete")){   
	   
	   klo = new String[price_name.size()];
		price_name.clear();
		price=0.0;
	   
	   }
	}
		klo = new String[price_name.size()];


	for(int i=0; i<price_name.size(); i++){ klo[i]= price_name.get(i).name; }

	
	}

//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================		
	
		request.setAttribute("price",price);
		request.setAttribute("klo", klo);
		request.setAttribute("meg",mega_lock);
		request.setAttribute("shop_id2",paramValue);
		request.setAttribute("products2", new_products);
		request.setAttribute("shop_id1",paramId1);
		request.setAttribute("category1", category1);
		request.getRequestDispatcher("/category_list.jsp").forward(request, response);
		
		
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================
//====================================================================================================================================================	

	
}
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    mega_lock =(int) request.getSession().getAttribute("mega_lock");
    doPost(request,response);

}
}
