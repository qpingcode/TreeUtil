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

    public void trace(List<TreeModel> tree, TreeConsumer consumer, TreeModel parent){
        for(TreeModel treeModel : tree){
            consumer.call(treeModel, parent);
            if(treeModel.getChildren() != null && treeModel.getChildren().size() > 0){
                trace(treeModel.getChildren(), consumer, treeModel);
            }
        }
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

}
