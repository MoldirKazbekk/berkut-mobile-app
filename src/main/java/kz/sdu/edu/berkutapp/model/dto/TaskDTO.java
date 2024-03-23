package kz.sdu.edu.berkutapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.sdu.edu.berkutapp.model.ChildTask;
import lombok.Data;

import java.io.Serializable;

@Data
public class TaskDTO implements Serializable {
    private String name;
    private String description;
    @Schema(defaultValue = "5")
    private Integer coins;
    private String status;

    public TaskDTO(ChildTask childTask){
        this.coins = childTask.getCoins();
        this.status=childTask.getStatus();
        this.description= childTask.getDescription();
        this.name = childTask.getName();
    }
    public TaskDTO(){

    }
}

