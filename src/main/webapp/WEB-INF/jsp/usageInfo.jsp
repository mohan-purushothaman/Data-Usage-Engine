<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="mainDiv" >
    <div id="inputDiv">
        <input id="customerId"  placeholder="Enter customer id to check usage">
        <button id="addCustomer">Load usage</button>
    </div>


    <div id="usages" >
        <div id="hourUsage">
            <!--
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
            -->
        </div>

        <div id="dayUsage"></div>
    </div>
</div>

<script>
    $(function () {



        $('#addCustomer').button().click(function () {



    <%--
                $('#usageInfoTable').show().DataTable({
                    "processing": true,
                    "destroy":true,
                    "ajax": '<c:url value="/usageData" />' + "?customerIds="+$('#customerId').val(),
                    "columns": [
                        {"data": "customerId"},
                        {"data": function (row, type, val, meta) {
                                var d = "";
                                for (var hour in row.hourUsage)
                                {
                                    var usage=row.hourUsage[hour].persistedUsage;
                                    if(usage!==0)
                                    d +="<p>"+ hour + ":00 - " + ((parseInt(hour) + 1) + ":00  -  " )+usage +" Bytes</p>";
                                }
                                return d;
                            }},
                    
                        {"data": function (row, type, val, meta) {
                                var d = "";
                                for (var day in row.dayUsage)
                                {
                                    var usage=row.dayUsage[day].persistedUsage;
                                    if(usage!==0)
                                    d +="<p> Day "+((parseInt(day) + 1)  )+"  -  "+usage +" Bytes</p>";
                                }
                                return d;
                            }},
                        {"data": "monthUsage.persistedUsage"}

                ]
            });
    --%>
            $.post('<c:url value="/usageData" />', {"customerIds": $('#customerId').val()},
            function (data) {



                var custId = data.data.customerId;

                var hourUsage = data.data[0].hourUsage;
                var dayUsage = data.data[0].dayUsage;

                var h = [];
                
                var l=data.data[0].lastUsageTime;
                var lastAccessedDate= new Date( parseInt(l.substring(0,4)),parseInt(l.substring(5,7)),parseInt(l.substring(8,10)) ,parseInt(l.substring(12,14))   );
                var lHour=lastAccessedDate.getHours();
                var index=24;
                while(index>0){
                    var i=(index+lHour)%24;
                    h[i] = {"Hourly": new Date(lastAccessedDate.getYear(),lastAccessedDate.getMonth(),lastAccessedDate.getDate(),lastAccessedDate.getHours()), "Usage": hourUsage[i].persistedUsage};
                    index--;
                    lastAccessedDate.setHours(lastAccessedDate.getHours()-1);
                }
                


                var svg = dimple.newSvg("#hourUsage", 950, 400);
                var myChart = new dimple.chart(svg, h);
                myChart.setBounds(60, 30, 800, 350)
                var x = myChart.addTimeAxis("x", "Hourly",null,' %I-%p ');
                //x.addOrderRule("Date");
                var yAxis = myChart.addMeasureAxis("y", "Usage");

                yAxis.lineMarkers = true;
                yAxis.lineWeight = 5;
                var series = myChart.addSeries(null, dimple.plot.bar);
                series.getTooltipText = function (e) {
                    return [
                        convertBytes(e.cy)
                    ];
                };
                myChart.draw();
                yAxis.shapes.selectAll("text")
                        .text(function (d) {
                            return convertBytes(d);
                        });



                var d = [];

                for (var day in dayUsage) {
                    d[day] = {"Day": 1+parseInt(day), "Usage": dayUsage[day].persistedUsage};
                }

                var svg1 = dimple.newSvg("#dayUsage", 900, 400);
                var myChart1 = new dimple.chart(svg1, d);
                myChart1.setBounds(60, 30, 800, 305);
                var x1 = myChart1.addCategoryAxis("x", "Day");
                //x.addOrderRule("Date");
                var yAxis1 = myChart1.addMeasureAxis("y", "Usage");

                yAxis1.lineMarkers = true;
                yAxis1.lineWeight = 5;
                var series1 = myChart1.addSeries(null, dimple.plot.bar);
                series1.getTooltipText = function (e) {

                    return [
                        convertBytes(e.cy)
                    ];
                };
                myChart1.draw();
                yAxis1.shapes.selectAll("text")
                        .text(function (d) {
                            return convertBytes(d);
                        });

            });
        });
    });
</script>

