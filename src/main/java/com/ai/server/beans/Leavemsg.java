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
 * 
 * </p>
 *
 * @author bruce
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leavemsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer memberId;

    //留言人的账号,表示这个字段是一个扩展字段
    @TableField(exist = false)
    private String memberName;

    private String content;

    private String createTime;

    private String reply;

    private String replyTime;

    private Integer replyId;

    //回复人的账号,表示这个字段是一个扩展字段
    @TableField(exist = false)
    private String replyName;

    private String del;

}
