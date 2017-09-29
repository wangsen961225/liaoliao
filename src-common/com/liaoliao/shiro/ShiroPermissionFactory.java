package com.liaoliao.shiro;


import java.util.List;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.liaoliao.admin.entity.Navigation;
import com.liaoliao.admin.entity.Permission;
import com.liaoliao.admin.service.NavigationService;
import com.liaoliao.admin.service.PermissionService;


public class ShiroPermissionFactory extends ShiroFilterFactoryBean {  
    
    /**配置中的过滤链*/  
    public static String filterChainDefinitions;  
      
    @Autowired  
    private NavigationService navigationService; 
    
    @Autowired
    private PermissionService permissionService;
  
    /** 
     * 从数据库动态读取权限 
     */  
    @Override  
    public void setFilterChainDefinitions(String definitions) {
        ShiroPermissionFactory.filterChainDefinitions = definitions;  
        //数据库动态权限  
        List<Navigation> navigations = navigationService.findAll();  
        for(Navigation na : navigations){  
            //字符串拼接权限  
        	if(na.getNavigationUrl() != null && !("".equals(na.getNavigationUrl()))){
        		List<Permission> permissions = permissionService.findByNavigationId(na.getId());
        		String pers="";
        		if(permissions!=null && permissions.size() == 1){
        			for(Permission p:permissions){
            			pers+=p.getAdminGroup().getGroupName()+",";
            		}
        			pers = pers.substring(0,pers.length()-1);
        			definitions = definitions+na.getNavigationUrl() + " = "+"authc,roles["+pers+"]";  
        		}
        		if(permissions!=null && permissions.size()>1){
        			for(Permission p:permissions){
            			pers+=p.getAdminGroup().getGroupName()+",";
            		}
        			pers = pers.substring(0,pers.length()-1);
        			definitions = definitions+na.getNavigationUrl() + " = "+"authc,anyRoles["+pers+"]";  
        		}
        		if(permissions==null || permissions.size()==0){
        			definitions = definitions+na.getNavigationUrl() + " = "+"authc,roles[超级管理员]";
        		}
        	}
        	definitions+="\n";
        }
        //从配置文件加载权限配置  
        Ini ini = new Ini();  
        ini.load(definitions);  
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);  
        if (CollectionUtils.isEmpty(section)) {  
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);  
        }  
        //加入权限集合  
        setFilterChainDefinitionMap(section);  
    }  
} 

