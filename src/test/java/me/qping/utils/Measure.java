package me.qping.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.qping.utils.tree.TreeModel;


/**
 * @ClassName Measure
 * @Description 指标
 * @Author qping
 * @Date 2019/6/18 15:45
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Measure extends TreeModel {
    String id;
    String parent_id;
    String remark;
    int score;
}
