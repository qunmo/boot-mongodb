package sjz.sgy.lb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("sjz.sgy.lb.dao")
public class LbApplication {

	public static void main(String[] args) {
		SpringApplication.run(LbApplication.class, args);
	}
	
}

