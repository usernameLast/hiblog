package cc.lastone.hiblog.service;

import cc.lastone.hiblog.domain.Industry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IndustryService {
    List<Industry> getList();
}
