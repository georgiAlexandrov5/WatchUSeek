package softuni.WatchUSeek.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import softuni.WatchUSeek.web.interceptors.FaviconInterceptor;
import softuni.WatchUSeek.web.interceptors.TitleInterceptor;

@Controller
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {
    private final TitleInterceptor interceptor;
    private final FaviconInterceptor faviconInterceptor;

    @Autowired
    public ApplicationWebMvcConfiguration(TitleInterceptor interceptor, FaviconInterceptor faviconInterceptor) {
        this.interceptor = interceptor;
        this.faviconInterceptor = faviconInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.interceptor);
        registry.addInterceptor(this.faviconInterceptor);
    }
}
