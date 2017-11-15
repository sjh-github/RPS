package com.hdu.rps.web;

import com.hdu.rps.model.Position;
import com.hdu.rps.service.HRRecruitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by SJH on 2017/11/7.
 */
@Transactional
@RequestMapping("/hr")
@Controller
public class HRHomeAction {

    @Autowired
    private HRRecruitServiceImpl hrRecruitServiceImpl;

    private Logger logger = Logger.getLogger(String.valueOf(HRHomeAction.this));
    private List<Position> positionList = new ArrayList<>();
    private String[] ids;
    @RequestMapping("/homeDetail")
    public String homeDetail(ModelMap modelMap) {
        positionList = hrRecruitServiceImpl.getPositionList();
        modelMap.addAttribute("positionList",positionList);
        return "hrHomeDetail";
    }

    @RequestMapping("/addJob")
    public String addJob() {
        return "addJob";
    }

    @RequestMapping(value = "/recruit",method = RequestMethod.POST)
    public String recruit(@RequestParam String jobname,@RequestParam String jobcount,@RequestParam String province,@RequestParam String city
        ,@RequestParam String deadtime,@RequestParam int salary1,@RequestParam int salary2
                          ,@RequestParam String duty,@RequestParam String skill,@RequestParam String message
            ,@RequestParam String btn,ModelMap modelMap
    ) {
        if("普通发布".equals(btn)) {
            logger.info("---------------进入普通发布--------------");
            hrRecruitServiceImpl.recruit(jobname,jobcount,province,city,deadtime,salary1,salary2,duty,skill,message);
        } else if("紧急发布".equals(btn)) {
            logger.info("---------------进入紧急发布--------------");
            hrRecruitServiceImpl.recruit(jobname,jobcount,province,city,deadtime,salary1,salary2,duty,skill,message);
            //添加邮件服务


        } else {
            logger.warning("--------------发布有误---------");
        }
        return "redirect:/hr/homeDetail";
    }

    @RequestMapping("/del/{id}")
    public String delByID(@PathVariable int id) {
        logger.info("------------删除单个招聘信息id=" + id);
        hrRecruitServiceImpl.delByID(id);
        return "redirect:/hr/homeDetail";
    }

    @RequestMapping("/delSelected")
    public String delSelected(@RequestParam String checkedID) {
        logger.info("-----------删除多个招聘信息-----------");
        ids = checkedID.split(",");
        hrRecruitServiceImpl.delSelected(ids);
        return "redirect:/hr/homeDetail";
    }
}
