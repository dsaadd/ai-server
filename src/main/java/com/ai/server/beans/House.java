package com.ai.server.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * </p>
 * @author bruce
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private Double area;

    private Integer rent;

    private String address;

    private String photos;

    private String del;

    @TableField(value = "member_id")
    private String member_id;  //外键

    @TableField(value = "status")
    private String status;

}

