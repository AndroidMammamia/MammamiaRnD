<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>        
<%
	request.setCharacterEncoding("utf-8");
	String userinfoId = request.getParameter("userinfoId");
	String userinfoPw = request.getParameter("userinfoPw");
	String userinfoAddr = request.getParameter("userinfoAddr");
	String userinfoTel = request.getParameter("userinfoTel");	
		
//------
	String url_mysql = "jdbc:mysql://localhost/mammamia?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
	String id_mysql = "root";
	String pw_mysql = "qwer1234";

	PreparedStatement ps = null;
	try{
	    Class.forName("com.mysql.jdbc.Driver");
	    Connection conn_mysql = DriverManager.getConnection(url_mysql,id_mysql,pw_mysql);
	    Statement stmt_mysql = conn_mysql.createStatement();
	
	    String A = "insert into userinfo (userinfoId, userinfoPw, userinfoAddr, userinfoTel";
	    String B = ") values (?,?,?,?)";
	
	    ps = conn_mysql.prepareStatement(A+B);
	    ps.setString(1, userinfoId);
	    ps.setString(2, userinfoPw);
	    ps.setString(3, userinfoAddr);
	    ps.setString(4, userinfoTel);
	    
	    ps.executeUpdate();
	
	    conn_mysql.close();
	} 
	
	catch (Exception e){
	    e.printStackTrace();
	}
	
%>

