package cc.lastone.hiblog.service.impl;

import cc.lastone.hiblog.domain.Industry;
import cc.lastone.hiblog.repository.IndustaryRepository;
import cc.lastone.hiblog.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IndustryServiceImpl implements IndustryService {

    @Autowired
    private IndustaryRepository industaryRepository;


    @Override
    public List<Industry> getList() {
        return industaryRepository.findAll(new Sort(Sort.Direction.ASC,"sn"));
    }
}
