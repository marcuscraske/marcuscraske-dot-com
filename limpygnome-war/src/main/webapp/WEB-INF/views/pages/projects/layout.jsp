<%@taglib prefix="tiles"    uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>

<div class="projects">
    <article>
        <header>
            <h1>
                <a href="/projects">
                    Projects
                </a>
                -
                <c:out value="${document.title}" />
            </h1>
            <p class="status <c:out value='${document.status}' />">
                <c:out value="${document.status}" />
            </p>
        </header>

        <tiles:insertAttribute name="projectContent" />

        <footer>
            <p class="created">
                Created: <c:out value="${document.created}" />
            </p>
            <c:if test="${document.created != document.updated}">
                <p class="updated">
                    Updated: <c:out value="${document.updated}" />
                </p>
            </c:if>
            <p class="print">
                <a href="#" title="Print" onclick="window.print(); return false;">
                    <span class="icon-printer"></span>
                    Print
                </a>
            </p>
        </footer>

    </article>
</div>