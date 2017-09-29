package com.liaoliao.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import com.liaoliao.admin.entity.AdminUser;
import com.liaoliao.admin.entity.Permission;
import com.liaoliao.admin.service.AdminUserService;
import com.liaoliao.admin.service.PermissionService;


public class MyRealm extends AuthorizingRealm {
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private PermissionService permissionService;
	
	
    /**
     * 为当前登录的Subject授予角色和权限
     * -----------------------------------------------------------------------------------------------
     * 经测试：本例中该方法的调用时机为需授权资源被访问时
     * 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持，则可灵活决定是否启用AuthorizationCache
     * 比如说这里从数据库获取权限信息时，先去访问Spring3.1提供的缓存，而不使用Shior提供的AuthorizationCache
     * -----------------------------------------------------------------------------------------------
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
        //获取当前登录的用户名
        String currentUsername = (String)super.getAvailablePrincipal(principals);
        ////从数据库中获取当前登录用户的详细信息
        AdminUser adminUser = adminUserService.findByUserName(currentUsername);
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        Set<String>  perlist = new HashSet<String>();
       if(adminUser != null){
        simpleAuthorInfo.addRole(adminUser.getAdminGroup().getGroupName());
         	List<Permission> pers = permissionService.findByGroupId(adminUser.getAdminGroup().getId());
        	if(pers!=null && pers.size()>0){
        		for(Permission p:pers){
        			perlist.add(p.getNavigation().getNavigationUrl());
            	}
        		simpleAuthorInfo.addStringPermissions(perlist);
        	} 
        	return simpleAuthorInfo;
         }else{
        	 return null;
         }
    }
    /**
     * 验证当前登录的Subject
     * 经测试：本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()的时候
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken){
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        if(token.getUsername()==null){
        	return null;
        }
        AdminUser adminUser = adminUserService.findByUserName(token.getUsername());
        if(null != adminUser){
            String username = adminUser.getUserName();
            String password = adminUser.getPassWord();
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(username, password, this.getName());
            this.setAuthenticationSession(adminUser);
            return authcInfo;
        }else{
//        	throw new UnknownAccountException("用户帐号不存在！");
        	return null;
        }
    }

	/**
     * 将一些数据放到ShiroSession中，以便于其它地方使用
     * 比如Controller里面，使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setAuthenticationSession(Object value){
     /*   Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            session.setTimeout(1000 * 60 * 60 * 2);
            session.setAttribute("currentUser", value);
        }*/
    }
}