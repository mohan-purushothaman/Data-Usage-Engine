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

    <div id="usages" >
        <div id="hourUsage">
            <table id="usageInfoTable" class="display" cellspacing="0" width="100%"  style="display:none">
                <thead>
                <th>CustId</th>
                <th>Today Hour usage</th>
                <th>This Month Day Usage</th>
                <th>Total Monthly Usage</th>
                </thead> 
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
    $(function () {



        $('#addCustomer').button().click(function () {

            var div = $('#rightDiv').html("").append("<h3>Loading data for</h3>");
            var custList = $('#customerId').val().split('\n');
            for (var s in custList) {
                div.append("<span>" + custList[s] + "</span");
            }


            $('#usageInfoTable').show().DataTable({
                "processing": true,
                "ajax": '<c:url value="/usageData" />' + "?customerIds="+$('#customerId').val().replace('\n','#'),
                "columns": [
                    {"data": "customerId"},
                    {"data": function (row, type, val, meta) {
                            var d = "";
                            for (var hour in row.hourUsage)
                            {
                                var usage=row.hourUsage[hour].usage;
                                if(usage!==0)
                                d +="<p>"+ hour + ":00 - " + ((parseInt(hour) + 1) + ":00  -  " )+usage +" Bytes</p>";
                            }
                            return d;
                        }},
                    
                    {"data": function (row, type, val, meta) {
                            var d = "";
                            for (var day in row.dayUsage)
                            {
                                var usage=row.dayUsage[day].usage;
                                if(usage!==0)
                                d +="<p> Day "+((parseInt(day) + 1)  )+"  -  "+usage +" Bytes</p>";
                            }
                            return d;
                        }},
                    {"data": "monthUsage"}

                ]
            });

            /**$.post(,
             function (data) {
             
             
             
             var svg = dimple.newSvg("#hourUsage", 600, 400);
             console.log(JSON.stringify(d3.nest()
             .key(function (d) {
             return d.customerId;
             })
             .rollup(function (d) {
             console.log(JSON.stringify(d));
             
             var json = {};
             d = d[0].hourUsage;
             for (d1 in d) {
             json['hour' + d1] = d[d1].usage;
             }
             return json;
             })
             .map(data.data)));
             
             data = dimple.filterData(data, "", ["Aperture", "Black Mesa"])
             var myChart = new dimple.chart(svg, data);
             myChart.setBounds(60, 30, 505, 305);
             var x = myChart.addCategoryAxis("x", "Month");
             x.addOrderRule("Date");
             myChart.addMeasureAxis("y", "Unit Sales");
             myChart.addSeries("Channel", dimple.plot.line);
             myChart.addLegend(60, 10, 500, 20, "right");
             myChart.draw();
             */
        });
    });

</script>

