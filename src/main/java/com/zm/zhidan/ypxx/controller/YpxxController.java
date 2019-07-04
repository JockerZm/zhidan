package com.zm.zhidan.ypxx.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zm.zhidan.util.Util;
import com.zm.zhidan.ypxx.domain.Ypxx;
import com.zm.zhidan.ypxx.handler.YpxxHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.alibaba.fastjson.JSON.parseObject;

@RestController
@RequestMapping(value = "/zhidan/ypxx")
@CrossOrigin(value = "*", maxAge = 1800, allowedHeaders = "*")
public class YpxxController {

    @Autowired
    private YpxxHandler ypxxHandler;

    @Value("${fileupload.path}")
    private String fileUploadPath;

    /**
     * 保存药品信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/addOrUpadteYpxx", method = RequestMethod.POST)
    public Mono<Ypxx> addOrUpadteYpxx(@RequestBody String param) {
        JSONObject parse = (JSONObject) JSON.parse(param);
        String formData = parse.get("formData").toString();
        Ypxx ypxx = parseObject(formData, Ypxx.class);
        String nowDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        ypxx.setCreateTime(nowDate);
        ypxx.setUpdateTime(nowDate);
        return ypxxHandler.addOrUpadteYpxx(ypxx);
    }

    /**
     * 查询药品信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/queryYpxxList")
    public Flux<Ypxx> queryYpxxList(@RequestBody String param) {
        JSONObject parse = (JSONObject) JSON.parse(param);
        Integer page = Integer.valueOf(parse.get("page").toString());
        String nowDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        return ypxxHandler.queryYpxxList(page, Util.PAGECOUNT);
    }

    /**
     * 删除药品
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/deleteYpxx", method = RequestMethod.POST)
    public Mono<Void> deleteYpxx(@RequestBody String param) {
        JSONObject parse = (JSONObject) JSON.parse(param);
        String formData = parse.get("formData").toString();
        Type type = new TypeReference<List<Ypxx>>() {
        }.getType();
        List<Ypxx> list = JSON.parseObject(formData, type);
//        Ypxx ypxx = parseObject(formData, Ypxx.class);
        return ypxxHandler.deleteYpxx(list);
    }


    /**
     * 查询药品编码对应的药品信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/queryYpxxByBianMa", method = RequestMethod.POST)
    public Flux<Ypxx> queryYpxxByBianMa(@RequestBody String param) {
        JSONObject parse = (JSONObject) JSON.parse(param);
        Long bianMa = Long.parseLong(parse.get("formData").toString());
        return ypxxHandler.queryYpxxByBianMa(bianMa);
    }

    /**
     * 查询药品编码对应的药品信息
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux<Ypxx> uploadExcel(@RequestPart("file") FilePart file) {
        InputStream inputStream = null;
        String filePath = fileUploadPath + File.separator + System.currentTimeMillis() + ".xlsx";
        File uploadFile = new File(filePath);
//        file.transferTo(Paths.get(filePath));
        List<Ypxx> ypxxList = new ArrayList<>();
        file.transferTo(uploadFile);
        try {
            inputStream = new FileInputStream(filePath);
            List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 1));
            System.out.println(data);
            for (int i = 0; i < data.size(); i++) {
                List oneData = (ArrayList) data.get(i);
                Ypxx ypxx = new Ypxx();
                ypxx.setBianma(oneData.get(0).toString());
                ypxx.setPinming(oneData.get(1).toString());
                ypxx.setGuige(oneData.get(2).toString());
                ypxx.setPihao(oneData.get(3).toString());
                ypxx.setYouxiaoqi(oneData.get(4).toString());
                ypxx.setDanwei(oneData.get(5).toString());
                ypxx.setShuliang(oneData.get(6).toString());
                ypxx.setDanjia(oneData.get(7).toString());
                ypxx.setShengchanchangjia(oneData.get(8).toString());
                ypxx.setPizhunwenhao(oneData.get(9).toString());
                String nowDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                ypxx.setId(System.currentTimeMillis() + i);
                ypxx.setCreateTime(nowDate);
                ypxx.setUpdateTime(nowDate);
                ypxxList.add(ypxx);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                uploadFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ypxxHandler.saveAllYpxx(ypxxList);
    }

    /**
     * 查询药品编码对应的药品信息
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/downloadExcel", method = RequestMethod.POST)
    public ResponseEntity<Object> downloadExcel(@RequestBody String param, ServerHttpResponse response) throws IOException {
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //文件名
        String fileName ="制单"+ nowDate + UUID.randomUUID().toString() + ".xlsx";
        //文件全路径
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileName));
        response.getHeaders().setContentType(MediaType.valueOf("application/vnd.ms-excel;charset=UTF-8"));

        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        zeroCopyResponse.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileName));
        zeroCopyResponse.getHeaders().setContentType(MediaType.valueOf("application/vnd.ms-excel;charset=UTF-8"));
        //解析数据
        JSONObject parse = (JSONObject) JSON.parse(param);
        String cartsMoney = parse.get("cartsMoney").toString();
        List<Map<String, String>> cartProducts = (List<Map<String, String>>) parse.get("cartProducts");

        ClassPathResource fileResource = new ClassPathResource(fileUploadPath + File.separator + fileName);
        System.out.println(fileResource.toString());
        File uploadPath = new File(fileUploadPath);
        if(!uploadPath.exists()){
            uploadPath.mkdir();
        }
        File file = new File(fileUploadPath, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

         try {
             FileOutputStream fileOutputStream = new FileOutputStream(file);
             ExcelWriter writer = new ExcelWriter(fileOutputStream, ExcelTypeEnum.XLSX, true);


            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("第一个sheet");
            ArrayList<List<String>> lists = new ArrayList<>();
//            writer.write0(ypxxHandler.getListString(), sheet1);
            writer.write0(lists, sheet1);
            writer.finish();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        File file1 = fileResource.getFile();

//        return ypxxHandler.queryYpxxByBianMa(10000000);
//        return zeroCopyResponse.writeWith(file, 0, file.length());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel")
//                .header(HttpHeaders.CONTENT_LENGTH, file.length())
                .header("Connection", "close")
                .body(file);
    }



}
