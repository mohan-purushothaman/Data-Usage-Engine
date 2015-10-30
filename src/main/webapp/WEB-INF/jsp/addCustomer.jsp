<%-- 
    Document   : addCustomer
    Created on : Oct 30, 2015, 3:59:01 AM
    Author     : Administrator
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

      <table >
          <tr>
              <td>Cust ID:             
              </td>
              <td><input id="custId" value="" type="text"/>             
              </td>
          </tr>
          <tr>
              <td>BillCycle:             
              </td>
              <td><input id="billCycle" value="" type="text"/>             
              </td>
          </tr>
          <tr>
              <td>Email:            
              </td>
              <td><input id="email" value="" type="text"/>             
              </td>
          </tr>
          <tr>
              <td>TN:             
              </td>
              <td><input id="tn" value="" type="text"/>             
              </td>
          </tr>
      </table>
  <button id="addCustomer">Add Customer Details </button>
  
  
<script>
    $(function () {
        $('#addCustomer').button().click(function () {
            
            //var custId=$('#custId').val();
            //"processing": true,
               // "ajax": '<c:url value="/saveCustomer" />' + "?custId="+$('#custId').val().replace('\n','#'),
        });
            });
   </script>
