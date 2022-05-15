package top.yuany3721.ir;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@MapperScan("top.yuany3721.ir.dao")
@EntityScan("top.yuany3721.ir.entity")
public class IrApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrApplication.class, args);
	}

}
