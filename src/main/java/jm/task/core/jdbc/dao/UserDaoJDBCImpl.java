package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.GetSQLQueryUsersTemplate;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (
                Statement statement = connection.createStatement();
        ) {
            statement.execute(GetSQLQueryUsersTemplate.CREATE_TABLE);
            System.out.println("Таблица успешно создана");
        }
        catch (SQLException e) {
            System.out.println("Таблица уже существует или у вас ошибка.");
            System.out.println(e);
        }
    }

    public void dropUsersTable() {
        try (
                Statement statement = connection.createStatement();
        ) {
            statement.execute(GetSQLQueryUsersTemplate.DROP_TABLE);
            System.out.println("Таблица успешно удалена");
        }
        catch (SQLException e) {
            System.out.println("Таблица уже не существует или у вас ошибка");
            System.out.println(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (
                PreparedStatement statement = connection.prepareStatement(GetSQLQueryUsersTemplate.INSERT_ONE_USER)
        ) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            statement.execute();
            System.out.println("User с именем "+name+" успешно добавлен.");
        }
        catch (SQLException e) {
            System.out.println("Произошли проблемы с добавлением User.");
            System.out.println(e);
        }
    }

    public void removeUserById(long id) {
        try (
                PreparedStatement statement = connection.prepareStatement(GetSQLQueryUsersTemplate.DELETE_USER_BY_ID);
        ) {
            statement.setLong(1, id);

            statement.execute();
            System.out.println("Пользователь удален.");
        }
        catch (SQLException e) {
            System.out.println("Произошла ошибка удаления пользователя.");
            System.out.println(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(GetSQLQueryUsersTemplate.SELECT_ALL_USERS);
        ) {
            while (result.next()) {
                Long id = result.getLong("id");
                String name = result.getString("name");
                String lastName = result.getString("lastName");
                Byte age = result.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);

                list.add(user);
            }
        }
        catch (SQLException e) {
            System.out.println("Произошла ошибка получения пользователей.");
            System.out.println(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        try (
                Statement statement = connection.createStatement();
        ) {
            statement.execute(GetSQLQueryUsersTemplate.DELETE_ALL_USERS);
            System.out.println("Таблица User успешно очищена.");
        }
        catch (SQLException e) {
            System.out.println("Произошла ошибка очищения таблицы");
            System.out.println(e);
        }
    }
}
