<%@taglib prefix="tiles"    uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>

<div class="document project">
    <article>
        <header>
            <h1>
                <a href="/projects">
                    Projects
                </a>
                -
                <c:out value="${document.title}" />
            </h1>
            <p class="project-status <c:out value='${document.status}' />">
                <c:out value="${document.status}" />
            </p>
        </header>

        <tiles:insertAttribute name="projectContent" />

        <tiles:insertDefinition name="documents/footer" />

    </article>
</div>