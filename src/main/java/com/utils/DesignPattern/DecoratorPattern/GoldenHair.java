package com.utils.DesignPattern.DecoratorPattern;
// 下面以美国女孩示例
// 给美国女孩加上金发
public class GoldenHair extends GirlDecorator {

    private Girl girl;

    public GoldenHair(Girl g) {
        girl = g;
    }

    @Override
    public String getDescription() {
        return girl.getDescription() + "+with golden hair";
    }

}