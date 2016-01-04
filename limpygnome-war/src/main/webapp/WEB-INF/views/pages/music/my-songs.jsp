<%@ taglib prefix="c"           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="lg"          tagdir="/WEB-INF/tags" %>




<section>
    <h1>
        <a href="/music">Music</a> - Songs
    </h1>

    <p>
        Below are songs I have written, with the exception of covers, which may be used in both commercial and non-commercial work, as long as credit is given.
        Usage of covered songs requires permission from the original authors. Most of the work below has been made for fun and is experimental.
    </p>
    <p>
        I use my own HTML5 audio player below, do <a href="/contact">contact</a> me if you experience any issues.
    </p>

    <c:forEach var="album" items="${albums}">
        <lg:musicPlayer album="${album}" />
    </c:forEach>

    <script type="text/javascript">
        audioPlayerHookAll();
    </script>
</section>
