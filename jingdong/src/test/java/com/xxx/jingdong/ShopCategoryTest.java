package com.xxx.jingdong;

import com.xxx.jingdong.pojo.ShopCategory;
import com.xxx.jingdong.repository.ShopCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ShopCategoryTest {
    @Autowired
    private ShopCategoryRepository repository;
    //查询一条记录
    @Test
    public void findOneTest(){
        ShopCategory result =repository.findById(1).orElse(null);
        log.info("result={}",result);
    }
    //添加记录
    @Test
    public void addTest(){
        ShopCategory shopCategory=new ShopCategory();
        shopCategory.setName("超市测试");
        shopCategory.setImgUrl("http://webapi.hanmaweilai.com/public/static/images/超市.png");
        repository.save(shopCategory);
    }
    //修改记录
    @Test
    public void saveTest(){
        ShopCategory shopCategory=new ShopCategory();
        shopCategory.setId(1);
        shopCategory.setName("超市测试2");
        shopCategory.setImgUrl("http://webapi.hanmaweilai.com/public/static/images/超市.png");
        repository.save(shopCategory);
    }
    //查询记录
    @Test
    public void selectTest(){
        List<ShopCategory> result =repository.findAll();
        log.info("result={}",result);
    }
    //查询记录
    @Test
    public void selectByStatusTest(){
        List<ShopCategory> result =repository.findAllByStatus(1);
        log.info("result={}",result);
    }
}
