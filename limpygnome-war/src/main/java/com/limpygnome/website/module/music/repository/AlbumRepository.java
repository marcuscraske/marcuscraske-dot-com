package com.limpygnome.website.module.music.repository;

import com.limpygnome.website.module.music.model.Album;
import com.limpygnome.website.module.music.model.Song;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * Currently a static repository to reduce using a database.
 */
@Repository
public class AlbumRepository
{
    private static final String SONGS_BASE_URL = "http://files.limpygnome.com/music/";
    private static List<Album> albums;

    static
    {
        albums = new LinkedList<>();

        // Unorganised
        Album albumUnorganised = new Album("Unorganised");
        albumUnorganised.add(new Song("goth", SONGS_BASE_URL + "unorganised/goth.mp3", null));
        albumUnorganised.add(new Song("liveset", SONGS_BASE_URL + "unorganised/liveset.mp3", null));
        albumUnorganised.add(new Song("mess", SONGS_BASE_URL + "unorganised/mess.mp3", null));
        albumUnorganised.add(new Song("messaround", SONGS_BASE_URL + "unorganised/messaround.mp3", null));
        albumUnorganised.add(new Song("messaround2", SONGS_BASE_URL + "unorganised/messaround2.mp3", null));
        albumUnorganised.add(new Song("rush", SONGS_BASE_URL + "unorganised/rush.mp3", null));
        albumUnorganised.add(new Song("untitled", SONGS_BASE_URL + "unorganised/untitled.mp3", null));
        albumUnorganised.add(new Song("warmth", SONGS_BASE_URL + "unorganised/warmth.mp3", null));
        albumUnorganised.add(new Song("whoiusedtobe", SONGS_BASE_URL + "unorganised/whoiusedtobe.mp3", null));

        albums.add(albumUnorganised);


        // Noise
        final String NOISE_THUMB = "/content/music/thumbs/noise.png";

        Album albumNoise = new Album("Noise I");
        albumNoise.add(new Song("Noise I - 7", SONGS_BASE_URL + "noise/7.mp3", NOISE_THUMB));
        albumNoise.add(new Song("Noise I - 11", SONGS_BASE_URL + "noise/11.mp3", NOISE_THUMB));
        albumNoise.add(new Song("Noise I - 15", SONGS_BASE_URL + "noise/15.mp3", NOISE_THUMB));

        albums.add(albumNoise);


        // Covers
        Album albumCovers = new Album("Covers");
        albumCovers.add(new Song(
                "Phantogram - Mouthful of Diamonds (instrumental)",
                SONGS_BASE_URL + "covers/phantogram_mouthful_of_diamonds.mp3",
                null
        ));

        albums.add(albumCovers);
    }

    public List<Album> getAlbums()
    {
        return albums;
    }

}
