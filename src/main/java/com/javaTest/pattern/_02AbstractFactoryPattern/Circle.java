package com.javaTest.pattern._02AbstractFactoryPattern;

/*
步骤 4
使用该工厂，通过传递类型信息来获取实体类的对象。
 */
class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
