package com.limpygnome.website.module.music.controller;

import com.limpygnome.website.common.controller.BaseController;
import com.limpygnome.website.module.music.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MusicController extends BaseController
{
    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping({ "/music", "/music/home" })
    public ModelAndView home()
    {
        return createMV("pages/music/home", "music");
    }

    @RequestMapping("/music/my-songs")
    public ModelAndView mySongs()
    {
        ModelAndView modelAndView = createMV("pages/music/my-songs", "my songs");
        modelAndView.addObject("albums", albumRepository.getAlbums());

        return modelAndView;
    }

}
