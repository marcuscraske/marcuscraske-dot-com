<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:if test="${not empty pageMetaAuthor}">
    <meta name="author" content="<c:out value='${pageMetaAuthor}' />">
</c:if>
<c:if test="${not empty pageMetaDescription}">
    <meta name="description" content="<c:out value='${pageMetaDescription}' />">
</c:if>
<c:if test="${not empty pageMetaKeywords}">
    <meta name="keywords" content="<c:out value='${pageMetaKeywords}' />">
</c:if>
