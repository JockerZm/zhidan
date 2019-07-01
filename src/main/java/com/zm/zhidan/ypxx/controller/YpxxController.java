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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
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
    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
//    public Flux<Ypxx> uploadExcel(@RequestParam(name = "file", value = "file", required = false) MultipartFile uploadFile) {
    public Flux<Ypxx> uploadExcel(@RequestParam( value = "file", required = false) MultipartFile uploadFile) {
        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
            List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        JSONObject parse = (JSONObject)JSON.parse(param);
//        Long bianMa = Long.parseLong(parse.get("formData").toString());
        Long bianMa = 0L;
        return ypxxHandler.queryYpxxByBianMa(bianMa);
    }


}
