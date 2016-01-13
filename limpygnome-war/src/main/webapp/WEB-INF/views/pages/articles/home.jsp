<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>

<section class="documents">

    <h1>
        Articles
    </h1>

    <ul>
        <c:forEach var="document" items="${documents}">
            <li class="document">
                <span class="thumbnail">
                    <a href="<c:out value='${document.url}' />">
                        <img alt="Article thumbnail" src="<c:out value='${document.thumbnailUrl}' />" />
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
                <span class="created" title="updated: <c:out value='${document.updated}' />">
                    <c:out value="${document.created}" />
                </span>
            </li>
        </c:forEach>
    </ul>

</section>
