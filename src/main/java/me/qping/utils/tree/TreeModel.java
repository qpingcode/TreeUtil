package me.qping.utils.tree;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.Collator;
import java.util.List;

/**
 * @ClassName TreeModel
 * @Description 树接口实现(id 和 parentid 为 int 类型)
 * @Author qping
 * @Date 2019/6/19 18:16
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
public class TreeModel {
    boolean isLeaf;
    List<TreeModel> children;
}
