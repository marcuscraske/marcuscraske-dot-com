/*
    Simple HTML5 audio player, with play-lists and thumbnails.
*/

limpygnome = { };
limpygnome.audio = { };

limpygnome.audio.player = (function(){

    var setup = function(id, songs)
    {
        // Add songs to player

        // Add hooks
    };

//    function audioPlayerHookAll()
//    {
//        var nodes = document.getElementsByTagName("DIV");
//
//        var node;
//        for(i = 0; i < nodes.length; i++)
//        {
//            node = nodes[i];
//            if(node.className != null && node.className == "music_player")
//            {
//                // Find first element, hook as current song
//                var elementsPlaylistItems = node.getElementsByTagName("A");
//                var elementsAudio = node.getElementsByTagName("AUDIO");
//
//                if(elementsPlaylistItems.length > 0 && elementsAudio.length > 0)
//                {
//                    var player = elementsAudio[0];
//                    var item = elementsPlaylistItems[0];
//                    player.currentSong = item;
//
//                    // Hook for auto-play of playlist
//                    player.onended = function(e)
//                    {
//                        audioPlayerNext(e.target);
//                    };
//
//                    // Hook for seeker update
//                    player.ontimeupdate = function(e)
//                    {
//                        var player = e.target;
//                        var progress = (player.currentTime / player.duration) * 100.0;
//                        var seeker = audioPlayerGetControl(player, "INPUT", "seeker");
//                        seeker.value = progress;
//                    };
//
//                    // Hook for mute / volume update
//                    player.onvolumechange = function(e)
//                    {
//                        var player = e.target;
//                        var button = audioPlayerGetControl(player, "IMG", "volume");
//                        button.src = "/content/music/player/" + (player.muted ? "muted" : "unmuted") + ".png";
//                        var volumeSeeker = audioPlayerGetControl(player, "INPUT", "volume_seeker");
//                        volumeSeeker.value = player.volume * 100.0;
//                    };
//
//                    // Hook for play/pause update
//                    player.onplay = player.onpause = function(e)
//                    {
//                        var player = e.target;
//                        var button = audioPlayerGetControl(player, "IMG", "play");
//                        button.src = "/content/music/player/" + (player.paused ? "play" : "pause") + ".png";
//                    };
//                }
//            }
//        }
//
//    }
//
//    function audioPlayerGetControl(player, type, id)
//    {
//        var controls = player.parentNode.getElementsByTagName(type);
//        var control;
//        for(i = 0; i < controls.length; i++)
//        {
//            control = controls[i];
//            if(control.id != null && control.id == id)
//            {
//                return control;
//            }
//        }
//    }
//
//    function audioPlayerGet(child)
//    {
//        var parent = child.parentNode;
//        var nodes;
//        var node;
//        var tag;
//
//        for(var level = 0; level < 5; level++)
//        {
//            nodes = parent.childNodes;
//
//            for(i = 0; i < nodes.length; i++)
//            {
//                node = nodes[i];
//                tag = node.tagName;
//
//                if(tag != null && tag.toLowerCase() == 'audio')
//                {
//                    return node;
//                }
//            }
//
//            parent = parent.parentNode;
//        }
//
//        return null;
//    }
//
//    function audioPlayerSeek(element)
//    {
//        var value = element.value;
//        var player = audioPlayerGet(element);
//        player.currentTime = player.duration * (value/100.0);
//    }
//
//    function audioPlayerPlayToggle(element)
//    {
//        var player = audioPlayerGet(element);
//        if(player.paused)
//        {
//            player.play();
//        }
//        else
//        {
//            player.pause();
//        }
//    }
//
//    function audioPlayerVolume(element)
//    {
//        var player = audioPlayerGet(element);
//        player.volume = element.value/100.0;
//    }
//
//    function audioPlayerMuteToggle(element)
//    {
//        var player = audioPlayerGet(element);
//        player.muted = !player.muted;
//    }
//
//    function audioPlayerRestart(element)
//    {
//        var player = audioPlayerGet(element);
//        player.pause();
//        player.load();
//        player.play();
//    }
//
//    function audioPlayerNext(element)
//    {
//        // Find player from element
//        var player;
//        if(element.tagName == null)
//        {
//            alert("Cannot play next song, player error.");
//            return;
//        }
//        else if(element.tagName.toLowerCase() == "audio")
//        {
//            player = element;
//        }
//        else if((player = audioPlayerGet(element)) == null)
//        {
//            alert("Cannot play next song, player not found.");
//            return;
//        }
//
//        var currentElement = player.currentSong;
//
//        if(currentElement == null)
//        {
//            alert("Cannot play next song, player error.");
//            return;
//        }
//
//        // Find index of list, pick next or first index
//        var ceParentNodes = currentElement.parentNode.parentNode.childNodes;
//        var index = -1;
//        var node;
//        var nextNode;
//        for(i = 0; i < ceParentNodes.length; i++)
//        {
//            node = ceParentNodes[i].firstElementChild;
//            if(node != null && node.href != null)
//            {
//                // Select first node, incase no next node is found...
//                if(nextNode == null)
//                {
//                    nextNode = node;
//                }
//
//                if(index != -1)
//                {
//                    // We've found the next href element after the current one
//                    nextNode = node;
//                    break;
//                }
//                else
//                {
//                    // Check if first index
//                    if(node.href == currentElement.href)
//                    {
//                        index = i;
//                    }
//                }
//            }
//        }
//
//        // Check we found an index
//        if(nextNode == null)
//        {
//            alert("Failed to play next item, playlist error.");
//            return;
//        }
//
//        audioPlayerChange(nextNode);
//    }
//
//    function audioPlayerChange(element)
//    {
//        var url = element.href;
//        var player = audioPlayerGet(element);
//
//        if(player != null)
//        {
//            // Play clicked song
//            player.pause();
//            player.src = url;
//            player.load();
//            player.play();
//
//            // Remove class of old element
//            if(player.currentSong != null)
//            {
//                player.currentSong.parentNode.className = null;
//            }
//
//            // Save element to player
//            player.currentSong = element;
//            player.currentSong.parentNode.className = "playing";
//        }
//
//    }

    return {
        setup : setup
    };

})();

