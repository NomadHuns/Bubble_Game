package org.example;

/*
 * default를 사용하면 인터페이스도 몸체가 있는 메소드를 만들 수 있다.
 * 어뎁터 패턴을 사용하면 다중상속이 되지 않기 때문에 default를 사용하는 것이 좋다.
 */
public interface Moveable {
    public abstract void left();
    public abstract void right();
    public abstract void up();
    default public void down() {};
    default public void attack() {};
}
