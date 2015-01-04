<#global title="Finance - History">
${append_css("/content/css/finance.css")}

<h2>
    <a href="/finance">Finance</a> - History
</h2>

<h3>Graph</h3>
<#include "graph_balance.ftl">

<h3>Overview</h3>

<table>
    <tr>
        <th>Total TXs</th>
        <th>Total In</th>
        <th>Total Out</th>
        <th>Net Balance</th>
    </tr>
    <tr>
        <td>${finance_overview.getTotalTxs()}</td>
        <td>&pound;${(finance_overview.getTotalIn()/100.0)}</td>
        <td>&pound;${((finance_overview.getTotalOut()/100.0)*-1.0)}</td>
        <td>&pound;${(finance_overview.getTotalBalance()/100.0)}</td>
    </tr>
</table>

<h3>Monthly Breakdown</h3>
<table>
    <tr>
        <th rowspan="2">Year</th>
        <th rowspan="2">Month</th>
        <th colspan="3">Transactions</th>
        <th colspan="3">Cash Flow</th>
    </tr>
    <tr>
        <th>Total</th>
        <th>In</th>
        <th>Out</th>
        <th>In</th>
        <th>Out</th>
        <th>Net</th>
    </tr>
    <#list finance_months as month>
        <tr>
            <td>${month.getYear()?c}</td>
            <td>${month.getMonth()}</td>
            <td>${month.getTotalTxs()}</td>
            <td>${month.getTotalTxsIn()}</td>
            <td>${month.getTotalTxsOut()}</td>
            <td>&pound;${(month.getTotalIn())/100.0}</td>
            <td>&pound;${((month.getTotalOut())/100.0)*-1.0}</td>
            <td>&pound;${(month.getBalance())/100.0}</td>
        </tr>
    </#list>
</table>
