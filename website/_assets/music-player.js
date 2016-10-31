/*
    Simple HTML5 audio player, with play-lists and thumbnails.
*/

limpygnome = { };
limpygnome.audio = { };

limpygnome.audio.player = (function(){

    var htmlPlayer = [
        '<div class="music-player">' +
            '<audio controls>' +
                '<source src="" type="audio/mpeg">' +
                '<p>Your browser does not support HTML5 audio.</p>' +
            '</audio>' +
            '<div class="player">' +
                '<div class="controls">' +
                    '<img alt="Play/Pause" src="/assets/music/player/play.png" id="play" onclick="limpygnome.audio.player.playPause(this);" />' +
                    '<img alt="Restart" src="/assets/music/player/restart.png" id="restart" onclick="limpygnome.audio.player.restart(this);" />' +
                    '<img alt="Next" src="/assets/music/player/next.png" id="next" onclick="limpygnome.audio.player.next(this);" />' +
                '</div>' +
                '<input type="range" class="seeker" min="0" max="100" value="0" id="seeker" onchange="limpygnome.audio.player.seek(this);" />' +
                '<div class="volume">' +
                    '<img alt="Volume" src="/assets/music/player/unmuted.png" id="volume" onclick="limpygnome.audio.player.muteToggle(this);" />' +
                    '<input type="range" min="0" max="100" value="100" id="volume_seeker" onchange="limpygnome.audio.player.volume(this);" />' +
                '</div>' +
            '</div>' +
            '<ul>' +
                '%SONGS%' +
            '</ul>' +
        '</div>'
    ];

    var htmlItem = [
        '<li>' +
            '<a href="%URL%" onclick="return limpygnome.audio.player.change(this);">' +
                '<img src="%THUMBNAIL%" alt="Album thumbnail" />' +
                '<span>%TITLE%</span>' +
            '</a>' +
        '</li>'
    ];

    var setup = function(config)
    {
        // Build list of songs
        var songs = "";
        var song;
        var songItem;

        for (var i = 0; i < config.songs.length; i++)
        {
            song = config.songs[i];
            songItem = htmlItem.join("")
                               .replace("%TITLE%", song.title)
                               .replace("%THUMBNAIL%", config.thumb)
                               .replace("%URL%", config.base + "/" + (song.fileName != null ? song.fileName : song.title));

            songs += songItem;
        }

        var player = htmlPlayer.join("");

        // Insert songs HTML
        player = player.replace("%SONGS%", songs);

        // Write HTML to page
        document.write(player);

        // Setup hooks for any uninitialized players i.e. one above
        setupHooksAndFirstSong();
    };


    var setupHooksAndFirstSong = function()
    {
        var nodes = document.getElementsByTagName("DIV");

        var node;
        for(i = 0; i < nodes.length; i++)
        {
            node = nodes[i];
            if(node.className != null && node.className == "music-player" && node.isSetup == null)
            {
                // Find first element, hook as current song
                var elementsPlaylistItems = node.getElementsByTagName("A");
                var elementsAudio = node.getElementsByTagName("AUDIO");

                if(elementsPlaylistItems.length > 0 && elementsAudio.length > 0)
                {
                    var player = elementsAudio[0];
                    var item = elementsPlaylistItems[0];
                    player.currentSong = item;

                    // Hook for auto-play of playlist
                    player.onended = function(e)
                    {
                        next(e.target);
                    };

                    // Hook for seeker update
                    player.ontimeupdate = function(e)
                    {
                        var player = e.target;
                        var progress = (player.currentTime / player.duration) * 100.0;
                        var seeker = getPlayerControl(player, "INPUT", "seeker");
                        seeker.value = progress;
                    };

                    // Hook for mute / volume update
                    player.onvolumechange = function(e)
                    {
                        var player = e.target;
                        var button = getPlayerControl(player, "IMG", "volume");
                        button.src = "/content/music/player/" + (player.muted ? "muted" : "unmuted") + ".png";
                        var volumeSeeker = getPlayerControl(player, "INPUT", "volume_seeker");
                        volumeSeeker.value = player.volume * 100.0;
                    };

                    // Hook for play/pause update
                    player.onplay = player.onpause = function(e)
                    {
                        var player = e.target;
                        var button = getPlayerControl(player, "IMG", "play");
                        button.src = "/assets/music/player/" + (player.paused ? "play" : "pause") + ".png";
                    };
                }

                // Set flag to indicate player has been setup
                node.isSetup = true;
            }
        }
    };

    var getPlayerControl = function(player, type, id)
    {
        var controls = player.parentNode.getElementsByTagName(type);
        var control;
        for(i = 0; i < controls.length; i++)
        {
            control = controls[i];
            if(control.id != null && control.id == id)
            {
                return control;
            }
        }
    };

    var getPlayer = function(child)
    {
        var parent = child.parentNode;
        var nodes;
        var node;
        var tag;

        for(var level = 0; level < 5; level++)
        {
            nodes = parent.childNodes;

            for(i = 0; i < nodes.length; i++)
            {
                node = nodes[i];
                tag = node.tagName;

                if(tag != null && tag.toLowerCase() == 'audio')
                {
                    return node;
                }
            }

            parent = parent.parentNode;
        }

        return null;
    };

    var getFirstSong = function(player)
    {
        var container = player.parentNode;
        var firstSong = null;

        // Find song list
        var songList = null;
        for (var i = 0; songList == null && i < container.childNodes.length; i++)
        {
            if (container.childNodes[i].tagName.toLowerCase() == 'ul')
            {
                songList = container.childNodes[i];
            }
        }

        // Find first song container
        if (songList != null)
        {
            var firstSongContainer = null;
            for (var i = 0; firstSongContainer != null && i < songList.childNodes.length; i++)
            {
                if (songList.childNodes[i].tagName.toLowerCase() == 'li')
                {
                    firstSongContainer = songList.childNodes[i];
                }
            }

            // Now fetch the actual first song (hyper-link)
            if (firstSongContainer != null)
            {
                for (var i = 0; firstSong != null && i < firstSongContainer.childNodes.length; i++)
                {
                    if (firstSongContainer.childNodes[i].tagName.toLowerCase() == 'a')
                    {
                        firstSong = firstSongContainer.childNodes[i];
                    }
                }
            }
        }

        return firstSong;
    };

    var seek = function(element)
    {
        var value = element.value;
        var player = getPlayer(element);
        player.currentTime = player.duration * (value/100.0);
    };

    var playPause = function(element)
    {
        var player = getPlayer(element);

        // Check if we have a current song
        if (player.currentSong == null)
        {
            // Fetch first song
            var firstSong = getFirstSong(player);

            // Change to first item
            if (firstSong != null)
            {
                change(firstSong);
            }
        }
        else
        {
            // Toggle player
            if(player.paused)
            {
                player.play();
            }
            else
            {
                player.pause();
            }
        }
    };

    var volume = function(element)
    {
        var player = getPlayer(element);
        player.volume = element.value/100.0;
    };

    var muteToggle = function(element)
    {
        var player = getPlayer(element);
        player.muted = !player.muted;
    };

    var restart = function(element)
    {
        var player = getPlayer(element);
        player.pause();
        player.load();
        player.play();
    };

    var next = function(element)
    {
        // Find player from element
        var player;
        if(element.tagName == null)
        {
            alert("Cannot play next song, player error.");
            return;
        }
        else if(element.tagName.toLowerCase() == "audio")
        {
            player = element;
        }
        else if((player = getPlayer(element)) == null)
        {
            alert("Cannot play next song, player not found.");
            return;
        }

        var currentElement = player.currentSong;

        if(currentElement == null)
        {
            alert("Cannot play next song, player error.");
            return;
        }

        // Find index of list, pick next or first index
        var ceParentNodes = currentElement.parentNode.parentNode.childNodes;
        var index = -1;
        var node;
        var nextNode;
        for(i = 0; i < ceParentNodes.length; i++)
        {
            node = ceParentNodes[i].firstElementChild;
            if(node != null && node.href != null)
            {
                // Select first node, incase no next node is found...
                if(nextNode == null)
                {
                    nextNode = node;
                }

                if(index != -1)
                {
                    // We've found the next href element after the current one
                    nextNode = node;
                    break;
                }
                else
                {
                    // Check if first index
                    if(node.href == currentElement.href)
                    {
                        index = i;
                    }
                }
            }
        }

        // Check we found an index
        if(nextNode == null)
        {
            alert("Failed to play next item, playlist error.");
            return;
        }

        change(nextNode);
    };

    var change = function(element)
    {
        var url = element.href;
        var player = getPlayer(element);

        if(player != null)
        {
            // Play clicked song
            player.pause();
            player.src = url;
            player.load();
            player.play();

            // Remove class of old element
            if(player.currentSong != null)
            {
                player.currentSong.parentNode.className = null;
            }

            // Save element to player
            player.currentSong = element;
            player.currentSong.parentNode.className = "playing";
        }

    };

    return {
        setup       : setup,
        change      : change,
        playPause   : playPause,
        seek        : seek,
        next        : next,
        restart     : restart,
        muteToggle  : muteToggle,
        volume      : volume
    };

})();

