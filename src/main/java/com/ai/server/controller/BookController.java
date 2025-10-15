package com.ai.server.controller;


import com.ai.server.beans.Book;
import com.ai.server.beans.House;
import com.ai.server.common.Result;
import com.ai.server.service.IBookService;
import com.ai.server.service.IMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("book")
@Tag(name = "图书接口", description = "图书接口AI") // 类级注解
public class BookController {

    @Resource
    IBookService bookService;
    @Resource
    IMemberService memberService;
    /**
     * 查询接口
     */
    @Operation(summary = "获取图书列表", description = "获取图书列表") // 方法级注解
    @GetMapping("list")
    public Result list(){
        List<Book> adminsList = bookService.list(new QueryWrapper<Book>().eq("del","0"));
        return Result.success(adminsList);
    }

    /**
     * 新增接口
     * @return
     */
    @Operation(summary = "新增图书信息", description = "新增图书信息") // 方法级注解
    @PostMapping("add")
    public Result add(@RequestBody Book book){
        book.setDel("0");//删除状态
        boolean b = bookService.save(book);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     *  更新
     */
    @Operation(summary = "更新图书信息", description = "更新图书信息") // 方法级注解
    @PutMapping("update")
    public Result update(@RequestBody Book book){
        boolean b = bookService.updateById(book);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }


    /**
     * 删除
     */
    @Operation(summary = "根据ID删除图书信息", description = "根据ID删除图书信息") // 方法级注解
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id")  Integer id){
        Book book = new Book();
        book.setId(id);
        book.setDel("1");//删除
        boolean b = bookService.updateById(book);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
     * 分页查询
     * @return
     */
    @Operation(summary = "根据标题模糊查询分页", description = "根据标题模糊查询分页") // 方法级注解
    @GetMapping("selectPage")
    public Result selectPage(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("name") String name){
        //使用分页插件
        PageHelper.startPage(pageNum,pageSize);
        //查询数据库，需要用到like查询
        List<Book> list = bookService.list(new QueryWrapper<Book>().like("name", name).eq("del", "0").orderByDesc("id"));
        //封装一下查询的结果集
        PageInfo<Book> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }


    /**
     * 分页查询可用房源
     * @return
     */
    @Operation(summary = "根据书名模糊查询分页", description = "根据书名模糊查询分页") // 方法级注解
    @GetMapping("selectPageAvailable")
    public Result selectPageAvailable(@RequestParam("pageNum") Integer pageNum,
                             @RequestParam("pageSize") Integer pageSize,
                             @RequestParam("name") String name){
        //使用分页插件
        PageHelper.startPage(pageNum,pageSize);
        //查询数据库，需要用到like查询
        List<Book> list = bookService.list(new QueryWrapper<Book>().like("name", name).ne("stock","0").eq("del", "0").orderByDesc("id"));
        //封装一下查询的结果集
        PageInfo<Book> pageInfo = PageInfo.of(list);
        return Result.success(pageInfo);
    }


    /**
     *  更新房源状态
     */
    @Operation(summary = "更新图书状态", description = "更新图书状态") // 方法级注解
    @PutMapping("updateStatus")
    public Result updateStatus(@RequestBody Book book){
        if("0".equals(book.getStatus())){
            book.setMember_id("");
            bookService.tuiZu(book.getId());
            return Result.success();
        }else{
            boolean b = bookService.updateById(book);
            if(b){
                return Result.success();
            }else{
                return Result.error();
            }
        }
    }

    @RequestMapping("updateStatus1")
    public Result delstock(@RequestBody Book book) {
        int count = 0;
        Integer id = book.getId();
        List<Integer> selectbook = bookService.selectbook(book.getMember_id());
        for (int bookid : selectbook
        ) {
            if (bookid == id) {
                count += 1;
            }
        }
        if (count == 0) {
            bookService.updatestock(id);
            memberService.insertBookUser(id, book.getMember_id());
            return Result.success("借阅成功");
        } else return Result.error("您已经借阅了此书了");
    }
    /**
     *  更新房源状态
     */
    @Operation(summary = "更新图书状态-退租", description = "更新图书状态-退租") // 方法级注解
    @PutMapping("updateStatusTui")
    public Result updateStatusTui(@RequestBody Book book){
        if("0".equals(book.getStatus())){
            book.setMember_id("");
        }
        System.out.println("书的id为"+book.getId());
        int b = bookService.tuiZu(book.getId());
        if(b>0){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**s
     * @return
     */
    @Operation(summary = "根据书名模糊查询分页", description = "根据书名模糊查询分页") // 方法级注解
    @GetMapping("selectPageMyBook")
    public Result selectPageMyBook(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam("name") String name,
                                      @RequestParam("member_id") String member_id) {
        //使用分页插件
        PageHelper.startPage(pageNum, pageSize);
        List<Integer> selectbook = bookService.selectbook(member_id);
        List<Book> books = new ArrayList<>();
        for (Integer bookid : selectbook
        ) {
            books.add(bookService.selectByIdBook(bookid));
        }
        //查询数据库，需要用到like查询
        List<Book> list = bookService.list(new QueryWrapper<Book>().like("name", name).eq("del", "0").orderByDesc("id"));
        //封装一下查询的结果集
        PageInfo<Book> pageInfo = PageInfo.of(books);
        return Result.success(pageInfo);
    }

}
