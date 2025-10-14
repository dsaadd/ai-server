package com.ai.server.service;

import com.ai.server.beans.Book;
import com.ai.server.beans.House;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 * @author bruce
 */
public interface IBookService extends IService<Book> {

    int tuiZu(int id);

    Integer updatestock(Integer id);
    List<Integer> selectbook(String  id);
    Book selectByIdBook(int id);
}
