package com.ritian.designpattern.behaviortype.iterator;

/**
 * 迭代模式：用于顺序访问集合对象的元素，不需要知道集合对象的底层表示。
 * <p>
 * 主要解决：不同的方式来遍历整个整合对象。
 * <p>
 * 何时使用：遍历一个聚合对象。
 * <p>
 * 如何解决：把在元素之间游走的责任交给迭代器，而不是聚合对象。
 * <p>
 * 关键代码：定义接口：hasNext, next。
 * <p>
 * 应用实例：JAVA 中的 iterator。
 * <p>
 * 优点： 1、它支持以不同的方式遍历一个聚合对象。
 * 2、迭代器简化了聚合类。
 * 3、在同一个聚合上可以有多个遍历。
 * 4、在迭代器模式中，增加新的聚合类和迭代器类都很方便，无须修改原有代码。
 * <p>
 * 缺点：由于迭代器模式将存储数据和遍历数据的职责分离，增加新的聚合类需要对应增加新的迭代器类，类的个数成对增加，这在一定程度上增加了系统的复杂性。
 * <p>
 * 使用场景： 1、访问一个聚合对象的内容而无须暴露它的内部表示。
 * 2、需要为聚合对象提供多种遍历方式。
 * 3、为遍历不同的聚合结构提供一个统一的接口。
 *
 * @author ritian.Zhang
 * @date 2019/05/04
 **/
public class IteratorDemo {
    public static void main(String[] args) {

        Collection collection = new MyCollection();
        Iterator it = collection.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

    }
}

interface Collection {
    public Iterator iterator();

    //获得集合元素
    public Object get(int i);

    //大小
    public int size();
}

interface Iterator {
    public Object previous();

    public Object next();

    public boolean hasNext();

    public Object first();
}

class MyCollection implements Collection {

    public String[] array = {"A", "B", "C", "D", "E"};

    @Override
    public Iterator iterator() {
        return new MyIterator(this);
    }

    @Override
    public Object get(int i) {
        return array[i];
    }

    @Override
    public int size() {
        return array.length;
    }
}

class MyIterator implements Iterator {

    private Collection collection;
    private int pos = -1;

    public MyIterator(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Object previous() {
        if (pos > 0) {
            pos--;
        }
        return collection.get(pos);
    }

    @Override
    public Object next() {
        if (pos < collection.size()) {
            pos++;
        }
        return collection.get(pos);
    }

    @Override
    public boolean hasNext() {
        if (pos < collection.size() - 1) {
            return true;
        }
        return false;
    }

    @Override
    public Object first() {
        pos = 0;
        return collection.get(pos);
    }
}
