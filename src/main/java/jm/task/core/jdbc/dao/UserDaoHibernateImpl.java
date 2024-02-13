package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.GetSQLQueryTemplate;
import jm.task.core.jdbc.util.Util;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (
                Session session = Util.getFactory().openSession();
                ) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryTemplate.CREATE_TABLE).executeUpdate();
            transaction.commit();

            System.out.println("Таблица 'users' успешно создана.");
        } catch (Exception e) {
            System.out.println("Проблема с созданием таблицы в Базе Данных.");
            System.out.println(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (
                Session session = Util.getFactory().openSession();
        ) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryTemplate.DROP_TABLE).executeUpdate();
            transaction.commit();

            System.out.println("Таблица 'users' успешно была удалена.");
        } catch (Exception e) {
            System.out.println("Проблема с удаление таблицы в Базе Данных.");
            System.out.println(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (
                Session session = Util.getFactory().openSession();
        ) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryTemplate.INSERT_ONE_USER)
                            .setParameter(1, name)
                            .setParameter(2, lastName)
                            .setParameter(3, age)
                                    .executeUpdate();
            transaction.commit();

            System.out.println("User с именем "+name+" успешно добавлен.");
        }
        catch (Exception e) {
            System.out.println("Произошли проблемы с добавлением User.");
            System.out.println(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (
                Session session = Util.getFactory().openSession();
                ) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryTemplate.DELETE_USER_BY_ID)
                    .setParameter(1, id)
                            .executeUpdate();
            transaction.commit();

            System.out.println("Пользователь удален.");
        }
        catch (Exception e) {
            System.out.println("Произошла ошибка удаления пользователя.");
            System.out.println(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (
                Session session = Util.getFactory().openSession();
                ScrollableResults result = session
                        .createSQLQuery(GetSQLQueryTemplate.SELECT_ALL_USERS)
                        .scroll();
                ) {

            Transaction transaction = session.beginTransaction();

            while (result.next()) {
                Object[] res = result.get();

                BigInteger id = (BigInteger) res[0];
                String name = (String) res[1];
                String lastName = (String) res[2];
                Short age = (Short) res[3];

                User user = new User(name, lastName, age.byteValue());
                user.setId(id.longValue());

                list.add(user);
            }

            transaction.commit();
        }
        catch (Exception e) {
            System.out.println("Произошла ошибка получения пользователей.");
            System.out.println(e);
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (
                Session session = Util.getFactory().openSession();
        ) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(GetSQLQueryTemplate.DELETE_ALL_USERS).executeUpdate();
            transaction.commit();

            System.out.println("Таблица User успешно очищена.");
        }
        catch (Exception e) {
            System.out.println("Произошла ошибка очищения таблицы");
            System.out.println(e);
        }
    }
}

