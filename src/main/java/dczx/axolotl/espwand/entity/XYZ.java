package dczx.axolotl.espwand.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author AxolotlXM
 * @version 1.0
 * @since 2024/7/15 15:34
 */
@Data
@ToString
@AllArgsConstructor
public class XYZ {
    public double x;
    public double y;
    public double z;

    public String show() {
        return "%.2f,%.2f,%.2f".formatted(x, y, z);
    }
}
