package dczx.axolotl.espwand.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import dczx.axolotl.espwand.entity.Actions;
import dczx.axolotl.espwand.entity.XYZ;
import dczx.axolotl.espwand.util.MotionRecognizer;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
public class ESPController {
    @SneakyThrows
    @RequestMapping(path = "/pushData")
    public ResponseEntity<String> CalData(@RequestBody JSONObject jsonObject) {
        JSONArray array = jsonObject.getJSONArray("xyz");//数组对象
        List<XYZ> list = array.toList(XYZ.class);//集合

        list.forEach(p -> p.z -= 9.8);//消去重力加速度


        System.out.println("list = " + list);


        AtomicInteger i = new AtomicInteger(0);
        list.forEach(xyz -> {
            i.addAndGet(1);
            System.out.println("%d\t%s".formatted(i.intValue(), xyz));
        });


//        list.forEach(e -> {
//            System.out.println("%.2f %.2f %.2f".formatted(e.getX(), e.getY(), e.getZ()));
//        });


        List<Actions> actions = MotionRecognizer.recognizeAction(list);
        if (actions.isEmpty()) actions.add(Actions.NULL);
        System.out.println("识别动作: " + actions);
        if (actions.contains(Actions.CLICK)) {
            isOpen = !isOpen;
        }


//        File dir = new File("G:\\CreativeJava\\2024_07\\ESPWandPy\\data\\circle");
//        File file = new File(dir, "circle.csv");
//        FileWriter writer = new FileWriter(file, true);
//
//        IOUtils.write(list.stream().map(XYZ::show).collect(Collectors.joining(",")) + ",circle", writer);
//        writer.flush();
//        writer.close();

        return ResponseEntity.ok("OK");
    }


    static boolean isOpen = false;

    @RequestMapping(path = "/isOpen")
    public static ResponseEntity<String> isOpen() {
        return isOpen ?
                ResponseEntity.ok("开灯") :
                ResponseEntity.ok("关灯");
    }

    @RequestMapping(path = "/setOpen")
    public static ResponseEntity<String> setOpen(boolean open) {
        isOpen = open;
        System.out.println("Set to: " + isOpen);
        return ResponseEntity.ok("OK");
    }

    @RequestMapping(path = "/changeOpen")
    public static ResponseEntity<String> changeOpen() {
        isOpen = !isOpen;
        System.out.println("Change to: " + isOpen);
        return isOpen ?
                ResponseEntity.ok("开灯") :
                ResponseEntity.ok("关灯");
    }


}
