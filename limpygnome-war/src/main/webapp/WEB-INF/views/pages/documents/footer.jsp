<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>

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
