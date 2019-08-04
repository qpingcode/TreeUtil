# List 转换为树形结构

### 场景说明

开发中经常会遇到在数据库中存储树时，储存每个节点，并通过父节点 id 关联，数据库表结构如下：

| id   | parent_id | name  | score |
| ---- | --------- | ----- | ----- |
| 1    | 0         | 节点1 | 100   |
| 2    | 1         | 节点2 | 20    |
| 3    | 1         | 节点3 | 80    |

在使用时可能需要转换树状结构，如下。可以使用本工具类完成转换。

```json
{
  id: 1,
  name: "节点1",
  score: 100,
  children: [
    {id: 2, name: "节点2", score: 20},
    {id: 3, name: "节点3", score: 80}
  ]
}
```

### 使用方法

在pom.xml中增加依赖

```xml
  <dependency>
    <groupId>me.qping.utils</groupId>
    <artifactId>TreeUtil</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
```

工具内置了TreeModel类，主要是为了给对象增加 children 属性，从而变成树形结构。

使用时自己的业务类继承 TreeModel。

```java
// 假设需要处理的业务类为 Measure
@Data
@AllArgsConstructor
public class Measure extends TreeModel {
    String id;
    String parent_id;
    String remark;
    int score;
}
```

然后通过 TreeUtil 来处理，测试用例如下。

```java
class Test{
    public static void main(String[] args){
      // id 为类中节点 id 属性名， parentId 为类中父节点 id 属性名。
      TreeUtil treeUtil = TreeUtil.builder()
                              .id("id")
                              .parentId("parent_id")
                              .build();
      
      List<Measure> list = new ArrayList<>();
      list.add(new Measure("1", "0", "测试1", 100));
      list.add(new Measure("2", "0", "测试2", 99));
      list.add(new Measure("3", "1", "测试3", 99));
      list.add(new Measure("4", "1", "测试4", 99));
      list.add(new Measure("5", "2", "测试5", 98));
      list.add(new Measure("6", "2", "测试6", 99));
      list.add(new Measure("7", "3", "测试7", 99));
      list.add(new Measure("8", "4", "测试8", 99));
      
      List<TreeModel> tree = treeUtil.treeByRootId("0", list, Measure.class);
      System.out.println(tree.size());
    }
}
```



# 遍历树

遍历一棵树，先访问父节点，再访问其子节点

```java
class Test{
    public static void main(String[] args){
      List<TreeModel> treeList = treeUtil.treeByRootId("0", list, Measure.class);
      treeUtil.trace(treeList, new TreeConsumer() {
          @Override
          public void call(TreeModel self, TreeModel parent) {
              Measure measure = (Measure) self;
              System.out.println(measure.getScore())
          }
      
      }, null);
    }
}
```