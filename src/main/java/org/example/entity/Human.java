package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

@Entity
@Data
@Table(name = "humans", schema = "my-schema")
@Where(clause = "DELETED = 0") //во всех WHERE будет добавляться “AND DELETED = 0”
@NoArgsConstructor
public class Human {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "user_sequence"),
                    @Parameter(name = "initial_value", value = "4"),
                    @Parameter(name = "increment_size", value = "7")
            }
    )
    private int id;

    @Column(name = "DELETED")
    // если значение в колонке DELETED == 0, то запись жива, если 1 - мертва
    private Integer deleted = 0;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public void softDeleted() {
        this.deleted = 1; //помечаем запись как мертвую
    }
}
