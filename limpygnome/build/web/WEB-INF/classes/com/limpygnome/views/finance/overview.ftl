<#global title="Finance - Overview">
${append_css("/content/css/finance.css")}

<h2>
    Finance - <a href="/finance">Overview</a>
</h2>

<h3>Last 6 Months</h3>
<#include "graph_balance.ftl">

<h3>Options</h3>
<p class="tac">
    <a class="button" href="/finance/upload">
        <img src="/content/images/finance/upload.png" alt="Upload" />
        Upload
    </a>

    <a class="button" href="/finance/history">
        <img src="/content/images/finance/history.png" alt="History" />
        History
    </a>

    <a class="button" href="/finance/wipe">
        <img src="/content/images/finance/wipe.png" alt="Wipe All Data" />
        Wipe All Data
    </a>
</p>


<h3>Accounts</h3>
<div class="table">
    <#list finance_accounts as account>
        <div class="row">
            <div class="cell3">
                <div class="p">
                    ${account.getAlias()?html}
                </div>
            </div>
            <div class="cell3">
                <div class="p">
                    ${account.getAccountIdentifier()?html}
                </div>
            </div>
            <div class="cell3">
                <div class="p">
                    <a href="/finance/account/${account.getId()}" class="button">
                        View
                    </a>
                    <a href="/finance/account/${account.getId()}/edit" class="button">
                        Edit
                    </a>
                    <a href="/finance/account/${account.getId()}/delete" class="button">
                        Delete
                    </a>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </#list>
    <div class="clear"></div>
</div>
