package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Instructor;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DataHolder {
    public static List<Workshop> workshops=null;
    public static List<Registration>registrations=null;
    public static List<Instructor>instructors=null;

    @PostConstruct
    public void init(){
        instructors=new ArrayList<>();
        workshops=new ArrayList<>();
        registrations=new ArrayList<>();
        Instructor i1 = new Instructor(1L, "Ana", "Petrovska", "ana@finki.mk", "Programming");
        Instructor i2 = new Instructor(2L, "Marko", "Jovanov", "marko@finki.mk", "Artificial Intelligence");
        Instructor i3 = new Instructor(3L, "Elena", "Stojanova", "elena@finki.mk", "Design");
        instructors.add(i1);
        instructors.add(i2);
        instructors.add(i3);
        workshops.add(new Workshop("Java Basics", "Programming", 1200.0, 20,i1));
        workshops.add(new Workshop("Web Development", "Programming", 1500.0, 15,i2));
        workshops.add(new Workshop("Python for Beginners", "Programming", 1000.0, 25,i3));
        workshops.add(new Workshop("Introduction to AI", "Artificial Intelligence", 2000.0, 12,i1));
        workshops.add(new Workshop("Machine Learning", "Artificial Intelligence", 2500.0, 0,i2));
        workshops.add(new Workshop("Graphic Design", "Design", 1300.0, 18,i3));
        workshops.add(new Workshop("UI/UX Fundamentals", "Design", 1700.0, 10,i1));
        workshops.add(new Workshop("Cybersecurity Basics", "Security", 1800.0, 20,i2));
        workshops.add(new Workshop("Database Management", "Databases", 1400.0, 15,i3));
        workshops.add(new Workshop("Mobile App Development", "Programming", 2200.0, 8,i1));
    }
}
