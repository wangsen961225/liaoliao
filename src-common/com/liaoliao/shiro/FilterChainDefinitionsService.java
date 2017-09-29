package com.liaoliao.shiro;

import java.util.Map;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("filterChainDefinitionsService") 
public class FilterChainDefinitionsService {
	

	private   ShiroPermissionFactory shiroPermissionFactory ;
    
    public void reloadFilterChains() {
        synchronized (shiroPermissionFactory) {   //强制同步，控制线程安全
            AbstractShiroFilter shiroFilter = null;

            try {
                shiroFilter = (AbstractShiroFilter) shiroPermissionFactory.getObject();

                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                        .getFilterChainResolver();
                // 过滤管理器
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
                // 清除权限配置
                manager.getFilterChains().clear();
                shiroPermissionFactory.getFilterChainDefinitionMap().clear();
                // 重新设置权限
                shiroPermissionFactory.setFilterChainDefinitions(ShiroPermissionFactory.filterChainDefinitions);//传入配置中的filterchains

                Map<String, String> chains = shiroPermissionFactory.getFilterChainDefinitionMap();
                //重新生成过滤链
                if (!CollectionUtils.isEmpty(chains)) {
                    chains.forEach((url, definitionChains) -> {
                        manager.createChain(url, definitionChains.trim().replace(" ", ""));
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	public ShiroPermissionFactory getShiroPermissionFactory() {
		return shiroPermissionFactory;
	}

	public void setShiroPermissionFactory(ShiroPermissionFactory shiroPermissionFactory) {
		this.shiroPermissionFactory = shiroPermissionFactory;
	}

}
