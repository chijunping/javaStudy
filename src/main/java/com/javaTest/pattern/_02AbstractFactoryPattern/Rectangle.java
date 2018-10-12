package com.javaTest.pattern._02AbstractFactoryPattern;

/*
步骤 2
创建实现接口的实体类。
 */
class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
