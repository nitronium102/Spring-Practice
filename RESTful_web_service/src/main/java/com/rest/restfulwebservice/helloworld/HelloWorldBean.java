package com.rest.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lombok -> Bean 만들 때 setter, getter, 생성자 자동
@Data
@AllArgsConstructor // 모든 arg를 가지고 있는 생성자
@NoArgsConstructor // 디폴트 생성자
public class HelloWorldBean {

    private String message;

    /*@AllArgsConstructor
    public HelloWorldBean(String hello_world_bean) {
        this.message = message;
    */
}
