<#global title="Music - Songs">
${append_js("/content/js/music-player.js")}
${append_css("/content/css/music-player.css")}

<h2>
    <a href="/music">Music</a> - Songs
</h2>

<p>
    Below are songs I have written, with the exception of covers, which may be used in both commercial and non-commercial work, as long as credit is given.
    Usage of covered songs requires permission from the original authors. Most of the work below has been made for fun and is experimental.
</p>
<p>
    I use my own HTML5 audio player below, do <a href="/contact">contact</a> me if you experience any issues.
</p>

<h3>Unorganised</h3>
<#assign songs_base=''>
<#assign songs=[
    ['goth'],
    ['liveset'],
    ['mess'],
    ['messaround'],
    ['messaround2'],
    ['rush'],
    ['untitled'],
    ['warmth'],
    ['whoiusedtobe']
]>
<#include "includes/song_player.ftl">

<h3>Noise</h3>
<#assign songs_base='noise'>
<#assign songs=[
    ['Noise - 7', 'noise'],
    ['Noise - 11', 'noise'],
    ['Noise - 15', 'noise']
]>
<#include "includes/song_player.ftl">

<h3>Covers</h3>
<#assign songs_base='covers'>
<#assign songs=[
    ['Phantogram - Mouthful of Diamonds (instrumental cover)']
]>
<#include "includes/song_player.ftl">

<script type="text/javascript">
    audioPlayerHookAll();
</script>
