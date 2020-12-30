<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	
<%@ page 
import="com.oreilly.servlet.MultipartRequest" 
import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"
 import="java.util.*" 
 import="java.io.*"%> 
 
 <% 
 String savePath = "/Library/Tomcat/webapps/ROOT/pictures"; // 저장할 디렉토리 (절대경로)

  int sizeLimit = 5 * 1024 * 1024; 

  // 5메가까지 제한 넘어서면 예외발생 

  

  try { 
	  MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy());
	   
	   
	   } catch (Exception e) {
		    out.print(e); out.print("예외 상황 발생..! ");
		}
%>
