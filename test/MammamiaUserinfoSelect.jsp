<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	String userinfoId= request.getParameter("userinfoId");
	String userinfoPw= request.getParameter("userinfoPw");
	String url_mysql = "jdbc:mysql://localhost/mammamia?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select userinfoId, userinfoPw, userinfoAddr, userinfoTel from userinfo where userinfoId = ?";
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	
    int count = 0;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
		ps = conn_mysql.prepareStatement(WhereDefault);
		ps.setString(1,userinfoId);// 
        rs = ps.executeQuery(); 
%>
		{ 
  			"userinfo"  : [ 
<%
        while (rs.next()) {
            if (count == 0) {

            }else{
%>
            , 
<%
            }
%>            
			{
			"userinfoId" : "<%=rs.getString(1) %>", 
			"userinfoPw" : "<%=rs.getString(2) %>",   
			"userinfoAddr" : "<%=rs.getString(3) %>",  
			"userinfoTel" : "<%=rs.getString(4) %>"
			}

<%		
        count++;
        }
%>
		  ] 
		} 
<%		
        conn_mysql.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
	
%>
