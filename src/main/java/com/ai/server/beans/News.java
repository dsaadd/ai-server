package com.ai.server.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 资讯表
 * </p>
 *
 * @author bruce
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String description;

    private String content;

    private String cover;

    private Integer count;

    private String createTime;

    private String del;

}
