package cc.lastone.hiblog.service.impl;

import cc.lastone.hiblog.domain.Area;
import cc.lastone.hiblog.repository.AreaRepository;
import cc.lastone.hiblog.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<Area> getListByParentId(Integer parent_id) {
        return areaRepository.findAllByParentId(parent_id);
    }
}
