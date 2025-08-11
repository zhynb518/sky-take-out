package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CategoryServicceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void add(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        //默认为禁用
        category.setStatus(StatusConstant.DISABLE);
        category.setCreateTime(LocalDateTime.now());//创建时间
        category.setCreateUser(BaseContext.getCurrentId());//创建人
        category.setUpdateTime(LocalDateTime.now());//修改时间
        category.setUpdateUser(BaseContext.getCurrentId());//修改人
        categoryMapper.insert(category);



    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        //select * from category limit 0,10
        //开始分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> records = page.getResult();

        return new PageResult(total,records);
    }
}
