<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>

<section class="documents">

    <h1>
        Projects
    </h1>

    <ul>
        <c:forEach var="document" items="${documents}">
            <li class="document">
                <span class="thumbnail">
                    <a href="<c:out value='${document.url}' />">
                        <img alt="Project thumbnail" src="<c:out value='${document.thumbnailUrl}' />" />
                    </a>
                </span>
                <span class="description">
                    <a href="<c:out value='${document.url}' />">
                        <h2>
                            <c:out value="${document.title}" />
                        </h2>
                        <c:out value="${document.description}" />
                    </a>
                </span>
                <span class="status">
                    <span class="project-status <c:out value='${document.status}' />">
                        <c:out value="${document.status}" />
                    </span>
                </span>
            </li>
        </c:forEach>
    </ul>

</section>
