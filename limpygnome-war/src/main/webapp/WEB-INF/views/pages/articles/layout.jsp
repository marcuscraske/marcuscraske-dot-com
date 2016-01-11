<%@taglib prefix="tiles"    uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>

<div class="document article">
    <article>
        <header>
            <h1>
                <a href="/articles">
                    Articles
                </a>
                -
                <c:out value="${document.title}" />
            </h1>
        </header>

        <tiles:insertAttribute name="articleContent" />

        <tiles:insertDefinition name="documents/footer" />

    </article>
</div>
