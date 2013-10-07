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


public class list extends HttpServlet{


private String ui = "****";
static public String[][] category;
static public String[][] products;
static public String[][] prices;
static public ArrayList<struct> price_name = new ArrayList<struct>();
public String fff = new String();
public double price=0.0;


public void getPrice(String id,String id2)
{
	try{

	}
	catch (Exception ex){
			
		System.out.println(ex);
			
	}

}




public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{

	String paramId = "";
	String paramName = "";
	String temp_id = "";
	struct temp;
	String [] klo;

	PrintWriter out = response.getWriter();
	Enumeration ids = request.getParameterNames();
	
	while(ids.hasMoreElements())
	{
		paramId =	(String)ids.nextElement();
	
		if(!paramId.equals("button") && !paramId.equals("delete") ){
    
				String[] val = request.getParameterValues(paramId);
				temp = new struct(paramId, val[0]);
				price_name.add(temp);
				price += Double.parseDouble(paramId);
 
		}
		else if(paramId.equals("delete")){   
	   
			price_name.clear();
			price=0.0;
	   
		}
	}

	klo = new String[price_name.size()];


	for(int i=0; i<price_name.size(); i++){ klo[i]= price_name.get(i).name; }

	request.setAttribute("price",price);
	request.setAttribute("klo", klo);
	request.getRequestDispatcher("/list.jsp").forward(request, response);

}

}
