package dczx.axolotl.espwand.util;

import dczx.axolotl.espwand.entity.Actions;
import dczx.axolotl.espwand.entity.XYZ;

import java.util.ArrayList;
import java.util.List;


/**
 * @author AxolotlXM
 * @version 1.0
 * @since 2024/7/15 15:57
 */
public class MotionRecognizer {


    public static List<Actions> recognizeAction(List<XYZ> data) {
        double yMean = data.stream().mapToDouble(p -> p.y).average().orElse(0.0);
        double zMean = data.stream().mapToDouble(p -> p.z).average().orElse(0.0);

        double yVariance = data.stream().mapToDouble(p -> Math.pow(p.y - yMean, 2)).sum() / data.size();
        double zVariance = data.stream().mapToDouble(p -> Math.pow(p.z - zMean, 2)).sum() / data.size();
        double xVariance = data.stream().mapToDouble(p -> Math.pow(p.x - data.get(0).x, 2)).sum() / data.size();


        double xAverage = data.stream().mapToDouble(XYZ::getX).average().getAsDouble();
        double yAverage = data.stream().mapToDouble(XYZ::getY).average().getAsDouble();
        double zAverage = data.stream().mapToDouble(XYZ::getZ).average().getAsDouble();

        System.out.println("============================================");
        System.out.println("xVariance = " + xVariance);
        System.out.println("yVariance = " + yVariance);
        System.out.println("zVariance = " + zVariance);

        System.out.println();

        System.out.println("xAverage = " + xAverage);
        System.out.println("yAverage = " + yAverage);
        System.out.println("zAverage = " + zAverage);
        System.out.println("============================================");

        final double maxVariance = 99999;
        final double recognizeBaseA = 1.5;//检测系数
        final double recognizeBaseB = 1.5;//检测系数


        List<Actions> actions = new ArrayList<>();//存储可能的动作

        if (
                isInRange(xAverage, 2)
                        && isInRange(yAverage, 2)
                        && isInRange(zAverage, 2)
                        && isInRange(xVariance, 1)
                        && isInRange(yVariance, 1)
                        && isInRange(zVariance, 1)
        ) {
            actions.add(Actions.STATIC);
        }
//        if (yVariance > 100 && zVariance < yVariance * recognizeBase && xVariance < yVariance * recognizeBase) {
//            return "竖直平面内点双击";
//        }
        if (
                Math.abs(yAverage) > Math.abs(xAverage) * recognizeBaseA
                        && isInRange(xVariance, 0, (yVariance + zVariance) / (recognizeBaseB * 2))
                        && isInRange(yVariance, xVariance * recognizeBaseB, maxVariance)
                        && isInRange(zVariance, xVariance * recognizeBaseB, maxVariance)

        ) {
            actions.add(Actions.CLICK);
        }
        return actions;
    }

    private static boolean isInRange(double value, double range) {
        return Math.abs(value) < Math.abs(range);
    }

    private static boolean isInRange(double value, double from, double to) {
        return Math.abs(value) > from && Math.abs(value) < to;
    }


}