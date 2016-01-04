package com.limpygnome.website.module.music.model;

/**
 * Represents a song item.
 */
public class Song
{
    private String title;
    private String songUrl;
    private String thumbnailUrl;

    public Song(String title, String songUrl, String thumbnailUrl)
    {
        this.title = title;
        this.songUrl = songUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSongUrl()
    {
        return songUrl;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

}
