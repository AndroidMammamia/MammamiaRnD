<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String addrTag = request.getParameter("addrTag");
	String addrName = request.getParameter("addrName");
	String addrTel = request.getParameter("addrTel");
	String addrAddr = request.getParameter("addrAddr");
	String addrDetail = request.getParameter("addrDetail");	
    String addrNo = request.getParameter("addrNo");
	String addrImagePath = request.getParameter("addrImagePath");
		
//------
	String url_mysql = "jdbc:mysql://database-2.cotrd7tmeavd.ap-northeast-2.rds.amazonaws.com/mammamia?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into addrlist (addrNo,addrName, addrTel, addrAddr, addrDetail, addrTag, addrImagePath";
	    String B = ") values (?,?,?,?,?,?,?)";
	
	    ps = conn_mysql.prepareStatement(A+B);
        ps.setString(1, addrNo);
	    ps.setString(2, addrName);
		ps.setString(3, addrTel);
		ps.setString(4, addrAddr);
		ps.setString(5, addrDetail);
	    ps.setString(6, addrTag);
	    ps.setString(7, addrImagePath);
	    
	    ps.executeUpdate();
	
	    conn_mysql.close();
	} 
	
	catch (Exception e){
	    e.printStackTrace();
	}
	
%>

