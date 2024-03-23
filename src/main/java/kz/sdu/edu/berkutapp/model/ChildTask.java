package kz.sdu.edu.berkutapp.model;


import jakarta.persistence.*;
import kz.sdu.edu.berkutapp.model.dto.TaskDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "child_task")
@NoArgsConstructor
public class ChildTask{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;
    @Column
    private Integer coins;
    @Column
    private String status;
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private AppUser child;

    public ChildTask(TaskDTO taskDTO){
        this.coins=taskDTO.getCoins();
        this.status= taskDTO.getStatus();
        this.description= taskDTO.getDescription();
        this.name = taskDTO.getName();
    }

}
