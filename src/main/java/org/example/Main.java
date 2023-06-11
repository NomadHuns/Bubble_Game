package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Dog {
    private String name;
}

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();

        dog.setName("멍멍이");

        System.out.println(dog.getName());
    }
}