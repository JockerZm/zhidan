package com.zm.zhidan.ypxx.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zm.zhidan.util.Util;
import com.zm.zhidan.ypxx.domain.Ypxx;
import com.zm.zhidan.ypxx.handler.YpxxHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONStringWithDateFormat;

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
        String filePath = fileUploadPath + "/" + System.currentTimeMillis() + ".xlsx";
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
        }finally {
            try {
                inputStream.close();
                uploadFile.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ypxxHandler.saveAllYpxx(ypxxList);
    }


}
