package com.bjsts.manager.controller.document;

import com.bjsts.manager.core.constants.CharsetConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.enums.document.DocumentType;
import com.bjsts.manager.service.document.DocumentService;
import com.bjsts.manager.utils.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/document")
public class DocumentController extends AbstractController {

    @Autowired
    private DocumentService documentService;

    @Value("${file.external.url}")
    private String fileExternalUrl;

    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, String> upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String group = request.getParameter("group");
        DocumentType documentType = null;
        if (!StringUtils.isEmpty(group)) {
            documentType = DocumentType.get(group);
        }
        if (documentType == null) {
            documentType = DocumentType.DEFAULT;
        }

        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

                InputStream input = file.getInputStream();

                String fullFileDir = fileExternalUrl + File.separator + documentType.getEnglishName() + File.separator;
                if (!FileUtils.createDirectory(fullFileDir)) {
                    String message = "创建文件夹失败，请重试!";
                    map.put("message", message);
                    return map;
                }

                String fileName = File.separator + documentType.getEnglishName() + File.separator + UUID.randomUUID().toString() + "." + suffix;
                OutputStream output = new FileOutputStream(fileExternalUrl + fileName);
                IOUtils.copy(input, output);

                output.close();
                input.close();

                String message = "上传成功!";

                map.put("path", fileName);
                map.put("fileName", originalFilename);
                map.put("message", message);
            } catch (Exception e) {
                map.put("message", "上传失败 => " + e.getMessage());
            }
        } else {
            map.put("message", "上传失败，文件为空.");
        }
        return map;
    }

    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        DocumentEntity documentEntity = documentService.get(id);

        if (documentEntity != null) {
            String fileName = documentEntity.getName();
            fileName = new String(fileName.getBytes(CharsetConstants.CHARSET_DEFAULT), "ISO_8859_1");
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            File file=new File(fileExternalUrl + documentEntity.getUrl());
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
