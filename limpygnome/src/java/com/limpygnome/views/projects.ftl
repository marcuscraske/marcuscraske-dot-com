<#global title="Projects">
${append_css("/content/css/projects.css")}

<h2>
    ${title}
</h2>

<div class="projects">
    <#list projects as project>
        <a href="${project.getRelativeUrl()}">
            <span class="status">
                ${project.getStatusText()?html}
            </span>
            <img src="${project.getThumbnailURL()}" alt="${project.getTitle()?html}'s thumbnail" /> ${project.getTitle()?html}
        </a>
    </#list>
</div>
