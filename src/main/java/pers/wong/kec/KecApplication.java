package pers.wong.kec;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("pers.wong.kec.dao.dao")
public class KecApplication {

  public static void main(String[] args) {
    SpringApplication.run(KecApplication.class, args);
  }

}

