package com.limpygnome.website.module.music.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a collection of songs, not necessarily an actual/literal album.
 *
 * Not used by an actual DB for now, could be moved though.
 */
public class Album
{
    private String title;
    private List<Song> songs;

    public Album(String title)
    {
        this.title = title;
        this.songs = new LinkedList<>();
    }

    public void add(Song song)
    {
        songs.add(song);
    }

    public String getTitle()
    {
        return title;
    }

    public List<Song> getSongs()
    {
        return songs;
    }

}
