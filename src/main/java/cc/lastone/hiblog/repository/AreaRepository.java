package cc.lastone.hiblog.repository;

import cc.lastone.hiblog.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Integer> {

    List<Area> findAllByParentId(Integer parent_id);


}
