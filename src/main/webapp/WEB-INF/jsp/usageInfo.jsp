<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="mainDiv" >
    <div id="baseDiv">
    <div id="leftDiv">
        <textarea id="customerId"  rows="10" placeholder="Enter customer id's to check usage "></textarea>
        <button id="addCustomer">Add Customer</button>
    </div>
    <div id="rightDiv">
        <h2>Add Customer</h2>
    </div>
    </div>
</div>

<script>
    $(function () {

        $('#addCustomer').button().click(function () {

            var div = $('#rightDiv').html("").append("<h3>Loading data for</h3>");
            var custList = $('#customerId').val().split('\n');
            for (var s in custList){
                div.append("<span>" + custList[s] + "</span");
            }
            
            
        });
    });
</script>

