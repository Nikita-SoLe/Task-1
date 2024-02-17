package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();

        User user1 = new User("Nikita", "Pantyukhin", (byte) 27);
        User user2 = new User("Ivan", "Ivanov", (byte) 20);
        User user3 = new User("Petr", "Petrov", (byte) 24);
        User user4 = new User("Maria", "Duch", (byte) 25);

        List<User> listIn = List.of(user1, user2, user3, user4);

        listIn.forEach(user -> service.saveUser(user.getName(), user.getLastName(), user.getAge()));

        List<User> userOut = service.getAllUsers();
        userOut.forEach(System.out::println);

        service.cleanUsersTable();

        service.dropUsersTable();

        try {
            Util.getConnection().close();
            Util.getFactory().close();
        } catch (Exception e) {
            System.out.println("Произошла ошибка закрытия соединения с БД.");
            System.out.println(e);
        }
    }
}
