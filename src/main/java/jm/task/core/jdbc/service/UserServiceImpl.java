package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDaoJDBCImpl DAO = new UserDaoJDBCImpl();
    private UserDaoHibernateImpl DAOHiber = new UserDaoHibernateImpl();

    public void createUsersTable() {
        // DAO.createUsersTable();

        DAOHiber.createUsersTable();
    }

    public void dropUsersTable() {
        // DAO.dropUsersTable();

        DAOHiber.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        // DAO.saveUser(name, lastName, age);

        DAOHiber.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        // DAO.removeUserById(id);

        DAOHiber.removeUserById(id);
    }

    public List<User> getAllUsers() {
        // return DAO.getAllUsers();

        return DAOHiber.getAllUsers();
    }

    public void cleanUsersTable() {
        // DAO.cleanUsersTable();

        DAOHiber.cleanUsersTable();
    }
}
