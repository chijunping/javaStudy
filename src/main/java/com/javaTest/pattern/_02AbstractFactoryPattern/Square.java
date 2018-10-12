package com.javaTest.pattern._02AbstractFactoryPattern;


/*
步骤 3
创建一个工厂，生成基于给定信息的实体类的对象。
 */
class Square implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
