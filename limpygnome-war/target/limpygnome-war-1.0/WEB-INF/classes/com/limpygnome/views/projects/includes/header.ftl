<#if project??>
    ${append_css("/content/css/projects.css")}
    <#global title=project.getTitle()>

    <div class="project">
        <div class="status ${project.getStatus()?html}">${project.getStatusText()?html}</div>
        <h2>
            <a href="/projects">Projects</a> - ${project.getTitle()?html}
        </h2>
        <div class="clear"></div>
    </div>
<#else>
    <h2>
        <#if title??>${title?html}</#if>
    </h2>
    <p class="error">
        Cannot include project header, not present in database.
    </p>
</#if>
