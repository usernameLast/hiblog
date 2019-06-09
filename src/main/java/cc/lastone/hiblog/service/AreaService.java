package cc.lastone.hiblog.service;

import cc.lastone.hiblog.domain.Area;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AreaService {
    List<Area> getListByParentId(Integer parent_id);
}
