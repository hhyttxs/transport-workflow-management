package com.domed.api.service.hy.impl;


import com.domed.api.service.hy.IYhcdService;
import com.domed.common.result.Result;
import com.domed.core.dao.IGenericDAO;
import com.domed.core.query.support.IPageList;
import com.domed.core.service.ICommonService;
import com.domed.domain.hy.TabYhcd;
import com.domed.domain.hy.TabCdgl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.UUID;



@Service
@Transactional
public class YhcdServiceImpl implements IYhcdService {

    @Autowired
    private ICommonService commonService;

    @Resource(name = "yhcdDAO")
    private IGenericDAO<TabYhcd> yhcdDAO;
    @Resource(name = "cdglDAO")
    private IGenericDAO<TabCdgl> cdglDAO;

    @Override
    public Result queryYhList(Map infoMap) {
        String currentPage = this.commonService.notNull(infoMap, "currentPage", "当前页");
        String numberPage = this.commonService.notNull(infoMap, "numberPage", "每页条数");
        Map<String, Object> resultMap = new HashMap<>();
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT ");
        sb.append("GID AS \"gid\", ");
        sb.append("NAME AS \"name\", ");
        sb.append("\"DESC\" AS \"desc\", ");
        sb.append("STATUS AS \"status\", ");
        sb.append("NEW_USER AS \"newUser\", ");
        sb.append("TO_CHAR(NEW_USER_TIME, 'YYYY-MM-DD HH24:MI:SS') AS \"newUserTime\" ");
        sb.append("FROM TAB_YHCD ");


        IPageList iPageList = commonService.pageQuery(sb.toString(), currentPage, numberPage);
        resultMap.put("data", iPageList);
        resultMap.put("pageCount", iPageList.getRowCount());
        resultMap.put("totalPage", iPageList.getRowCount());

        return Result.success(resultMap);
    }
    @Override
    public Result saveCdgl(Map infoMap) {
        String yhcdGid = commonService.getParam(infoMap, "yhcdGid");
        Object menuCodeObject = infoMap.get("menuCodeList");
        Set<String> menuCodeSet = new LinkedHashSet<>();
        List<?> menuCodeList = (List<?>) menuCodeObject;
        cdglDAO.executeNativeSQL(" DELETE FROM TAB_CDGL WHERE YHCD_GID = '" + yhcdGid + "'");
        for (Object item : menuCodeList) {
            if (item != null) {
                String menuCode = item.toString().trim();

                if (!menuCode.isEmpty()) {
                    menuCodeSet.add(menuCode);
                }
            }
        }

        for (String menuCode : menuCodeSet) {
            TabCdgl tabCdgl = new TabCdgl();

            tabCdgl.setGid(UUID.randomUUID().toString());

            tabCdgl.setYhcdGid(yhcdGid);
            tabCdgl.setMenuCode(menuCode);

            cdglDAO.save(tabCdgl);
        }


        return Result.success("保存成功");
    }
}
