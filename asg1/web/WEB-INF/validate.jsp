
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<% List errors = (List) request.getAttribute("errors"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Client information</title>
    </head>
    <body>
        <% 
        if (errors.isEmpty()) { 
        %>
        <h1>Client Information is valid</h1>
        <%
        } else { 
        %>
        <h1>Client Information is invalid</h1>
        <% 
            for (Object error : errors) { 
        %>
            <h2>Error !!!</h2>
            <p><%= error.toString() %></p>
        <% 
            }
          }
        %>
    </body>
</html>
