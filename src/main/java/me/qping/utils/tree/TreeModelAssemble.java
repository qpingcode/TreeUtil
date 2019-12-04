package me.qping.utils.tree;


import lombok.Data;

import java.util.List;

/**
 * 可拼装的树形结构
 * 解决当需要将多个表的数据整合为一个树的时候，id、parent、name 在各个表中对应不同字段的问题
 * 对于 bean 侵入性大，需要实现4个接口方法，故单独独立成一个接口
 */
@Data
public abstract class TreeModelAssemble {

    boolean isLeaf;

    List<TreeModelAssemble> children;

    public abstract Object getTreeNodeId();
    public abstract Object getTreeNodeParentId();
    public abstract String getTreeNodeName();
    public abstract String getTreeNodeType();
}
