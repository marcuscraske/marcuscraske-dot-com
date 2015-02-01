<#global title="Tools - UUID Generator">
${append_css("/content/css/tools.css")}
${append_js("/content/js/tools.js")}

<h2>
    <a href="/tools">Tools</a> - UUID Generator
</h2>

<form method="get" action="/tools/uuid">
    <p>
        Select amount:
        &nbsp;
        <select name="count">
            <#list 1..20 as i>
                <option<#if uuid_count == i> selected</#if>>${i}</option>
            </#list>
        </select>
        &nbsp;
        <input class="button" type="submit" value="Generate" />
    </p>
</form>

<div class="tools_uuids">
    <#list uuids as uuid>
        <div class="uuid" onclick="toolsCopy(this);">
            ${uuid?html}
        </div>
    </#list>
</div>