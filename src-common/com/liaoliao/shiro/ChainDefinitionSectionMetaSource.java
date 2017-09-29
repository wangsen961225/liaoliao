package com.liaoliao.shiro;

import java.util.List;
import java.util.Map;
import org.apache.shiro.config.Ini;
 import org.apache.shiro.config.Ini.Section;
 import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
 import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
 import org.apache.shiro.web.servlet.AbstractShiroFilter;
 import org.springframework.beans.factory.FactoryBean;
 import org.springframework.beans.factory.annotation.Autowired;
import com.liaoliao.admin.entity.Navigation;
import com.liaoliao.admin.entity.Permission;
import com.liaoliao.admin.service.NavigationService;
import com.liaoliao.admin.service.PermissionService;
 
 
 public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
 
     private  ShiroFilterFactoryBean shiroFilterFactoryBean;
 
     @Autowired  
     private NavigationService navigationService; 
     
     @Autowired
     private PermissionService permissionService;
     
     
     private String filterChainDefinitions="";
     
 
     public static final String PREMISSION_STRING = "roles[\"{0}\"]";
 
     @Override
     public Section getObject() throws Exception {
    	 String definitions="";
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
         return section;
     }
 
     @Override
     public Class<?> getObjectType() {
         return this.getClass();
     }
 
     @Override
     public boolean isSingleton() {
         return false;
     }
 
     public void setFilterChainDefinitions(String filterChainDefinitions) {
         this.filterChainDefinitions = filterChainDefinitions;
     }
     
     public void updatePermission() {  
           
         synchronized (shiroFilterFactoryBean) {  
        	 
             AbstractShiroFilter shiroFilter = null;  
   
             try {  
                 shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();  
             } catch (Exception e) {
            	 System.out.println("updatePermission出错");
            }  
   
             // 获取过滤管理器  
             PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter  
                     .getFilterChainResolver();  
             DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();  
   
             // 清空初始权限配置  
             manager.getFilterChains().clear();  
             shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();  
   
            // 重新构建生成  
             shiroFilterFactoryBean.setFilterChainDefinitions(filterChainDefinitions);  
             Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();  
   
             for (Map.Entry<String, String> entry : chains.entrySet()) {  
                 String url = entry.getKey();  
                 String chainDefinition = entry.getValue().trim().replace(" ", "");  
                 manager.createChain(url, chainDefinition);  
             }  
  
         }  
     }

	public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
		return shiroFilterFactoryBean;
	}

	public void setShiroFilterFactoryBean(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		this.shiroFilterFactoryBean = shiroFilterFactoryBean;
	}
 
 }