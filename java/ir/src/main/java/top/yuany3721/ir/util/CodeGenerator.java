package top.yuany3721.ir.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author yuany3721
 **/

public class CodeGenerator {

        /**
         * @param args
         */
        public static void main(String[] args) {
                AutoGenerator autoGenerator = new AutoGenerator();

                // 全局配置
                GlobalConfig globalConfig = new GlobalConfig();
                globalConfig.setOutputDir("G:\\website_IR\\java\\ir\\src\\main\\java")
                                .setFileOverride(true)
                                .setActiveRecord(false)// 不需要ActiveRecord特性的请改为false
                                .setEnableCache(false)// XML 二级缓存
                                .setBaseResultMap(true)// XML ResultMap
                                .setBaseColumnList(false)// XML columList
                                .setSwagger2(false)
                                .setAuthor("lcl");// 作者
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                globalConfig.setControllerName("%sController")
                                .setServiceName("%sService")
                                .setServiceImplName("%sServiceImpl")
                                .setMapperName("%sMapper")
                                .setEntityName("%s")
                                .setXmlName("%sMapper");
                autoGenerator.setGlobalConfig(globalConfig);

                // 数据源配置
                DataSourceConfig dataSourceConfig = new DataSourceConfig();
                dataSourceConfig.setDbType(DbType.MARIADB)
                                .setDriverName("com.mysql.cj.jdbc.Driver")
                                .setUrl("jdbc:mysql://ddns.yuany3721.top:1117/ir")
                                .setUsername("root")
                                .setPassword("167012");
                autoGenerator.setDataSource(dataSourceConfig);

                // 策略配置
                StrategyConfig strategy = new StrategyConfig();
                strategy.setTablePrefix("")// 表前缀
                                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                                .setRestControllerStyle(true)
                                .setSuperMapperClass(null);
                strategy.setInclude("author", "doc", "inverted", "title_inverted"); // 需要生成的表
                autoGenerator.setStrategy(strategy);

                // 包配置
                PackageConfig packageConfig = new PackageConfig();
                packageConfig.setParent("top.yuany3721.ir")
                                .setController("controller")
                                .setService("service")
                                .setServiceImpl("service.impl")
                                .setMapper("dao")
                                .setEntity("entity")
                                .setXml("mapper");
                autoGenerator.setPackageInfo(packageConfig);

                // 执行生成
                autoGenerator.execute();

        }

}