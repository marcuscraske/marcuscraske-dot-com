<#global title="Finance - Overview">
${append_css("/content/css/finance.css")}

<h2>
    Finance - <a href="/finance">Overview</a>
</h2>

<h3>Overview</h3>
<script src="/content/js/Chart.js"></script>

<p class="tac">
    <canvas id="myChart" width="600" height="300"></canvas>
</p>

<script>
    var options = {
        ///Boolean - Whether grid lines are shown across the chart
        scaleShowGridLines : true,

        //String - Colour of the grid lines
        scaleGridLineColor : "rgba(0,0,0,0.1)",

        //Number - Width of the grid lines
        scaleGridLineWidth : 1,

        //Boolean - Whether the line is curved between points
        bezierCurve : true,

        //Number - Tension of the bezier curve between points
        bezierCurveTension : 0.4,

        //Boolean - Whether to show a dot for each point
        pointDot : true,

        //Number - Radius of each point dot in pixels
        pointDotRadius : 4,

        //Number - Pixel width of point dot stroke
        pointDotStrokeWidth : 1,

        //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
        pointHitDetectionRadius : 20,

        //Boolean - Whether to show a stroke for datasets
        datasetStroke : true,

        //Number - Pixel width of dataset stroke
        datasetStrokeWidth : 2,

        //Boolean - Whether to fill the dataset with a colour
        datasetFill : true,

        //String - A legend template
        legendTemplate :
            "<ul class=\"finance-overview-legend\">" +
                "<% for (var i=0; i<datasets.length; i++){%>" +
                    "<li><span style=\"background-color:<%=datasets[i].pointColor%>\"><%if(datasets[i].label){%><%=datasets[i].label%></span><%}%></li>" +
                "<%}%>" +
            "</ul>"

    };

    var data = {
        labels: [${finance_months_labels}],
        datasets: [
            {
                label: "Total Expenditure",
                fillColor: "rgba(204,0,0,0.2)",
                strokeColor: "rgba(204,0,0,1)",
                pointColor: "rgba(153,0,0,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: [${finance_months_totalout}]
            },
            {
                label: "Total Income",
                fillColor: "rgba(0,204,51,0.2)",
                strokeColor: "rgba(0,204,51,1)",
                pointColor: "rgba(0,153,0,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: [${finance_months_totalin}]
            },
            {
                label: "Total Balance",
                fillColor: "rgba(0,102,204,0.2)",
                strokeColor: "rgba(0,102,204,1)",
                pointColor: "rgba(0,51,153,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(0,51,153,1)",
                data: [${finance_months_balance}]
            }
        ]
    };



    var ctx = document.getElementById("myChart").getContext("2d");
    var myNewChart = new Chart(ctx).Line(data, options);
    document.write(myNewChart.generateLegend());
</script>

<h3>Accounts</h3>
list each account

<h3>Stats</h3>
overview table

chart for total txs
