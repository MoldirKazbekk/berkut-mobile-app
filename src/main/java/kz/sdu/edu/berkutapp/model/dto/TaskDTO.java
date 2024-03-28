package kz.sdu.edu.berkutapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kz.sdu.edu.berkutapp.model.ChildTask;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TaskDTO implements Serializable {
    private String name;

    private String description;

    @Schema(defaultValue = "5")
    private Integer coins;

    //todo Enum type
    private String status;

    public TaskDTO(ChildTask childTask) {
        this.coins = childTask.getCoins();
        this.status = childTask.getStatus();
        this.description = childTask.getDescription();
        this.name = childTask.getName();
    }
}

