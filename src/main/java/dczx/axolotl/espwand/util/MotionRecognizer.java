package dczx.axolotl.espwand.util;

import dczx.axolotl.espwand.entity.XYZ;

import java.util.List;


/**
 * @author AxolotlXM
 * @version 1.0
 * @since 2024/7/15 15:57
 */
public class MotionRecognizer {


    public static String recognizeAction(List<XYZ> data) {
        double yMean = data.stream().mapToDouble(p -> p.y).average().orElse(0.0);
        double zMean = data.stream().mapToDouble(p -> p.z).average().orElse(0.0);

        double yVariance = data.stream().mapToDouble(p -> Math.pow(p.y - yMean, 2)).sum() / data.size();
        double zVariance = data.stream().mapToDouble(p -> Math.pow(p.z - zMean, 2)).sum() / data.size();
        double xVariance = data.stream().mapToDouble(p -> Math.pow(p.x - data.get(0).x, 2)).sum() / data.size();


        double yAverage = data.stream().mapToDouble(XYZ::getX).average().getAsDouble();
        double zAverage = data.stream().mapToDouble(XYZ::getY).average().getAsDouble();
        double xAverage = data.stream().mapToDouble(XYZ::getZ).average().getAsDouble();

        System.out.println("============================================");
        System.out.println("xVariance = " + xVariance);
        System.out.println("yVariance = " + yVariance);
        System.out.println("zVariance = " + zVariance);

        System.out.println();

        System.out.println("yAverage = " + yAverage);
        System.out.println("zAverage = " + zAverage);
        System.out.println("xAverage = " + xAverage);
        System.out.println("============================================");
        final double recognizeBase = 0.3;//越高，其贴合度越高 越容易被识别为一个动作

        if (
                isStatic(xAverage, 2)
                        && isStatic(yAverage, 2)
                        && isStatic(zAverage, 2)
                        && isStatic(xVariance, 1)
                        && isStatic(yVariance, 1)
                        && isStatic(zVariance, 1)
        ) {
            return "水平静止";
        }
        if (yVariance > 100 && zVariance < yVariance * recognizeBase && xVariance < yVariance * recognizeBase) {
            return "竖直平面内点双击";
        }
        if (yVariance > 50 && zVariance < yVariance * recognizeBase && xVariance < yVariance * recognizeBase) {
            return "竖直平面内点击";
        }
        return "错误";
    }

    private static boolean isStatic(double value, double range) {
        return Math.abs(value) < Math.abs(range);
    }


}