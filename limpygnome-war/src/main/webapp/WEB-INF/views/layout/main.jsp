<%@taglib prefix="tiles"    uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring"   uri="http://www.springframework.org/tags" %>
<%@taglib prefix="wp"       tagdir="/WEB-INF/tags/wp" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>
        <tiles:insertAttribute name="pageTitle" ignore="true" /> - limpygnome.com
    </title>

    <meta http-equiv="content-type" content="text/html;charset=utf-8" />

    <%--
    remove this...
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Droid+Sans">
    --%>

    <link rel="stylesheet" type="text/css" href="/content/fonts/droid-sans/droid-sans.css" />
    <link rel="stylesheet" type="text/css" href="/content/fonts/icomoon/style.css" />

    <link rel="stylesheet" type="text/css" href="/content/css/layout.css" />
    <link rel="stylesheet" type="text/css" href="/content/css/layout-responsive.css" />

    <link rel="stylesheet" type="text/css" href="/content/css/content/elements.css" />

    <link rel="stylesheet" type="text/css" href="/content/css/pages/home.css" />

</head>
<body>
    <div class="pwrapper">

        <div class="sidebar">
            <div class="header">
                <h1>
                    <a href="/">
                        limpygnome
                    </a>
                </h1>
            </div>
            <div class="nav">

                <div id="nav_cv">
                    <a href="/cv">
                        <span class="icon-file-text2"></span>
                        Curriculum Vitae
                    </a>
                </div>
                <div id="nav_main">
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
                </div>
                <div id="nav_misc">
                    <a href="/tools">
                        <span class="icon-cog"></span>
                        Tools
                    </a>
                    <a href="http://public.limpygnome.com">
                        <span class="icon-folder-open"></span>
                        Public Files
                    </a>
                </div>

                <div id="nav_contact">
                    <a href="/contact">
                        <span class="icon-phone"></span>
                        Contact
                    </a>
                </div>

            </div>
        </div>

        <div class="content">
            <tiles:insertAttribute name="pageContent" />
            <div class="clear"></div>
        </div>

        <div class="footer-icons">
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
        </div>
        <div class="clear"></div>
    </div>
    <div class="swrapper">
        &nbsp;
    </div>
</body>
</html>
