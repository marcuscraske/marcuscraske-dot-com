package com.limpygnome.website.module.music;

import com.limpygnome.website.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MusicController extends BaseController
{

    @RequestMapping({ "/music", "/music/home" })
    public ModelAndView home()
    {
        return createMV("pages/music/home", "music");
    }

    @RequestMapping("/music/my-songs")
    public ModelAndView mySongs()
    {
        return createMV("pages/music/my-songs", "my songs");
    }

}
