package me.qping.utils.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TreeUtil
 * @Description 树对象生成工具
 * @Author qping
 * @Date 2019/6/19 18:16
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeUtil {

    String nameId = "id";
    String nameParentId = "parentId";

    public static TreeUtilBuilder builder(){
        return new TreeUtilBuilder();
    }

    /**
     * 自己定义id和parentId对应的字段名后，将 orginList 转换为 树形结构
     *
     * {
     *     leaf: false,
     *     ....,                    // bean 的属性
     *
     *     children: [
     *          {....},             // 子节点
     *          {....}
     *     ]
     * }
     *
     * @param rootId
     * @param orginList
     * @param clazz
     * @return
     */
    public List<TreeModel> treeByRootId(Object rootId, List<? extends TreeModel> orginList, Class clazz) {

        try {
            Field fieldId = clazz.getDeclaredField(nameId);
            Field fieldParentId = clazz.getDeclaredField(nameParentId);
            fieldId.setAccessible(true);
            fieldParentId.setAccessible(true);

            return treeByRootId(rootId, orginList, fieldId, fieldParentId, 1);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<TreeModel> treeByRootId(Object rootId, List<? extends TreeModel> orginList, Field fieldId, Field fieldParentId, int level) throws IllegalAccessException {

        List<TreeModel> tree = new ArrayList<>();

        for(TreeModel obj : orginList){
            Object parentId = fieldParentId.get(obj);
            Object id = fieldId.get(obj);

            if((rootId == null && null == parentId ) || (rootId != null && rootId.equals(parentId))) {
                List<TreeModel> children = treeByRootId(id, orginList, fieldId, fieldParentId, level + 1);
                if(children.size() > 0){
                    obj.setChildren(children);
                    obj.setLeaf(false);
                } else {
                    obj.setLeaf(true);
                }
                tree.add(obj);
            }
        }
        return tree;
    }

    /**
     * 遍历一棵树，通过 TreeConsumer 接口实现对每个节点的访问
     * @param tree
     * @param consumer
     * @param parent
     */
    public void trace(List<TreeModel> tree, TreeConsumer consumer, TreeModel parent){
        for(TreeModel treeModel : tree){
            consumer.call(treeModel, parent);
            if(treeModel.getChildren() != null && treeModel.getChildren().size() > 0){
                trace(treeModel.getChildren(), consumer, treeModel);
            }
        }
    }


    /**
     * 一个 list 包含不同表的对象时，通过统一实现 TreeModelAssemble 中接口，实现将不同表对象的集合拼装成一个树形结构
     *
     * {
     *     treeNodeId: 1,
     *     treeNodeParentId: 0,
     *     treeNodeName: "节点",
     *     treeNodeType: null,
     *     leaf: false,
     *     ....,                    // bean 的属性
     *     children: [
     *          {....},             // 子节点
     *          {....}
     *     ]
     * }
     *
     * @param rootId
     * @param orginList
     * @return
     */
    public List<TreeModelAssemble> treeAssembleByRootId(Object rootId, List<? extends TreeModelAssemble> orginList){
        return treeAssembleByRootId(rootId, orginList, 1);

    }

    private List<TreeModelAssemble> treeAssembleByRootId(Object rootId, List<? extends TreeModelAssemble> orginList, int level) {

        List<TreeModelAssemble> tree = new ArrayList<>();

        for(TreeModelAssemble obj : orginList){
            Object parentId = obj.getTreeNodeParentId();
            Object id = obj.getTreeNodeId();

            if((rootId == null && null == parentId ) || (rootId != null && rootId.equals(parentId))) {
                List<TreeModelAssemble> children = treeAssembleByRootId(id, orginList, level + 1);
                if(children.size() > 0){
                    obj.setChildren(children);
                    obj.setLeaf(false);
                } else {
                    obj.setLeaf(true);
                }
                tree.add(obj);
            }
        }
        return tree;
    }

}
