package cc.lastone.hiblog.controller;

import cc.lastone.hiblog.domain.Images;
import cc.lastone.hiblog.service.ImagesService;
import cc.lastone.hiblog.utils.MyDateUtil;
import cc.lastone.hiblog.utils.MyRandomUtil;
import cc.lastone.hiblog.utils.ResultData;
import cc.lastone.hiblog.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


@RestController
@RequestMapping({"/upload"})
public class UploadController {

    @Value("${hb.upload.image.size}")
    private int ImageSize;

    @Value("${hb.upload.image.suffix}")
    private String imageSuffix;

    @Autowired
    private ImagesService imagesService;

    /**
     * 图片上传
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping({"/image"})
    public ResultData index(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return new ResultData(ResultData.ERROR_PARAM, "请选择图片");
        }
        // 验证图片类型
        ArrayList<String> is = new ArrayList<String>(Arrays.asList(imageSuffix.split(",")));
        if (is == null && !is.contains(file.getContentType())) {
            return new ResultData(ResultData.ERROR_PARAM, "图片类型错误");
        }
        // 验证图片大小
        float size = file.getSize() / 1000;
        if (size > ImageSize) {
            return new ResultData(ResultData.ERROR_PARAM, "图片不能超过" + ImageSize / 1000 + "MB");
        }
        String url = uploadImage(file, request);
        HashMap hm = new HashMap<String, String>();
        hm.put("url", url);
        return new ResultData<HashMap>(hm, "图片上传成功");
    }

    /**
     * ckeditor 的图片上传
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping({"/ckeditor"})
    public HashMap ckeditor(@RequestParam(value = "upload") MultipartFile file, HttpServletRequest request) {
        HashMap hm = new HashMap<String, String>();
        if (file.isEmpty()) {
            hm.put("uploaded", 0);
            hm.put("error", "{\"message\":\"请选择图片\"}");
            return hm;
        }
        // 验证图片类型
        ArrayList<String> is = new ArrayList<String>(Arrays.asList(imageSuffix.split(",")));
        if (is == null && !is.contains(file.getContentType())) {
            hm.put("uploaded", 0);
            hm.put("error", "{\"message\":\"" + "图片类型错误\"}");
            return hm;
        }
        // 验证图片大小
        float size = file.getSize() / 1000;
        if (size > ImageSize) {
            hm.put("uploaded", 0);
            hm.put("error", "{\"message\":\"" + "图片不能超过" + ImageSize / 1000 + "MB" + "\"}");
            return hm;
            //return new ResultData(ResultData.ERROR_PARAM, "图片不能超过" + ImageSize / 1000 + "MB");
        }
        // 生成文件地址
        String fileName = file.getOriginalFilename();  // 文件名
        String url = uploadImage(file, request);
        hm.put("uploaded", 1);
        hm.put("fileName", fileName);
        hm.put("url", url);
        return hm;
    }

    private String uploadImage(MultipartFile file, HttpServletRequest request) {
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String uploadDir = ClassUtils.getDefaultClassLoader().getResource("static/uploadDir").getPath();
        String dateDir = "/images/" + MyDateUtil.date("yyyy/MM/");
        String newFileName = MyDateUtil.date("yyyyMMddHHmmss") + MyRandomUtil.randomStr(6) + suffixName;
        File uploadFile = new File(uploadDir + dateDir + newFileName);
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = request.getContextPath() + "/uploadDir" + dateDir + newFileName;
        // 添加到数据库中
        Images images = new Images();
        images.setOriginalName(fileName);
        images.setUid(UserUtil.getUserId());
        images.setUrl(url);
        imagesService.save(images);
        return url;
    }

}
