package sjz.sgy.lb.config.druid;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Description: TODO(这里用一句话描述这个方法的作用) 
 * @author 于浩淼
 * @date 2020年2月6日 上午10:23:04
 */
@Configuration
public class DruidConfig {
	@Bean  
    @ConfigurationProperties(prefix = "spring.datasource")  
    public DataSource druidDataSource() {  
        DruidDataSource druidDataSource = new DruidDataSource();  
        return druidDataSource;  
    } 

	
}
