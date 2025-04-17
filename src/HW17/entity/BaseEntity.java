package HW17.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BaseEntity {
    public static Integer idx = 1;
    private Integer id;
    
    BaseEntity(){
        id = idx++;
    }
}
