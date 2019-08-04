package me.qping.utils;

import static org.junit.Assert.assertTrue;

import me.qping.utils.tree.TreeModel;
import me.qping.utils.tree.TreeUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AppTest
{
    @Test
    public void test() {

        TreeUtil treeUtil = TreeUtil.builder()
                                .id("id")
                                .parentId("parent_id")
                                .build();

        List<TreeModel> list = new ArrayList<>();
        list.add(new Measure("1", "0", "测试1", 100));
        list.add(new Measure("2", "0", "测试2", 99));
        list.add(new Measure("3", "1", "测试3", 99));
        list.add(new Measure("4", "1", "测试4", 99));
        list.add(new Measure("5", "2", "测试5", 98));
        list.add(new Measure("6", "2", "测试6", 99));
        list.add(new Measure("7", "3", "测试7", 99));
        list.add(new Measure("8", "4", "测试8", 99));
        list.add(new Measure("9", "5", "测试9", 99));
        list.add(new Measure("10", "5", "测试0", 99));

        List<TreeModel> tree = treeUtil.treeByRootId("0", list, Measure.class);

        System.out.println(tree.size());
    }
}
