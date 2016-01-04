<%@ tag trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="album" type="com.limpygnome.website.module.music.model.Album" required="true" %>

<h2>
    <c:out value="${album.title}" />
</h2>

<div class="music_player" name="music_player">
    <audio controls>
        <source src="<c:out value='${album.songs[0].songUrl}' />" type="audio/mpeg" />
        <p>
            Your browser does not support HTML5 audio.
        </p>
    </audio>
    <div class="player">
        <div class="controls">
            <img alt="Play/Pause"   src="/content/music/player/play.png"        id="play"       onclick="audioPlayerPlayToggle(this);" />
            <img alt="Restart"      src="/content/music/player/restart.png"     id="restart"    onclick="audioPlayerRestart(this);" />
            <img alt="Next"         src="/content/music/player/next.png"        id="next"       onclick="audioPlayerNext(this);" />
        </div>
        <input type="range" class="seeker" min="0" max="100" value="0" id="seeker" onchange="audioPlayerSeek(this);" />
        <div class="volume">
            <img alt="Volume" src="/content/music/player/unmuted.png" id="volume" onclick="audioPlayerMuteToggle(this);" />
            <input type="range" min="0" max="100" value="100" id="volume_seeker" onchange="audioPlayerVolume(this);" />
        </div>
    </div>
    <ul>
        <c:forEach var="song" items="${album.songs}" varStatus="songVar">
            <li <c:if test="${songVar.index == 0}">class="playing"</c:if>>
                <a href="<c:out value='${song.songUrl}' />" onclick="audioPlayerChange(this); return false;">
                    <c:choose>
                        <c:when test="${empty song.thumbnailUrl}">
                            <img    alt="Song thumbnail"
                                    src="/content/music/thumbs/nolabel.png"
                            />

                        </c:when>
                        <c:otherwise>
                            <img    alt="Song thumbnail"
                                    src="<c:out value='${song.thumbnailUrl}' />"
                            />
                        </c:otherwise>
                    </c:choose>
                    <span>
                        <c:out value="${song.title}" />
                    </span>
                </a>
            </li>
        </c:forEach>
    </ul>
</div>
