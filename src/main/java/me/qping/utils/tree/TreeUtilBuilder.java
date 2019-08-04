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
public class TreeUtilBuilder {

    String nameId;
    String nameParentId;

    public TreeUtilBuilder id(String fieldNameId){
        this.nameId = fieldNameId;
        return this;
    }

    public TreeUtilBuilder parentId(String fieldNameParentId){
        this.nameParentId = fieldNameParentId;
        return this;
    }



    public TreeUtil build(){
        return new TreeUtil(nameId, nameParentId);
    }


}
