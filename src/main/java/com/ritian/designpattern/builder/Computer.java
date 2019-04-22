package com.ritian.designpattern.builder;

import lombok.ToString;

/**
 * 传统的构造类，当需要传入很多参数时，代码可读性会非常差
 * @author ritian.Zhang
 * @date 2019/04/19
 **/
@ToString
public class Computer {

    private String cpu;

    private String screen;

    private String memory;

    private String mainboard;

    public Computer(String cpu, String screen, String memory, String mainboard) {
        this.cpu = cpu;
        this.screen = screen;
        this.memory = memory;
        this.mainboard = mainboard;
    }
}
/**
 * 省略 director(指挥者)
 */
@ToString
class NewComputer {
    private String cpu;

    private String screen;

    private String memory;

    private String mainboard;


    public NewComputer() {
        //throw new RuntimeException("cant't init");
    }

    public NewComputer(Builder builder) {
        this.cpu = builder.cpu;
        this.screen = builder.screen;
        this.memory = builder.memory;
        this.mainboard = builder.mainboard;
    }

    public static final class Builder {
        private String cpu;

        private String screen;

        private String memory;

        private String mainboard;

        public Builder() {
        }


        public Builder cpu(String val) {
            this.cpu = val;
            return this;
        }

        public Builder screen(String val) {
            this.screen = val;
            return this;
        }

        public Builder memory(String val) {
            this.memory = val;
            return this;
        }

        public Builder mainboard(String val) {
            this.mainboard = val;
            return this;
        }

        public NewComputer build() {
            return new NewComputer(this);
        }

    }
}