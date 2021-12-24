package com.funicorn.basic.cloud.security.service.impl;

import com.funicorn.basic.cloud.security.entity.Menu;
import com.funicorn.basic.cloud.security.mapper.MenuMapper;
import com.funicorn.basic.cloud.security.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
