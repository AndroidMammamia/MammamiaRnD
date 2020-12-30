	
    <%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String stSearch =  request.getParameter("addrName");
	String url_mysql = "jdbc:mysql://database-2.cotrd7tmeavd.ap-northeast-2.rds.amazonaws.com/mammamia?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
 	String id_mysql = "root";
 	String pw_mysql = "qwer1234";
    String WhereDefault = "select addrNo, addrName, addrTel, addrAddr,addrDetail,addrLike,addrTag,addrImagePath from addrlist Where addrName like '%"+stSearch+"%' or addrTel like '%" +stSearch+"%' or addrTag like '%" +stSearch+"%'";
    int count = 0;
     PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
       Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn_mysql = DriverManager.getConnection(url_mysql, id_mysql, pw_mysql);
        Statement stmt_mysql = conn_mysql.createStatement();
        ps = conn_mysql.prepareStatement(WhereDefault);
        rs = ps.executeQuery();  
%>
		{ 
  			"addrlist"  : [ 
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
			"addrNo" : "<%=rs.getInt(1) %>", 
			"addrName" : "<%=rs.getString(2) %>",   
			"addrTel" : "<%=rs.getString(3) %>",  
			"addrAddr" : "<%=rs.getString(4) %>",
            "addrDetail" : "<%=rs.getString(5) %>",
            "addrLike ": "<%=rs.getString(6) %>",
            "addrTag" : "<%=rs.getString(7) %>",
            "addrImagePath" :"<%=rs.getString(8) %>"
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