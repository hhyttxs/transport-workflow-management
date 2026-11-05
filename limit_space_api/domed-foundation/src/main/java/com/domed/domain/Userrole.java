package com.domed.domain;

import com.domed.core.domain.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_userrole")
public class Userrole extends IdEntity {

	    //用户角色描述
    @Column(name = "userRoleDescription")
    private String userRoleDescription;

    //用户角色名称
    @Column(name = "userRoleName")
    private String userRoleName;



}
