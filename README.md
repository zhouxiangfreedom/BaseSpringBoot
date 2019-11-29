# BaseSpringBoot

# 一、中间件使用

## 1、RabbitMQ
1. [配置文件](src/main/java/com/zhou/basespringboot/config/RabbitMqConfig.java) 
2. [发送消息示例](src/main/java/com/zhou/basespringboot/service/RabbitMqService.java)
3. [接收消息示例](src/main/java/com/zhou/basespringboot/listener/RabbitMqListener.java)
4. RabbitMQ docker镜像使用
    ```
    # 拉取镜像
    docker pull rabbitmq:3-management
    # 后台启动rabbitmq，暴露端口15673(管理界面接口)及5673(tcp连接端口)
    docker run -d -p 15673:15672 -p 5673:5672 rabbitmq:3-management
    ```
    - Admin界面url: **(http://localhost:15673)**
    - 停止运行 
        - `docker ps -l` 查看最新的运行的容器，
        -   `docker stop {containerId}` 停止指定的容器
    - 查看容器日志
        - `docker logs -f {containerId}` 
    - 进入容器
        - `docker exec -it {containerId} /bash` 