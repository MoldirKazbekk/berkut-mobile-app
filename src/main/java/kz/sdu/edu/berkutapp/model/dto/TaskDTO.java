package kz.sdu.edu.berkutapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.sdu.edu.berkutapp.model.ChildTask;
import lombok.Data;

import java.io.Serializable;

@Data
public class TaskDTO implements Serializable {
    private String taskDescription;
    @Schema(defaultValue = "5")
    private Integer coins;
    private String status;
    private String type;
    public TaskDTO(ChildTask childTask){
        this.coins = childTask.getCoins();
        this.status=childTask.getStatus();
        this.taskDescription= childTask.getTaskDescription();
        this.type= childTask.getType();
    }
    public TaskDTO(){

    }
}

