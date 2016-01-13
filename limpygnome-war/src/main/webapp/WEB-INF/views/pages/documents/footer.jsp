<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>

<footer>
    <p class="created">
        Created: <date><c:out value="${document.created}" /></date>
    </p>
    <c:if test="${document.created != document.updated}">
        <p class="updated">
            Updated: <date><c:out value="${document.updated}" /></date>
        </p>
    </c:if>
    <p class="print">
        <a rel="canonical" href="#" title="Print" onclick="window.print(); return false;">
            <span class="icon-printer"></span>
            Print
        </a>
    </p>
</footer>
