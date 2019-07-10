package com.zm.zhidan.ypxx.handler;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.zm.zhidan.ypxx.dao.YpxxRepository;
import com.zm.zhidan.ypxx.domain.Ypxx;
import org.apache.poi.ss.formula.ThreeDEval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class YpxxHandler {

    private final YpxxRepository ypxxRepository;

    @Autowired
    public YpxxHandler(YpxxRepository ypxxRepository) {
        this.ypxxRepository = ypxxRepository;
    }

    public Mono<Ypxx> addOrUpadteYpxx(Ypxx ypxx) {
        Mono<Boolean> booleanMono = ypxxRepository.existsById(ypxx.getId());
        if (ypxx.getId().equals("")) {
            ypxx.setId(UUID.randomUUID().toString());
        } else {
            ypxxRepository.deleteById(ypxx.getId());
        }
        return ypxxRepository.save(ypxx);
    }

    public Flux<Ypxx> saveAllYpxx(List<Ypxx> ypxxList) {
        return ypxxRepository.saveAll(ypxxList);
    }

    public Flux<Ypxx> queryYpxxList(Integer pageNo, Integer pageCount) {
        long startNo = (pageNo - 1) * pageCount;
        //按照更新时间排序
        Sort orders = new Sort(Sort.Direction.DESC, "updateTime");
        return ypxxRepository.findAll(orders).skip(startNo).limitRequest(pageCount);
    }

    public Mono<Void> deleteYpxx(List<Ypxx> list) {
//        for (Ypxx ypxx : list) {
//            Mono<Void> voidMono = ypxxRepository.deleteById(ypxx.getId());
//        }
        return ypxxRepository.deleteAll(list);
    }


    public Flux<Ypxx> queryYpxxByBianMa(long id) {
        return ypxxRepository.findByBianmaIsLike(id);
    }


    public List<List<String>> getExcelData(List cartsProducts,String cartMoney) {
        String nowDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        List<List<String>> sheetData = new ArrayList<List<String>>();
        for (int i = 0; i < cartsProducts.size(); i++) {
            List<String> rowData = new ArrayList<>();
            JSONObject o = (JSONObject)cartsProducts.get(i);
            rowData.add(nowDate1);
            rowData.add((String) o.get("bianma"));
            rowData.add((String) o.get("pinming"));
            rowData.add((String) o.get("guige"));
            rowData.add((String) o.get("pihao"));
            rowData.add((String) o.get("youxiaoqi"));
            rowData.add((String) o.get("danwei"));
            rowData.add((String) o.get("num").toString());
            rowData.add((String) o.get("danjia"));
            rowData.add((String) o.get("total_num"));
            rowData.add((String) o.get("shengchanchangjia"));
            rowData.add((String) o.get("pizhunwenhao"));

            sheetData.add(rowData);
        }
        List<String> rowData = new ArrayList<>();
        rowData.add("");
        rowData.add("");
        rowData.add("");
        rowData.add("");
        rowData.add("");
        rowData.add("");
        rowData.add("");
        rowData.add("");
        rowData.add("合计：");
        rowData.add(cartMoney);
        rowData.add("");
        rowData.add("");
        sheetData.add(rowData);

        return sheetData;
    }

    public List<List<String>> getExcelHead() {
        List<List<String>> lists = new ArrayList<>();

        addExcelHead(lists, "河北神威大药房配送清单", "", "日期");
        addExcelHead(lists, "河北神威大药房配送清单", "客户：河北中兴冀能实业有限公司", "编码");
        addExcelHead(lists, "河北神威大药房配送清单", "客户：河北中兴冀能实业有限公司", "品名");
        addExcelHead(lists, "河北神威大药房配送清单", "客户：河北中兴冀能实业有限公司", "规格");
        addExcelHead(lists, "河北神威大药房配送清单", "客户：河北中兴冀能实业有限公司", "批号");
        addExcelHead(lists, "河北神威大药房配送清单", "客户：河北中兴冀能实业有限公司", "有效期");
        addExcelHead(lists, "河北神威大药房配送清单", "客户：河北中兴冀能实业有限公司", "单位");
        addExcelHead(lists, "河北神威大药房配送清单", "发货区域：圣诺三店", "数量");
        addExcelHead(lists, "河北神威大药房配送清单", "发货区域：圣诺三店", "单价");
        addExcelHead(lists, "河北神威大药房配送清单", "发货区域：圣诺三店", "金额");
        addExcelHead(lists, "河北神威大药房配送清单", "", "生产厂家");
        addExcelHead(lists, "河北神威大药房配送清单", "", "批准文号");
        return lists;
    }

    private void addExcelHead(List<List<String>> lists,String one,String two,String three) {
        List<String> column = new ArrayList<>();
        column.add(one);
        column.add(two);
        column.add(three);
        lists.add(column);
    }


    public Map getExcelWidth() {
        Map columnWidth = new HashMap();
        columnWidth.put(0, 3000);
        columnWidth.put(1, 3000);
        columnWidth.put(2, 3000);
        columnWidth.put(3, 3000);
        columnWidth.put(4, 3000);
        columnWidth.put(5, 3000);
        columnWidth.put(6, 1500);
        columnWidth.put(7, 1500);
        columnWidth.put(8, 3000);
        columnWidth.put(9, 3000);
        columnWidth.put(10, 3000);
        columnWidth.put(11, 3000);
        columnWidth.put(12, 3000);

        return columnWidth;

    }
}
