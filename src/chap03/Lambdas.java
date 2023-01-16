package chap03;

import java.util.*;


public class Lambdas {
    public static void main(String[] args) {
        //람다
        /**
         * 익명
         * 함수
         * 전달
         * 간결성
         * (parameters) -> expression
         * (parameters) -> {statements;}
         */
        //3.2.1 함수형 인터페이스
        //3.2.2 함수 디스크립터
        //람다 표현식의 시그니처를 서술
        //
        // 람다로 거름
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        );

        // [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
        List<Apple> greenApples = filter(inventory, (Apple a) -> a.getColor() == Color.GREEN);
        System.out.println(greenApples);

        Comparator<Apple> c = (Apple a1, Apple a2) -> a1.getWeight() - a2.getWeight();

        // [Apple{color=GREEN, weight=80}, Apple{color=RED, weight=120}, Apple{color=GREEN, weight=155}]
        inventory.sort(c);
        System.out.println(inventory);
    }

    public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    interface ApplePredicate {

        boolean test(Apple a);
    }
}
