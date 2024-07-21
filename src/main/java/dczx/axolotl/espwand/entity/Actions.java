package dczx.axolotl.espwand.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @author AxolotlXM
 * @version 1.0
 * @since 2024/7/16 23:37
 */
@AllArgsConstructor
@ToString
public enum Actions {
    STATIC("水平面内静止"),CLICK("竖直平面内点击"),NULL("错误动作,无法识别");
    final String note;
}
