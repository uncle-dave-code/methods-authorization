package com.uncledavecode.methodsauthorization.controllers;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DemoController {
    @GetMapping("/demo")
    public String demo() {
        return "Hello World from Demo!!!";
    }

    //PreAuthorize
    @GetMapping("/preAuth1")
    @PreAuthorize("hasAuthority('write')")
    //https://docs.spring.io/spring-security/reference/servlet/authorization/expression-based.html
    public String preAuth1() {
        return "Hello World from PreAuth 1 !!!";
    }

    @GetMapping("/preAuth2/{param}")
    @PreAuthorize("""
            #param == authentication.principal.username and hasAuthority('write')
            """)
    public String preAuth2(@PathVariable("param") String param) {
        return param + "- Hello World from PreAuth 2 !!!";
    }

    @GetMapping("/preAuth3/{param}")
    @PreAuthorize("@conditionEvaluator.canPreAuth3(#param, authentication)")
    public String preAuth3(@PathVariable("param") String param) {
        return param + "- Hello World from PreAuth 3 !!!";
    }

    //PostAuthorize
    //Restrict access to the returned value
    @GetMapping("/postAuth1")
    @PostAuthorize("returnObject != 'postAuth1'")
    public String postAuth1() {
        System.out.println("**postAuth1**");
        return "postAuth1";
    }

    //PreFilter
    @GetMapping("/preFilter1")
    @PreFilter("filterObject.startsWith(authentication.principal.username)")
    public String preFilter1(@RequestBody List<String> values) { //the first parameter must be a collection
        System.out.println("Values: " + values);
        return "preFilter1";
    }

    //PostFilter
    //The return type must be a collection, can't be an immutable collection
    @GetMapping("/postFilter1")
    @PostFilter("filterObject.startsWith(authentication.principal.username)")
    public List<String> postFilter1() {
        //List<String> values = List.of("uncledave - Object1", "user - Object2", "uncledave - Object3", "user - Object4");
        List<String> values = new ArrayList<>();
        values.add("uncledave - Object1");
        values.add("user - Object2");
        values.add("uncledave - Object3");
        values.add("user - Object4");
        System.out.println("Values: " + values);
        return values;
    }

}
