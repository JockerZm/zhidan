package com.zm.zhidan.ypxx.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zm.zhidan.ypxx.handler.StyleExcelHandler;
import com.zm.zhidan.util.Util;
import com.zm.zhidan.ypxx.domain.Ypxx;
import com.zm.zhidan.ypxx.handler.YpxxHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
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
        String formData = parse.get("formData").toString();
        if (formData.equals("") || formData == "") {
            return ypxxHandler.queryYpxxList(1, Util.PAGECOUNT);
        }else {
            Long bianMa = Long.parseLong(formData);
            return ypxxHandler.queryYpxxByBianMa(bianMa);
        }
    }

    /**
     * 批量导入药品信息
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux<Ypxx> uploadExcel(@RequestPart("file") FilePart file) {
        InputStream inputStream = null;
        String nowDate1 = new SimpleDateFormat("yyyy-MM-dd-HHmmssSSS").format(new Date());
        String filePath = fileUploadPath + File.separator + "上传"+nowDate1 + ".xlsx";
        File uploadFile = new File(filePath);
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
                ypxx.setId(UUID.randomUUID().toString());
                ypxx.setCreateTime(nowDate);
                ypxx.setUpdateTime(nowDate);
                ypxxList.add(ypxx);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
//                uploadFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ypxxHandler.saveAllYpxx(ypxxList);
    }

    /**
     *  生成excel文件
     * @param param
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/getExcelFile")
    public Mono<String> getExcelFile(@RequestBody String param){
        JSONObject parse = (JSONObject) JSON.parse(param);
        String nowDate = new SimpleDateFormat("yyyy-MM-dd-HHmmssSSS").format(new Date());
        String cartsMoney = (String)parse.get("cartsMoney");
        List cartsProducts = (List<JSONObject>) parse.get("cartProducts");

        List<List<String>> excelData = ypxxHandler.getExcelData(cartsProducts, cartsMoney);
        List<List<String>> excelHead = ypxxHandler.getExcelHead();
        Map excelWidth = ypxxHandler.getExcelWidth();
        //文件名
        String filePath = fileUploadPath + File.separator + nowDate + ".xlsx";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            StyleExcelHandler hanlder = new StyleExcelHandler();
            ExcelWriter writer = new ExcelWriter(null, fileOutputStream, ExcelTypeEnum.XLSX, true, hanlder);

            Sheet sheet1 = new Sheet(1, 3);
            sheet1.setSheetName("第一个sheet");
            sheet1.setColumnWidthMap(excelWidth);
            sheet1.setAutoWidth(Boolean.TRUE);

            sheet1.setHead(excelHead);
            writer.write0(excelData, sheet1);

            //关闭资源
            writer.finish();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            System.out.println(e);

        }
        return Mono.just(filePath);
    }

    /**
     *  下载excel文件
     * @param filePath
     * @param response
     * @return
     */
    @GetMapping("/downloadExcel")
    public Mono<Void> downloadExcel(@RequestParam("filePath") String filePath,ServerHttpResponse response) {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;

        File file = new File(filePath);
        String fileName = null;
        try {
            fileName = new String(file.getName().getBytes(), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        zeroCopyResponse.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        zeroCopyResponse.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        zeroCopyResponse.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return zeroCopyResponse.writeWith(file,0,file.length());
    }

}
