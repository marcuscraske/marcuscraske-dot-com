<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>

<section class="projects">

    <h1>
        Projects
    </h1>

    <ul>
        <c:forEach var="document" items="${documents}">
            <li>
                <a href="<c:out value='${document.url}' />">
                    <span class="thumbnail">
                        <img alt="Project thumbnail" src="<c:out value='${document.thumbnailUrl}' />" />
                    </span>
                    <span class="title">
                        <c:out value="${document.title}" />
                        <span class="status <c:out value='${document.status}' />">
                            <c:out value="${document.status}" />
                        </span>
                    </span>
                    <span class="descriptioon">
                        <c:out value="${document.description}" />
                    </span>
                </a>
            </li>
        </c:forEach>
    </ul>

</section>
