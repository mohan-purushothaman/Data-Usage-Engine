<%-- 
    Document   : home
    Created on : 17 Oct, 2015, 9:26:23 PM
    Author     : Mohan Purushothaman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Data Usage Engine</title>
    </head>
    <body>
         <form method="POST" enctype="multipart/form-data"
		action="/upload">
		File to upload: <input type="file" name="file"><br /> Name: <input
			type="text" name="name"><br /> <br /> <input type="submit"
			value="Upload"> Press here to upload the file!
	</form>
    </body>
    
   