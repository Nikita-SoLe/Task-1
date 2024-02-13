package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDaoJDBCImpl DAO = new UserDaoJDBCImpl();
    private UserDaoHibernateImpl DAOHiber = new UserDaoHibernateImpl();

    public void createUsersTable() {
        DAOHiber.createUsersTable();
    }

    public void dropUsersTable() {
        DAOHiber.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        DAOHiber.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        DAOHiber.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return DAOHiber.getAllUsers();
    }

    public void cleanUsersTable() {
        DAOHiber.cleanUsersTable();
    }
}
