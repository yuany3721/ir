package com.ir.site.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AuthFilter
 * @Description token验证拦截器
 * @Author yanyi
 * @Date 2019/5/7 14:03
 * @Version 1.0
 **/
@Component
@WebFilter(urlPatterns = "/*", filterName = "tokenAuthorFilter")
//urlPatterns 是url拦截规则
public class AuthFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("wesker---------------------init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;




       /* boolean isFilter = false;
        //获取传过来的token
        String token = req.getHeader("token");
        //获取原token
        Map<String,String> map=ProperUtils.getHashProper();
        Object obj=getMd5WithSalt(map.get("hash"),map.get("pwd"),map.get("salt"),Integer.parseInt(map.get("num")));
        System.out.println("传过来的token=="+obj+"==现存token=="+token);
        //token验证
        if(obj.toString().equals(token)){
            System.out.println("token验证成功");
            isFilter=true;
        }else{
            System.out.println("token验证失败");
            isFilter=false;
        }
        //放行
        if (isFilter) {
            System.out.println("token filter过滤ok!");
            try{
                filterChain.doFilter(servletRequest, servletResponse);
            }catch(Exception e){
                e.printStackTrace();
            }

        }*/

        try{
            filterChain.doFilter(servletRequest, servletResponse);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
        System.out.println("wesker------------------->destroy");
    }
}