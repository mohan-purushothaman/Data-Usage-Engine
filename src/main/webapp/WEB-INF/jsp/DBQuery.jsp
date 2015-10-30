<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <form action=<c:url value="/executeQuery"/> id="queryfrom">
 Query: <textarea rows="8" cols="50" name="querytext" form = "queryfrom">
Enter query here...</textarea>
  <input type="submit">
</form>
  

