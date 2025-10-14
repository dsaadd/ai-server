package com.ai.server.mapper;

import com.ai.server.beans.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @author bruce
 */
public interface BookMapper extends BaseMapper<Book> {

    int tuiZu(int id);
    int updatestock(int id);
    int insertBookUser(@Param("id")String id, @Param("bookid")Integer bookid);
    List<Integer> selectbook(String  id);
    Book selectByIdBook(@Param("id")int id);
}
