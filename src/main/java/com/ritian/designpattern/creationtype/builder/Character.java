package com.ritian.designpattern.creationtype.builder;

/**
 * 类似于product
 * @author ritian.Zhang
 * @date 2019/04/22
 **/
public class Character {
     private String sex;
     private String face;
     private String clothes;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getClothes() {
        return clothes;
    }

    public void setClothes(String clothes) {
        this.clothes = clothes;
    }

    public String showMsg(){
        return "你创建了一个穿着"+this.clothes +"一副"+ face +"的"+ sex +"在微笑O(∩_∩)O哈哈~";
    }
}
interface Builder{
    public void setSex(String sex);
    public void setFace(String face);
    public void setClothes(String clothes);

    //提供同一个的创建Product的接口方法
    public Character build();
}

class CharacterBuilder implements Builder{

    private Character character = new Character();

    @Override
    public void setSex(String sex) {
        character.setSex(sex);
    }

    @Override
    public void setFace(String face) {
        character.setFace(face);
    }

    @Override
    public void setClothes(String clothes) {
        character.setClothes(clothes);
    }

    @Override
    public Character build() {
        return character;
    }
}
//创建Director 用来返回product
class Director{
   private Builder builder = null;

    public Director(Builder builder) {
        this.builder = builder;
    }
    public Character createCharacter(String sex, String face, String clothes){
        this.builder.setSex(sex);
        this.builder.setFace(face);
        this.builder.setClothes(clothes);
        return this.builder.build();
    }
}
