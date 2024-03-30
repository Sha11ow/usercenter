package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.model.domain.Students;
import com.yupi.usercenter.service.StudentsService;
import com.yupi.usercenter.mapper.StudentsMapper;
import org.springframework.stereotype.Service;

/**
* @author huxing
* @description 针对表【students】的数据库操作Service实现
* @createDate 2024-03-22 20:58:56
*/
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students>
    implements StudentsService{

}




