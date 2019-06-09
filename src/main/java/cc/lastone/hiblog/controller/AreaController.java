package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.domain.Area;
import cc.lastone.hiblog.service.AreaService;
import cc.lastone.hiblog.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping({"/area"})
public class AreaController {

    @Autowired
    AreaService areaService;

    /**
     * 通过父级id获得子级地区
     */
    @RequestMapping({"/select"})
    public ResultData index(Integer id) {
        ArrayList<Area> list = (ArrayList<Area>) areaService.getListByParentId(id);
        return new ResultData<ArrayList>(list);
    }

}
