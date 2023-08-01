package com.github.HorvDani;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    record Person(String firstName, String lastName){
        @Override
        public String toString() {
            return firstName + " " + lastName;
        }
    }
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Main.Person("Lucy", "Van Pelt"), // Person is a static nested class in Main
                new Person("Sally", "Brown"),
                new Person("Linus", "van Pelt"),
                new Person("Peppermint", "Patty"),
                new Person("Charlie", "Brown")));

        // Using anonymous class
        var comparatorLastName = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.lastName().compareTo(o2.lastName());
            }
        };

        //We can use Comparator.comparing instead of lambda
        people.sort((o1, o2) -> o1.lastName().compareTo(o2.lastName()));
        System.out.println(people);
        
        interface EnhancedComparator<T> extends Comparator<T> {
            int secondLevel(T o1, T o2);
        }
        var comparatorMixed = new EnhancedComparator<Person>(){

            @Override
            public int compare(Person o1, Person o2) {
                int result = o1.lastName().compareTo(o2.lastName());
                return (result == 0 ? secondLevel(o1,o2) : result);
            }

            @Override
            public int secondLevel(Person o1, Person o2) {
                return o1.firstName().compareTo(o2.firstName());
            }
        };

        // We created sorting by first names if the people's last names match
        people.sort(comparatorMixed);
        System.out.println(people);
    }
}