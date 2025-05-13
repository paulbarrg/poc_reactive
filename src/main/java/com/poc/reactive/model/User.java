package com.poc.reactive.model;

//record nos permite crear metaclase con menos codigo, ya tiene los metodos get,set, tostring, etc
public record User(String id, String name) {}
