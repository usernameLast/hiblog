package cc.lastone.hiblog.service;

import cc.lastone.hiblog.domain.Images;
import cc.lastone.hiblog.utils.ResultData;
import org.springframework.stereotype.Service;

@Service
public interface ImagesService {

    ResultData save(Images rv);
}
