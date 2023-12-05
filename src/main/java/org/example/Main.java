package org.example;

import java.util.List;
import java.util.Scanner;
import org.example.entity.Human;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class Main {
    public static void main(String[] args) {
        try (Session sessionFactory = HibernateUtil.getSessionFactory().openSession();
             Scanner scanner = new Scanner(System.in);
        ) {

            while (true) {
                System.out.println("""
                        Enter operation:
                        1: persist
                        2: save
                        3: get
                        4: soft deleted
                        5: load
                        6: merge
                        7: update
                        8: saveOUpdate
                        9: remove
                        10: from humans
                        """);
                int a = scanner.nextInt();
                switch (a) {
                    case 1 -> {
                        sessionFactory.beginTransaction();
                        System.out.println("name");
                        String name = scanner.next();
                        System.out.println("age");
                        int age = scanner.nextInt();
                        Human human=new Human();  // transient
                        human.setName(name);
                        human.setAge(age);
                        sessionFactory.persist(human); // persist
                        sessionFactory.getTransaction().commit(); // detached
                    }
                    case 2 -> {
                        sessionFactory.beginTransaction();
                        System.out.println("name");
                        String name = scanner.next();
                        System.out.println("age");
                        int age = scanner.nextInt();
                        Human human = new Human();
                        human.setName(name);
                        human.setAge(age);
                        Object save = sessionFactory.save(human);
                        System.out.println("Saved: " + save);
                        sessionFactory.getTransaction().commit();
                    }
                    case 3->{
                        sessionFactory.beginTransaction();
                        System.out.println("id");
                        int id=scanner.nextInt();
                        Human human=sessionFactory.get(Human.class, id);
                        System.out.println("get object:" + human);
                    }
                    case 4->{
                        sessionFactory.beginTransaction();
                        System.out.println("id");
                        int id=scanner.nextInt();
                        Human human=sessionFactory.get(Human.class, id);
                        human.softDeleted();
                        System.out.println("get object:" + human);
                        sessionFactory.getTransaction().commit();

                    }
                    case 5->{
                        sessionFactory.beginTransaction();
                        System.out.println("id");
                        int id=scanner.nextInt();
                        Human human=sessionFactory.load(Human.class, id);
                        System.out.println("get object:" + human);
                    }
                    case 6 -> {
                        sessionFactory.beginTransaction();
                        System.out.println("id");
                        int id = scanner.nextInt();
                        Human human=sessionFactory.get(Human.class,id);
                        System.out.println("get object: "+human);
                        System.out.println("enter new name: ");
                        String name=scanner.next();
                        human.setName(name);
                        System.out.println("enter  age: ");
                        int age=scanner.nextInt();
                        human.setAge(age);
                        Human merge=sessionFactory.merge(human);  // UPDATE
                        System.out.println("Changed: "+merge);
                        sessionFactory.getTransaction().commit();

//                        Human humanWithoutId=new Human();
//                        humanWithoutId.setAge(23);
//                        humanWithoutId.setName("K");
//                        sessionFactory.merge(humanWithoutId); // INSERT
//                        sessionFactory.getTransaction().commit();
                    }
                    case 7 -> {
                        sessionFactory.beginTransaction();
                        System.out.println("id");
                        int id = scanner.nextInt();
                        Human human = sessionFactory.get(Human.class, id);
                        System.out.println("get object: " + human);
                        System.out.println("enter new name: ");
                        String name = scanner.next();
                        human.setName(name);
                        System.out.println("Enter age: ");
                        int age = scanner.nextInt();
                        human.setAge(age);
                        sessionFactory.update(human);
                        sessionFactory.getTransaction().commit();

//                        Human humanWithoutId=new Human();
//                        humanWithoutId.setAge(23);
//                        humanWithoutId.setName("K");
//                        sessionFactory.update(humanWithoutId);
//                        sessionFactory.getTransaction().commit();
                    }
                    case 9->{
                        sessionFactory.beginTransaction();
                        System.out.println("id");
                        int id=scanner.nextInt();
                        Human human=sessionFactory.get(Human.class, id);
                        System.out.println("get object:" + human);
                        sessionFactory.remove(human);
                        sessionFactory.getTransaction().commit();
                    }
                    case 10 -> {
                        Query<Human> humanQuery =
                                sessionFactory.createQuery("from Human", Human.class);
                        List<Human> list = humanQuery.list();
                        for (Human human : list) {
                            System.out.println(human);
                        }
                    }

                }
            }
        }
    }
}
