package com.utils.DesignPattern.ObserverPattern;
//  测试一下
public class MainTest {

    public static void main(String[] args) {
        VideoSite vs = new VideoSite();
        vs.registerObserver(new VideoFans("LiLei"));
        vs.registerObserver(new VideoFans("HanMeimei"));
        vs.registerObserver(new VideoFans("XiaoMing"));

        // add videos
        vs.addVideos("Video 1");
        //vs.addVideos("Video 2");
    }
}