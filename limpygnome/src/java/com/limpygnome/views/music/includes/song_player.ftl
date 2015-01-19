<#--
    Music player - can be used multiple times on a page.

    Params:
    - songs : [filename, thumbnail (optional)] , ...
    - songs_base : base path of songs
-->

<div class="music_player" name="music_player">
    <audio controls>
        <source src="/content/songs/<#if (songs_base?length > 0)>${songs_base?url}/</#if>${songs[0][0]?url}.mp3" type="audio/mpeg">
        <p>Your browser does not support HTML5 audio.</p>
    </audio>
    <div class="player">
        <div class="controls">
            <img alt="Play/Pause" src="/content/images/music-player/play.png" id="play" onclick="audioPlayerPlayToggle(this);" />
            <img alt="Restart" src="/content/images/music-player/restart.png" id="restart" onclick="audioPlayerRestart(this);" />
            <img alt="Next" src="/content/images/music-player/next.png" id="next" onclick="audioPlayerNext(this);" />
        </div>
        <input type="range" class="seeker" min="0" max="100" value="0" id="seeker" onchange="audioPlayerSeek(this);" />
        <div class="volume">
            <img alt="Volume" src="/content/images/music-player/unmuted.png" id="volume" onclick="audioPlayerMuteToggle(this);" />
            <input type="range" min="0" max="100" value="100" id="volume_seeker" onchange="audioPlayerVolume(this);" />
        </div>
    </div>
    <ul>
        <#list 0..songs?size-1 as i>
            <#assign song = songs[i]>
            <li <#if (i==0)>class="playing"</#if>>
                <a href="/content/songs/<#if (songs_base?length > 0)>${songs_base?url}/</#if>${song[0]?url}.mp3" onclick="audioPlayerChange(this); return false;">
                    <img alt="Thumbnail" src="/content/images/music/<#if (song?size > 1)>${song[1]?url}<#else>nolabel</#if>.png" />
                    ${song[0]?html}
                </a>
            </li>
        </#list>
    </ul>
</div>
