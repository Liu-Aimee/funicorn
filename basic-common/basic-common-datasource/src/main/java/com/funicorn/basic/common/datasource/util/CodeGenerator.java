package com.funicorn.basic.common.datasource.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 * @author Aimee
 * @since 2021/10/28
 */
@Data
public class CodeGenerator {

	/**
	 * 数据库连接
	 * */
	private String jdbcUrl;
	/**
	 * 账号
	 * */
	private String username;
	/**
	 * 密码
	 * */
	private String password;
	/**
	 * 数据库驱动类
	 * */
	private String driverClassName;
	/**
	 * 项目路径 默认System.getProperty("user.dir")
	 * */
	private String projectPath;
	/**
	 * java类存储路径 默认/src/main/java
	 * */
	private String javaPath;
	/**
	 * 包名
	 * */
	private String packageName;
	/**
	 * 表前缀 表生成代码时会去掉前缀
	 * */
	private String tablePrefix;
	/**
	 * 作者
	 * */
	private String author;
	/**
	 * 是否生成swagger注解
	 * */
	private boolean enableSwagger;
	/**
	 * 命名规则
	 * */
	private NamingStrategy namingStrategy;

	public CodeGenerator(){
		this.projectPath = System.getProperty("user.dir");
		this.javaPath = "/src/main/java";
		this.author = "CodeGenerator";
		this.namingStrategy = NamingStrategy.underline_to_camel;
		this.setEnableSwagger(false);
	}

	/**
	 * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
	 * 如输入表名称 "t_user_detail" 将生成
	 * TUserDetail、TUserDetailMapper、TUserDetailService ...
	 * 
	 * @param tableNames
	 *            数据表名称...
	 */
	public void generateCode(String... tableNames) {

		// 0.代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// 1.全局配置
		GlobalConfig gc = getGlobalConfig();
		mpg.setGlobalConfig(gc);

		// 2.数据源配置
		DataSourceConfig dsc = getDataSourceConfig();
		mpg.setDataSource(dsc);

		// 3.包配置
		PackageConfig pc = getPackageConfig();
		mpg.setPackageInfo(pc);

		// 4.自定义配置
		InjectionConfig cfg = getInjectionConfig();
		mpg.setCfg(cfg);

		// 5.模板配置
		TemplateConfig templateConfig = getTemplateConfig();
		mpg.setTemplate(templateConfig);
		// 使用Freemarker模板引擎
		mpg.setTemplateEngine(new VelocityTemplateEngine());

		// 6.策略配置
		StrategyConfig strategy = getStrategyConfig(tableNames);
		mpg.setStrategy(strategy);

		// 7.开始生成代码
		mpg.execute();
	}


	/** 1.全局配置 */
	private GlobalConfig getGlobalConfig() {
		GlobalConfig config = new GlobalConfig();
		config.setOutputDir(this.projectPath + this.javaPath);
		config.setAuthor(this.author);
		config.setOpen(false);
		// 自定义生成的ServiceName，I去掉默认的ServiceName前面的
		config.setServiceName("%s" + ConstVal.SERVICE);
		// 实体属性 Swagger2 注解
		config.setSwagger2(this.enableSwagger);
		config.setIdType(IdType.ASSIGN_ID);
		return config;
	}

	/** 2.数据源配置 */
	private DataSourceConfig getDataSourceConfig() {
		DataSourceConfig dataSource = new DataSourceConfig();
		dataSource.setDriverName(this.driverClassName);
		dataSource.setUrl(this.jdbcUrl);
		dataSource.setUsername(this.username);
		dataSource.setPassword(this.password);
		return dataSource;
	}

	/** 3.包配置 */
	private PackageConfig getPackageConfig() {
		PackageConfig pc = new PackageConfig();
		// 生成PACKAGE_NAME.MODULE_NAME的包路径
		pc.setParent(this.packageName);
		return pc;
	}

	/** 4.自定义配置 */
	private InjectionConfig getInjectionConfig() {

		// 这里模板引擎使用的是vm
		String templatePath = "/template/Mapper.xml.vm";

		// 自定义输出配置
		List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化
				return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper"
						+ StringPool.DOT_XML;
			}
		});

		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};
		cfg.setFileOutConfigList(focList);
		return cfg;
	}

	/** 5.模板配置 */
	private static TemplateConfig getTemplateConfig() {
		TemplateConfig templateConfig = new TemplateConfig();

		// 配置自定义输出模板
		// 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
		templateConfig.setEntity("/template/Model.java");
		templateConfig.setService("/template/Service.java");
		templateConfig.setServiceImpl("/template/ServiceImpl.java");
		templateConfig.setController("/template/Controller.java");
		templateConfig.setMapper("/template/Mapper.java");
		templateConfig.setXml(null);
		return templateConfig;
	}

	/** 6.策略配置 */
	private StrategyConfig getStrategyConfig(String... tableNames) {
		StrategyConfig strategy = new StrategyConfig();
		// 下划线转驼峰命名
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		strategy.setRestControllerStyle(true);
		strategy.setInclude(tableNames);
		strategy.setControllerMappingHyphenStyle(true);
		//生成时去掉表前缀
		if (StringUtils.isNotBlank(this.tablePrefix)){
			strategy.setTablePrefix(this.tablePrefix);
		}
		return strategy;
	}
}
