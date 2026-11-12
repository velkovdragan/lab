package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data

public class Workshop {
    private String title;
    private String category;
    private double price;
    private int capacity;
    private Long id;
    private Instructor instructor;

    public Workshop(String title,String category,double price,int capacity,Instructor instructor){
        this.title=title;
        this.capacity=capacity;
        this.category=category;
        this.price=price;
        this.instructor=instructor;
        this.id=(long) (Math.random()*1000);
    }

}

