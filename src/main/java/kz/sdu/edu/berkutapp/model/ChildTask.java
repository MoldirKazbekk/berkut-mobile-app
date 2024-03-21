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
    private String taskDescription;
    @Column
    private Integer coins;
    @Column
    private String status;
    @Column
    private String type;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private AppUser parent;

    public ChildTask(TaskDTO taskDTO){
        this.coins=taskDTO.getCoins();
        this.status= taskDTO.getStatus();
        this.type= taskDTO.getType();
        this.taskDescription= taskDTO.getTaskDescription();
    }

}
