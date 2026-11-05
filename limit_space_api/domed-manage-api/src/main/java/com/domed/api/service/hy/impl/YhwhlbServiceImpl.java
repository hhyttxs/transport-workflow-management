package com.domed.api.service.hy.impl;


import com.domed.api.service.hy.IYhwhlbService;
import com.domed.common.result.Result;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.query.support.IPageList;
import com.domed.core.service.ICommonService;
import com.domed.domain.hy.Dlhwjyxk;
import com.domed.domain.hy.TabYhwh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional
public class YhwhlbServiceImpl implements IYhwhlbService {

    @Autowired
    private ICommonService commonService;

    @Resource(name = "yhwhlbDAO")
    private IGenericDAO<TabYhwh> yhwhlbDAO;

    @Override
    public Result queryUserList(Map infoMap) {
        String currentPage = this.commonService.notNull(infoMap, "currentPage", "当前页");
        String numberPage = this.commonService.notNull(infoMap, "numberPage", "每页条数");
        Map<String, Object> resultMap = new HashMap<>();
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT ");
        sb.append("GID AS \"gid\", ");
        sb.append("CODE AS \"code\", ");
        sb.append("NAME AS \"name\", ");
        sb.append("PHONE AS \"phone\", ");
        sb.append("TYPE AS \"type\", ");
        sb.append("STATUS AS \"status\", ");
        sb.append("NEW_USER AS \"newUser\", ");
        sb.append("YHCD_GID AS \"yhcdGid\", ");
        sb.append("TO_CHAR(NEW_USER_TIME, 'YYYY-MM-DD HH24:MI:SS') AS \"newUserTime\" ");
        sb.append("FROM TAB_YHWH ");
        sb.append("ORDER BY NEW_USER_TIME DESC");

        IPageList iPageList = commonService.pageQuery(sb.toString(), currentPage, numberPage);
        resultMap.put("data", iPageList);
        resultMap.put("pageCount", iPageList.getRowCount());
        resultMap.put("totalPage", iPageList.getRowCount());

        return Result.success(resultMap);
    }

    @Override
    public Result saveOrUpdateYhwhlb(Map infoMap) {
        String code = commonService.getParam(infoMap, "code");
        String password = commonService.getParam(infoMap, "password");
        String name = commonService.getParam(infoMap, "name");
        String phone = commonService.getParam(infoMap, "phone");
        String type = commonService.getParam(infoMap, "type");
        String newUser = commonService.getParam(infoMap, "newUser");

        TabYhwh tabYhwh = new TabYhwh();

        tabYhwh.setGid(UUID.randomUUID().toString());
        tabYhwh.setCode(code);
        tabYhwh.setPassword(password);
        tabYhwh.setName(name);
        tabYhwh.setPhone(phone);
        tabYhwh.setType(type);

        yhwhlbDAO.save(tabYhwh);
        return null;
    }

    @Override
    public Result bindUserRole(Map infoMap) {

        String userGid = commonService.getParam(infoMap, "userGid");
        String yhcdGid = commonService.getParam(infoMap, "yhcdGid");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gid", userGid);

        List<TabYhwh> list = yhwhlbDAO.find(
                "obj.gid = :gid",
                paramMap,
                0,
                1
        );

        TabYhwh tabYhwh = list.get(0);

        tabYhwh.setYhcdGid(yhcdGid);

        yhwhlbDAO.save(tabYhwh);

        return Result.success("绑定成功");
    }
}
