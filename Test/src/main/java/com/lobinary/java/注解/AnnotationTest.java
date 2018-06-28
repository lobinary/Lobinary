package com.lobinary.java.注解;

public class AnnotationTest {

    public static void main(String[] args) {
        System.out.println("start");

    }

    class T{

        @LAnnotation
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
