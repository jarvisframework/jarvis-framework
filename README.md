<p align="center">
    <img src="https://img.shields.io/badge/Release-V1.0.0-green.svg" alt="Downloads">
    <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" alt="Build Status">
    <img src="https://img.shields.io/badge/license-Apache%202-blue.svg" alt="Build Status">
    <img src="https://img.shields.io/badge/Spring%20Cloud-2021-blue.svg" alt="Coverage Status">
    <img src="https://img.shields.io/badge/Spring%20Boot-2.7.1-blue.svg" alt="Downloads">
</p>

## jarvis-framework

* Jarvis framework 快速开发框架
* 吸spring mvc、mybatis之精华，集日月之神奇。历时七七四十九天（远远不止于此）编码而成。基于Java语言对API进行了尽可能全面、优雅地封装，用以方便各位朋友使用与学习。

## 工程结构

```
jarvis-framework
├── doc -- 项目文档
├── jarvis-autoconfigure -- 自动配置类库(有代码混淆)
├── jarvis-baseweb -- 基础web类库
├── jarvis-dependencies -- 依赖管理类库
├── jarvis-modules -- 模块类库
    ├── jarvis-bizlog -- 业务日志类库
    ├── jarvis-core -- 核心类库
    ├── jarvis-cypto -- 密钥类库
    ├── jarvis-databse-upgrade -- 数据库脚本升级类库
    ├── jarvis-javax-persistence -- ****
    ├── jarvis-mybatis -- mybatis类库
    ├── jarvis-oauth2-authorization -- ****
    ├── jarvis-oauth2-common -- ****
    ├── jarvis-oauth2-resource -- ****
    ├── jarvis-openfeign -- openfeign类库
    ├── jarvis-redis -- redis类库
    ├── jarvis-resilience4j-openfeign -- ****
    ├── jarvis-security -- 安全类库
    ├── jarvis-token -- token类库
    ├── jarvis-web -- web类库
    ├── jarvis-webmvc -- webmvc类库
├── jarvis-parent -- 框架依赖管理
├── jarvis-starters -- starters
    ├── jarvis-bizlog-spring-boot-starter -- 业务日志起步依赖
    ├── jarvis-databse-upgrade-spring-boot-starter -- 数据库脚本升级起步依赖
    ├── jarvis-knife4j-spring-boot-starter -- 微服务knife4j起步依赖
    ├── jarvis-mybatis-security-spring-boot-starter -- mybatis安全起步依赖
    ├── jarvis-mybatis-spring-boot-starter -- mybatis起步依赖
    ├── jarvis-oauth2-authorization-spring-boot-starter -- ****
    ├── jarvis-jarvis-oauth2-resource-spring-boot-starter -- ****
    ├── jarvis-openfeign-spring-cloud-starter -- openfeign起步依赖
    ├── jarvis-security-spring-boot-starter -- 份验证和授权框架起步依赖
    ├── jarvis-spring-boot-starter -- spring起步依赖
    ├── jarvis-springfox-spring-boot-starter -- swagger起步依赖
    ├── jarvis-webmvc-spring-boot-starter -- webmvc起步依赖
├── jarvis-dev-ops -- 规约检查依赖
    
```

Feature
=========================
- 类`gitlab`的`RESTful API`，类`gitlab`的权限模型。将来打通`gitlab`，良心的惊喜
- 空间管理。意味着有独立的空间资源：环境管理、用户组、项目、服务器等
- 灰度发布。呼声不断，终于来了
- 项目管理。Deploy、Release的前置及后置hook，自定义全局变量；自带检测、复制功能，都贴心到这种程度了
- `websocket` 实时展示部署中的 `shell console`，跟真的终端长得一样。
- 完善的通知机制。邮件、钉钉

Architecture
=========================
![](https://raw.github.com/meolu/docs/master/walle-web.io/docs/2/zh-cn/static/walle-flow-relation.jpg)


Roadmap
=========================
- [x] **预览版**  2018-12-02
    - ~~安装文档、前后端代码、Data Migration~~

## 更新日志

### v2023.1.1-SNAPSHOT 2023.1.1
