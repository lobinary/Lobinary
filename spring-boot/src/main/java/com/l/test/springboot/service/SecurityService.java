package com.l.test.springboot.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SecurityService implements FilterInvocationSecurityMetadataSource {

    private Logger logger = LoggerFactory.getLogger(SecurityService.class);

    private HashMap<String, Collection<ConfigAttribute>> map =null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine(){
        map = new HashMap<>();

        List<ConfigAttribute> array = new ArrayList<>();
        ConfigAttribute cfg = new SecurityConfig("ROLE_ANONYMOUS");
        array.add(cfg);
        map.put("/user/login",array);
        List<ConfigAttribute> array2 = new ArrayList<>();
        ConfigAttribute cfg2 = new SecurityConfig("ROLE_USER");
        array2.add(cfg2);
        map.put("/card/*",array2);
        logger.info("SecurityService——>map："+map);
//        Collection<ConfigAttribute> array;
//        ConfigAttribute cfg;
//        SecurityConfig 钢蛋儿 = new SecurityConfig("钢蛋儿");
//        List<Permission> permissions = permissionDao.findAll();
//        for(Permission permission : permissions) {
//            array = new ArrayList<>();
//            cfg = new SecurityConfig(permission.getName());
//            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
//            array.add(cfg);
//            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
//            map.put(permission.getUrl(), array);
//        }

    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(map ==null) loadResourceDefine();
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;

        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                Collection<ConfigAttribute> configAttributes = map.get(resUrl);
//                logger.info("configAttributes:"+configAttributes);
                return configAttributes;
            }
        }
//        logger.info("object:"+object);
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}