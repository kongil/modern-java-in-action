package chap02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FilteringApples {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        );

        //2.1.1
        List<Apple> greenApples = filterGreenApples(inventory);
        System.out.println("greenApples = " + greenApples);

        List<Apple> heavyApples = filterHeavyApples(inventory);
        System.out.println("heavyApples = " + heavyApples);

        //2.1.2
        List<Apple> greenApples2 = filterApplesByColor(inventory, Color.GREEN);
        System.out.println("greenApples2 = " + greenApples2);

        List<Apple> heavyApples2 = filterApplesByWeight(inventory, 150);
        System.out.println("heavyApples2 = " + heavyApples2);

        //2.1.3
        List<Apple> greenApples3 = filterApples(inventory, Color.GREEN, 0, true);
        System.out.println("greenApples3 = " + greenApples3);
        
        List<Apple> heavyApples3 = filterApples(inventory, null, 150, false);
        System.out.println("heavyApples3 = " + heavyApples3);

        //2.2.1
        List<Apple> greenApples4 = filterApples(inventory, new AppleGreenColorPredicate());
        System.out.println("greenApples4 = " + greenApples4);

        List<Apple> heavyApples4 = filterApples(inventory, new AppleHeavyWeightPredicate());
        System.out.println("heavyApples4 = " + heavyApples4);

        //2.3.1. 익명 클래스
        //2.3.2 다선 번째 시도 : 익명 클래스 사용 -> 여전히 불필요한 긴 코드
        List<Apple> redApple = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return Color.RED.equals(apple.getColor());
            }
        });

        //2.3.3. 여섯 번쨰 시도 : 람다 표현식 사용
        List<Apple> result = filterApples(inventory, (Apple apple) -> Color.RED.equals(apple.getColor()));
    }
    //2.1.1. 첫 번쨰 시도 : 녹색 사과 필터링 -> 빨간 사과도 필터링 하고 싶어졌따. 거의 비슷한 코드가 반복 존재한다면 그 코드를 추상화 한다.
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (Color.GREEN.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }
    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > 150) {
                result.add(apple);
            }
        }
        return result;
    }
    // 2.1.2. 두번쨰 시도 : 색을 파라미터화 -> 색 구분, 무게 구분 필터링 조건을 적용하는 부분의 코드가 겹친다.
    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor() == color) {
                result.add(apple);
            }
        }
        return result;
    }
    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    // 2.1.3. 세 번째 시도: 가능한 모든 속성으로 필터링 -> 형편 없다.
    public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ((flag && apple.getColor().equals(color)) ||
                    !flag && apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    // 2.2. 동작 파라미터화
    //2.2.1. 네 번쨰 시도 : 추상적 조건으로 필터링 -> 여러 클래스를 정의한 다음에 인스턴스화, 번거롭다
    public interface ApplePredicate {
        boolean test(Apple apple);
    }

    public static class AppleHeavyWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }
    public static class AppleGreenColorPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return Color.GREEN.equals(apple.getColor());
        }
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public class AppleRedAndHeavyPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return Color.RED.equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }

    //2.3.1 익명 클래스
    //2.3.2 다선 번째 시도 : 익명 클래스 사용 -> 여전히 불필요한 긴 코드
    //2.3.3. 여섯 번쨰 시도 : 람다 표현식 사용

    //2.3.4. 일곱 번째 시도 : 리스트 형식으로 추상화
    public interface Predicate<T> {
        boolean test(T t);
    }
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T e: list) {
            if (p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    enum Color {GREEN, RED}

    public static class Apple {
        private int weight = 0;
        private Color color;

        public Apple(int weight, Color color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "weight=" + weight +
                    ", color='" + color + '\'' +
                    '}';
        }
    }
}
