<%@taglib prefix="tiles"    uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@taglib prefix="lg"       tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>
        <c:out value="${pageTitle}" /> - limpygnome.com
    </title>

    <lg:layoutMetaTags />
    <lg:layoutResources />

    <%-- Header includes --%>
    <tiles:importAttribute name="headerIncludes" />
    <c:forEach var="item" items="${headerIncludes}">
        <tiles:insertAttribute value="${item}" flush="true" />
    </c:forEach>

</head>
<body>
    <div class="pwrapper">

        <div class="sidebar">
            <header class="header">
                <h1>
                    <a href="/">
                        limpygnome
                    </a>
                </h1>
            </header>
            <nav>

                <ul>
                    <ul id="nav_cv">
                        <a href="/cv">
                            <span class="icon-file-text2"></span>
                            Curriculum Vitae
                        </a>
                    </ul>
                    <ul id="nav_main">
                        <a href="/articles">
                            <span class="icon-books"></span>
                            Articles
                        </a>
                        <a href="/projects">
                            <span class="icon-lab"></span>
                            Projects
                        </a>
                        <a href="/timeline">
                            <span class="icon-airplane"></span>
                            Timeline
                        </a>
                        <a href="/music">
                            <span class="icon-music"></span>
                            Music
                        </a>
                    </ul>
                    <ul id="nav_misc">
                        <a href="/tools">
                            <span class="icon-cog"></span>
                            Tools
                        </a>
                        <a href="http://files.limpygnome.com">
                            <span class="icon-folder-open"></span>
                            Public Files
                        </a>
                    </ul>

                    <ul id="nav_contact">
                        <a href="/contact">
                            <span class="icon-phone"></span>
                            Contact
                        </a>
                    </ul>
                </ul>

            </nav>
        </div>

        <main>
            <tiles:insertAttribute name="pageContent" />#

            <tiles:importAttribute name="pageContentIncludes" />
            <c:forEach var="item" items="${pageContentIncludes}">
                <tiles:insertAttribute value="${item}" flush="true" />
            </c:forEach>
        </main>

        <footer class="footer-icons">
            <a title="Github" class="ficon gh" href="https://www.github.com/limpygnome">
                Github
            </a>
            <a title="YouTube" class="ficon yt" href="https://www.youtube.com/user/limpygnome">
                YouTube
            </a>
            <a title="SoundCloud" class="ficon sc" href="https://www.soundcloud.com/limpygnome">
                SoundCloud
            </a>
            <a title="LinkedIn" class="ficon li" href="http://www.linkedin.com/in/marcuscraske">
                LinkedIn
            </a>
            <a title="Google Plus" class="ficon gp" href="https://plus.google.com/102279851938943898719">
                Google Plus
            </a>
            <a title="Twitter" class="ficon twt" href="https://twitter.com/Limpygnome">
                Twitter
            </a>
            <a title="Facebook" class="ficon fb" href="https://www.facebook.com/marcus.craske">
                Facebook
            </a>
            <a title="E-mail" class="ficon gm" href="mailto:limpygnome@gmail.com">
                E-mail
            </a>
        </footer>
    </div>
    <div class="swrapper">
        &nbsp;
    </div>
</body>
</html>
