<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>
            limpygnome.com ~ <#if title??>${title?html}<#else>Undefined Title</#if>
        </title>
	<meta http-equiv="/content-type" content="text/html;charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Droid+Sans">
	<link rel="stylesheet" type="text/css" href="/content/css/layout.css" />
</head>
<body>
	<div class="pwrapper">
		<div class="wrapper">
			<div class="banner">
				<h1>limpygnome</h1>
			</div>
		</div>
		<div class="wrapper">
			<div class="nav">
				<div id="nav_main">
					<a href="/">
						<img alt="Home" src="/content/images/nav/home.png" />
						Home
					</a>
					<a href="/me">
						<img alt="Me" src="/content/images/nav/me.png" />
						Me
					</a>
					<a href="/cv">
						<img alt="Cirriculum Vitae" src="/content/images/nav/cv.png" />
						Curriculum Vitae
					</a>
					<a href="/contact">
						<img alt="Contact" src="/content/images/nav/contact.png" />
						Contact
					</a>
				</div>
				<div id="nav_projects">
					<div>
						<a href="/projects">
							Projects
						</a>
					</div>
					<a href="/software/binary_clock">
						<img alt="Binary Clock" src="/content/images/nav/binary_clock.png" />
						Binary Clock
					</a>
					<a href="/software/portable_postgres">
						<img alt="Portable Postgres" src="/content/images/nav/portable_postgres.png" />
						Portable Postgres
					</a>
					<a href="/software/pals">
						<img alt="PALS" src="/content/images/nav/portable_postgres.png" />
						PALS
					</a>
				</div>
				<div id="nav_games">
					<div>Games</div>
					<a href="/games/insomniac">
						<img alt="Insomniac" src="/content/images/nav/insomniac.png" />
						Insomniac
					</a>
					<a href="/games/whirlpool">
						<img alt="Whirlpool" src="/content/images/nav/whirlpool.png" />
						Whirlpool
					</a>
				</div>
				<div id="nav_articles">
					<div>
						<a href="/articles">
							Articles
						</a>
					</div>
					<a href="/hacking_nandos_pong_game">
						<img alt="Alarm Clock" src="/content/images/nav/nandos.png" />
						Hacking The Nandos...
					</a>
					<a href="/university">
						<img alt="Alarm Clock" src="/content/images/nav/uni.png" />
						University Work
					</a>
				</div>
				<div id="nav_misc">
					<div>Misc</div>
					<a href="/files">
						<img alt="Images" src="/content/images/nav/files.png" />
						Public Files
					</a>
					<a href="https://github.com/limpygnome">
						<img alt="Github" src="/content/images/nav/github.png" />
						Github
					</a>
					<a href="/music">
						<img alt="Guitar" src="/content/images/nav/music.png" />
						Music
					</a>
					<a href="/tools">
						<img alt="Guitar" src="/content/images/nav/tools.png" />
						Tools
					</a>
				</div>
			</div>
			<div class="content">
				<div class="inner_content <#if page_fill??>fill</#if>">
                                        <#if page_fill??>
                                            <h2>
                                                <#if title??>${title?html}<#else>Undefined Title</#if>
                                            </h2>
                                        </#if>

                                        <#if content??>
                                            <#include content+".ftl">
                                        </#if>
					
					<div class="clear"></div>
				</div>
				<div class="icons">
					<a title="Github" class="icon gh" href="https://www.github.com/limpygnome">
						Github
					</a>
					<a title="YouTube" class="icon yt" href="https://www.youtube.com/user/limpygnome">
						YouTube
					</a>
					<a title="SoundCloud" class="icon sc" href="https://www.soundcloud.com/limpygnome">
						SoundCloud
					</a>
					<a title="LinkedIn" class="icon li" href="http://www.linkedin.com/in/marcuscraske">
						LinkedIn
					</a>
					<a title="Google Plus" class="icon gp" href="https://plus.google.com/102279851938943898719">
						Google Plus
					</a>
					<a title="Twitter" class="icon twt" href="https://twitter.com/Limpygnome">
						Twitter
					</a>
					<a title="Facebook" class="icon fb" href="https://www.facebook.com/marcus.craske">
						Facebook
					</a>
					<a title="E-mail" class="icon gm" href="mailto:limpygnome@gmail.com">
						E-mail
					</a>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
<div class="swrapper">
    &nbsp;
</div>
</body>
</html>
<!-- Partial icon credit to: gentleface.com -->
