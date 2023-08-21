package school21.edu;

import school21.edu.models.Computer;
import school21.edu.models.User;
import school21.edu.ormmanager.OrmManager;
import school21.edu.ormmanager.OrmManagerImpl;

public class Main {

  public static void main(String[] args) {
    OrmManager ormManager = new OrmManagerImpl();
    User user = new User( "Rinat", "Klinkov", 31);
    User user1 = new User( "NeRinat", "NeKlinkov", 13);
    User user2 = new User( "Rinat", "Klinkov", 31);
    User user3 = new User( "NeRinat", "NeKlinkov", 13);
    User user4 = new User( "Rinat", "Klinkov", 31);
    User user5 = new User( "NeRinat", "NeKlinkov", 13);
    Computer computer = new Computer("Apple", 3);
    Computer computer1 = new Computer("Xiaomi", 9);
    System.out.println("\n##########-Generated CREATE TABLE code-##########\n");

    System.out.println("\n##########-Generated INSERT INTO code-##########\n");
    ormManager.save(user);
    ormManager.save(user1);
    ormManager.save(user2);
    ormManager.save(user3);
    ormManager.save(user4);
    ormManager.save(user5);
    ormManager.save(computer);
    ormManager.save(computer1);
    System.out.println("\n##########-Result after INSERT INTO code-##########\n");
    System.out.println(user);
    System.out.println(user1);
    System.out.println(user2);
    System.out.println(user3);
    System.out.println(user4);
    System.out.println(user5);
    System.out.println(computer);
    System.out.println(computer1);
    System.out.println("\n##########-Generated FIND BY ID code-##########\n");
    User us = ormManager.fidById(1L, User.class);
    Computer co = ormManager.fidById(1L, Computer.class);
    System.out.println("\n##########-Result after FIND BY ID code-##########\n");
    System.out.println(us);
    System.out.println(co);
    System.out.println("\n##########-Generated UPDATE code-##########\n");
    us.setAge(null);
    co.setBrandName(null);
    ormManager.update(us);
    ormManager.update(co);
    us = ormManager.fidById(1L, User.class);
    co = ormManager.fidById(1L, Computer.class);
    System.out.println("\n##########-Result after UPDATE code-##########\n");
    System.out.println(us);
    System.out.println(co);
    System.out.println("\n##########-Generated UPDATE code-##########\n");
    us.setAge(33);
    co.setBrandName("Huyaviy");
    ormManager.update(us);
    ormManager.update(co);
    System.out.println("\n##########-Result after UPDATE code-##########\n");
    System.out.println(us);
    System.out.println(co);
  }
}