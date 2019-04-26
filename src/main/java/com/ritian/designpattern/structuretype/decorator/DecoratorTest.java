package com.ritian.designpattern.structuretype.decorator;

/**
 * 装饰模式
 * 就是对已经存在的某些类进行装饰，以此来扩展一些功能。
 * <a>参考链接：https://www.cnblogs.com/jzb-blog/p/6717349.html</a>
 * @author ritian.Zhang
 * @date 2019/04/26
 **/
public class DecoratorTest {
    public static void main(String[] args) {
        Decorator decorator = new ContreteDecorator(new ConcretComponent());
        decorator.biu();
    }
}
interface Component {
    //基础接口
    public void biu();
}

/**
 * 具体实现类
 */
class ConcretComponent implements Component{

    @Override
    public void biu() {
        System.out.println("biu biu");
    }
}

/**
 * 装饰类
 */
class Decorator implements Component{

    public   Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void biu() {
        this.component.biu();
    }
}
class ContreteDecorator extends Decorator{

    public ContreteDecorator(Component component) {
        super(component);
    }
    @Override
    public void biu(){
        System.out.println("ready? go");
        this.component.biu();
    }

}
