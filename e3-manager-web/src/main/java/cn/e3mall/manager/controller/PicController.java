package cn.e3mall.manager.controller;

import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.utils.FastDFSClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangji on 2017/1/17.
 */
@Controller
@RequestMapping("/pic")
public class PicController {

    @Value("${baseImgUrl:http://192.168.116.140/}")
    private String baseImgUrl;
    @Value("${baseImgExts:jpg,png,bmp,gif}")
    private String baseImgExts;

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile uploadFile) throws Exception {
        if (uploadFile == null) {
            return getReslult(true,"提示信息");
        }

        //获取文件名
        String originalFilename = uploadFile.getOriginalFilename();
        //获取扩展名
        String extension = FilenameUtils.getExtension(originalFilename);
        //校验扩展名
        if (!baseImgExts.contains(extension)) {
            return getReslult(true,"不支持的图片格式");
        }
        try {
            //使用工具类保存到图片服务器
            FastDFSClient client = new FastDFSClient();
            String imgPath = client.uploadFile(uploadFile.getBytes(), extension);
            //拼接图片url
            String separate = "/";
            if ("/".equals(baseImgUrl.lastIndexOf(baseImgUrl.length()-1))) {
                separate = "";
            }
            String imgUrl = baseImgUrl + separate + imgPath;
            return getReslult(false,imgUrl);
        } catch (Exception e) {
            return getReslult(true,"保存失败");
        }
    }

    /**
     * 返回信息格式
     * //成功时
     {
     "error" : 0,
     "url" : "http://www.example.com/path/to/file.ext"
     }
     //失败时
     {
     "error" : 1,
     "message" : "错误信息"
     }
     */
    private String getReslult(boolean isError, String msg) {
        //创建一个返回结果map
        Map<String,Object> result = new HashMap<String, Object>();
        int code = 0;
        if (isError) {
            code = 1;
            result.put("message",msg);
        } else {
            result.put("url",msg);
        }
        result.put("error",code);

        return JsonUtils.objectToJson(result);
    }

}
