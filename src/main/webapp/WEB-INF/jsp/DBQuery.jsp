<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Execute query</title>
</head>
<body>
<form action="/executeQuery" id="usrform">
 Query: <textarea rows="8" cols="50" name="querytext" form = "queryfrom">
Enter query here...</textarea>
  <input type="submit">
</form>
  
</body>
</html>
