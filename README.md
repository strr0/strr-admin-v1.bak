## strr-admin

### 项目介绍

项目依赖

| 依赖                     | 版本             |
|------------------------|----------------|
| Spring Boot            | 2.3.7.RELEASE  |
| Spring Security OAuth2 | 2.3.7.RELEASE  |
| Mybatis                | 3.5.7          |

项目结构

```
strr-admin
├── strr-boot -- 入口
└── strr-common -- 公共模块
     └── strr-common-core -- 公共核心：基础CRUD封装及工具类
└── strr-module -- 通用模块
     ├── strr-system -- 系统模块
     └── strr-generator -- 代码生成器
```

### 使用

1. 拉取代码
```
git clone https://github.com/strr0/strr-admin-v1.bak
```

2. 导入 script 的 schema.sql 文件

3. 运行 strr-boot
