package com.ritian.designpattern.structuretype.composite;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * 组合模式是为了表示那些层次结构，同时部分和整体也可能是一样的结构，常见的如文件夹或者树。
 *
 *
 * 组合模式涉及3个角色：
 * 抽象组件（Component）：组合中所有具体组件的抽象接口，用于规范组件的属性和行为，相当于树的节点（Node）。
 * 具体组件（Composite）：组合中每个具体的组件，相当于树的枝节点。
 * 未端具体组件（Leaf）：位于未端面的具体组件，没有子节点，相当于树的叶子节点。
 *
 * 优点： 1、高层模块调用简单。 2、节点自由增加。
 * 缺点：在使用组合模式时，其叶子和树枝的声明都是实现类，而不是接口，违反了依赖倒置原则。
 * 使用场景：部分、整体场景，如树形菜单，文件、文件夹的管理。
 * @author ritian.Zhang
 * @date 2019/04/27
 **/
public class CompositeDemo {
    public static void main(String[] args) {
        //声明若干节点
        Node root = new Composite("root");

        Node left = new Composite("left");
        Node l1 = new Leaf("left leaf 1");

        Node right = new Composite("right");
        Node r1 = new Leaf("right leaf 1");
        Node r2 =new Leaf("right leaf 2");

        //构造一棵树
        left.add(l1);
        right.add(r1);
        right.add(r2);

        root.add(left);
        root.add(right);

        Tree tree = new Tree();
        tree.setRoot(root);

        tree.show();

        //删除一个节点
        System.out.println("========================删除r2节点===============================");
        right.remove(r2);
        tree.show();



    }
}

/**
 * 抽象的组件对象,为组合中的对象声明接口，实现接口的缺省行为
 */
@Data
abstract class Node{
    //节点名
    public String name;

    public abstract void add(Node node);
    public abstract void remove(Node node);
    public abstract void display();
}

/**
 * 组合类
 */
class Composite extends Node{

    private List<Node> list = new ArrayList<>();

    public Composite(String name) {
        this.setName(name);
    }

    @Override
    public void add(Node node) {
         list.add(node);
    }

    @Override
    public void remove(Node node) {
         list.remove(node);
    }

    @Override
    public void display() {
        System.out.println(String.format("this is %s",this.getName()));
        list.forEach(v->v.display());
    }
}

/**
 * 叶子节点
 */
class Leaf extends Node{
    public Leaf(String name) {
        this.setName(name);
    }

    @Override
    public void add(Node node) {
        throw new RuntimeException("Leaf cannot add element!!");
    }

    @Override
    public void remove(Node node) {
        throw new RuntimeException("Leaf cannot remove element!!");
    }

    @Override
    public void display() {
        System.out.println(String.format("this is %s",this.getName()));
    }
}

/**
 * 树(客户)
 */
@Data
class Tree {
    private Node root;

    public void show(){
        root.display();
    }
}
