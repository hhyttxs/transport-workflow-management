package com.domed.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table ( name ="SYS_OAUTH_CLIENT" )
public class OAuth2Client {

    @Id
    @Column(name = "CLIENT_ID", unique = true, nullable = false)
    //@ApiModelProperty(value = "客户端ID")
    private String clientId;

    //@ApiModelProperty(value = "客户端密钥")
    @Column(name = "CLIENT_SECRET")
    private String clientSecret;

    //@ApiModelProperty(value = "资源id列表")
    @Column(name = "RESOURCE_IDS")
    private String resourceIds;

    //@ApiModelProperty(value = "域")
    @Column(name = "SCOPE")
    private String scope;

    //@ApiModelProperty(value = "授权方式")
    @Column(name = "AUTHORIZED_GRANT_TYPES")
    private String authorizedGrantTypes;

    //@ApiModelProperty("回调地址")
    @Column(name = "WEB_SERVER_REDIRECT_RUI")
    private String webServerRedirectUri;

   // @ApiModelProperty(value = "权限列表")
   @Column(name = "AUTHORITIES")
    private String authorities;

    //@ApiModelProperty(value = "认证令牌时效")
    @Column(name = "ACCESS_TOKEN_VALIDITY")
    private Integer accessTokenValidity;

    //@ApiModelProperty(value = "刷新令牌时效")
    @Column(name = "REFRESH_TOKEN_VALIDITY")
    private Integer refreshTokenValidity;

    //@ApiModelProperty(value = "扩展信息")
    @Column(name = "ADDITIONAL_INFORMATION")
    private String additionalInformation;

    //@ApiModelProperty(value = "是否自动放行")
    @Column(name = "AUTOAPPROVE")
    private String autoapprove;

}
