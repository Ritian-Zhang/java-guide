package com.ritian.designpattern.creationtype.builder;

/**
 * @author ritian.Zhang
 * @date 2019/04/22
 **/
public class BuilderTest {


    public static void main(String[] args) {
        //builder模式 director
        Builder builder = new CharacterBuilder();
        Director director = new Director(builder);
        Character character = director.createCharacter("基佬", "臭脸", "比基尼");
        System.out.println(character.showMsg());


        //非builder模式
        Computer computer = new Computer("cpu", "screen", "memory", "mainboard");

        //builder模式 省略director
        NewComputer newComputer = new NewComputer.Builder()
                .cpu("cpu1")
                .screen("screen")
                .memory("memory")
                .mainboard("mainboard")
                .build();
        System.out.println(computer.toString());
        System.out.println(newComputer.toString());



    }
}
