<#global title="Projects">
${append_css("/content/css/projects.css")}

<h2>
    ${title}
</h2>

<div class="projects">
    <#if !(projects?has_content)>
        <p>
            No projects available at this time.
        </p>
    <#else>
        <#list projects as project>
            <a href="/projects/${project.getUrlPart()}">
                <span class="status ${project.getStatus()?html}">
                    ${project.getStatusText()?html}
                </span>
                <img src="${project.getThumbnailUrl()}" alt="${project.getTitle()?html}'s thumbnail" /><b>${project.getTitle()?html}</b><br />
                <#if project.getDescription()??>
                    <span>${project.getDescription()?html}</span>
                </#if>
            </a>
        </#list>
    </#if>
    <div class="clear"></div>
</div>
