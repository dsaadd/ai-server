package com.ai.server.service.impl;

import com.ai.server.beans.Book;
import com.ai.server.mapper.BookMapper;
import com.ai.server.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 * @author bruce
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    BookMapper bookMapper;

    @Override
    public int tuiZu(int id) {
        // 先更新book表
        bookMapper.updateBookAfterReturn(id);
        // 再删除借阅记录
        bookMapper.deleteBorrowRecord(id);
        // 无论删除是否成功，只要更新了图书状态就算成功
        return 1;
    }

    @Override
    public Integer updatestock(Integer id) {
        return bookMapper.updatestock(id);
    }
@Override
public List<Integer> selectbook(String  id){
        return  bookMapper.selectbook(id);
}

    @Override
    public Book selectByIdBook(int id) {
        return bookMapper.selectByIdBook(id);
    }

}
