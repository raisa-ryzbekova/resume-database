package ru.javawebinar.basejava;

public class Deadlock {

    static class Person {

        private String name;

        Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public synchronized void sayHi(Person person2) {
            System.out.format("%s: %s said hi back to me", this.name, person2.getName());
            person2.sayHiBack(this);
        }

        public synchronized void sayHiBack(Person person1) {
            System.out.format("%s: %s said hi back to me", this.name, person1.getName());
        }
    }

    public static void main(String[] args) {

        final Person person1 = new Person("Person1");
        final Person person2 = new Person("Person2");
        new Thread(() -> person1.sayHi(person2)).start();
        new Thread(() -> person2.sayHi(person1)).start();
    }
}