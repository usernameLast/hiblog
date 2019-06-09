package cc.lastone.hiblog.service.impl;

import cc.lastone.hiblog.domain.Images;
import cc.lastone.hiblog.repository.ImagesRepository;
import cc.lastone.hiblog.service.ImagesService;
import cc.lastone.hiblog.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImagesServiceImpl implements ImagesService {

    @Autowired
    private ImagesRepository imagesRepository;

    @Override
    public ResultData save(Images images) {
        // 判断用户是否存在
        imagesRepository.save(images);
        return new ResultData<Images>(images);
    }
}
