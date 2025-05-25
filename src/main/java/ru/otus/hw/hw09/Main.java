package ru.otus.hw.hw09;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Список чисел в диапазоне от 5 до 10: " + createRangeList(5, 10));

        try{
            System.out.println("Будет Exception: " + createRangeList(2, 1));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("Сумма элементов больше 5: " + sumElementsGreaterThanFive( createRangeList(3, 7) ));

        List<Integer> listToFill = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        System.out.println("Исходный список: " + listToFill);
        fillListWithValue(7, listToFill);
        System.out.println("Список после заполнения: " + listToFill);

        List<Integer> listToIncrease = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        System.out.println("Исходный список: " + listToIncrease);
        increaseListElements(3, listToIncrease);
        System.out.println("Список после увеличения каждого элемента на 3: " + listToIncrease);

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Иван", 25));
        employees.add(new Employee("Мария", 30));
        employees.add(new Employee("Алексей", 22));
        employees.add(new Employee("Екатерина", 28));
        
        System.out.println("Имена сотрудников: " + getEmployeeNames(employees));

        System.out.println("Сотрудники с возрастом от 28 лет:");
        filterEmployeesByAge(employees, 28)
            .forEach(System.out::println);

        System.out.println("Средний возраст сотрудников больше 27? " + isAverageAgeGreaterThan(employees, 27));

        System.out.println("Самый молодой сотрудник: " + getYoungestEmployee(employees));
    }

    private static List<Integer> createRangeList(int min, int max) {
        if(min > max) throw new IllegalArgumentException("min(%d) не может быть больше чем max(%d)".formatted(min, max));

        List<Integer> result = new ArrayList<>();
        
        for (int i = min; i <= max; i++) {
            result.add(i);
        }
        
        return result;
    }

    private static int sumElementsGreaterThanFive(List<Integer> numbers) {
        int sum = 0;
        
        for (Integer number : numbers) {
            if (number > 5) {
                sum += number;
            }
        }
        
        return sum;
    }

    private static void fillListWithValue(int value, List<Integer> list) {
        Collections.fill(list, value);
    }

    private static void increaseListElements(int value, List<Integer> list) {
        list.replaceAll(integer -> integer + value);
    }


    private static List<String> getEmployeeNames(List<Employee> employees) {
        List<String> names = new ArrayList<>();
        
        for (Employee employee : employees) {
            names.add(employee.getName());
        }
        
        return names;
    }

    private static List<Employee> filterEmployeesByAge(List<Employee> employees, int minAge) {
        List<Employee> filtered = new ArrayList<>();
        
        for (Employee employee : employees) {
            if (employee.getAge() >= minAge) {
                filtered.add(employee);
            }
        }
        
        return filtered;
    }

    private static boolean isAverageAgeGreaterThan(List<Employee> employees, int minAverageAge) {
        if (employees.isEmpty()) {
            return false;
        }
        
        int totalAge = 0;
        for (Employee employee : employees) {
            totalAge += employee.getAge();
        }
        
        double averageAge = (double) totalAge / employees.size();
        return averageAge > minAverageAge;
    }

    private static Employee getYoungestEmployee(List<Employee> employees) {
        if (employees.isEmpty()) {
            return null;
        }
        
        Employee youngest = employees.getFirst();
        
        for (Employee employee : employees) {
            if (employee.getAge() < youngest.getAge()) {
                youngest = employee;
            }
        }
        
        return youngest;
    }
}