package com.ai.server.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("members")
public class Members implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String uname;

    private String upass;

    private String nickname;

    private String phone;

    private String photos;

    private String createtime;

    private String del;

    private String role;

}