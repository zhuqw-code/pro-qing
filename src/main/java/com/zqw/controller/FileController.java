package com.zqw.controller;

import cn.hutool.core.io.FileUtil;
import com.zqw.common.Result;
import com.zqw.exception.CustomException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 文件上传下载功能（非OSS）
 *    1.前端添加图片，发送请求给后端，后端接收到请求将图片文件存储到本地静态资源中
 *    2.并将该图片本地路径转换为网络连接+图片名称 呈现给用户
 */
@RestController
@RequestMapping("/files")
public class FileController {
    // 静态资源路径
    public static final String projectPath = System.getProperty("user.dir");
    public static final String staticPath = "/src/main/resources/static/files/";
    public static final String filePath = projectPath + staticPath;

    /**
     *  ？upload到服务器后，downLoad到前端页面，但是download时我怎么知道需要下载那个图片呢？
     *      ！只需要将上传的文件名返回给用户，之后用户可以根据这个文件名回显图片
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upLoad(MultipartFile file) {
        // 测试静态目录是否存存在
        if (!FileUtil.isDirectory(filePath)) {
            // 如果不是一个目录
            FileUtil.mkdir(filePath);
        }
        String originalName = file.getOriginalFilename();
        String suffer = originalName.substring(originalName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String savePath = filePath + uuid + suffer;
        // 将图片文件存储到目录下
        try {
            FileUtil.writeBytes(file.getBytes(), savePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("500", "图片上传失败！！！");
        }
        // 返回给前端一个网络连接，并加上图片名称
        // . download请求是如何发送的？
        // 浏览器自动发送。当您将upload接口返回的图片URL设置为<img>标签的src属性时，
        // 浏览器会自动向该URL发起GET请求，触发后端的download方法。
        String url = "http://localhost:8080/files/download/" + uuid + suffer;
        return Result.success(url);
    }

    /**
     * 该请求是在upload请求后返回的url在v-if中如果有的话浏览器就会访问这个图片路径，进而访问该接口
     * @param fileName
     * @param response
     * @return
     */
    @GetMapping("/download/{imgPath}")
    public Result downLoad(@PathVariable("imgPath") String fileName, HttpServletResponse response){
        try {
            response.addHeader("Content-Disposition", "attachment:filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
            OutputStream os = response.getOutputStream();
            String realPath = filePath + fileName;
            byte[] bytes = FileUtil.readBytes(realPath);
            os.write(bytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success();
    }
}
