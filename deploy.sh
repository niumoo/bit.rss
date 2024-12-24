#!/bin/bash
# 函数用于写入日志
log() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - $1"
}
# 停止脚本，如果有命令失败
set -e

# 拉取最新代码
log "正在执行 git pull..."
git pull
log "git pull 完成"

# 打包项目，跳过测试
log "正在执行 mvn package，跳过测试..."
mvn package -Dmaven.test.skip=true
log "mvn package 完成"

# 结束正在运行的服务
log "正在查找并终止正在运行的 bit-rss 服务..."
PID=$(pgrep -f bit-rss)
if [ -n "$PID" ]; then
    kill -9 $PID
    log "成功终止 bit-rss 服务，PID: $PID"
else
    log "未找到正在运行的 bit-rss 服务"
fi

# 启动新的服务实例并记录日志
log "正在启动新的 bit-rss 服务..."
nohup java -jar bit-rss-server/target/bit-rss-server-0.0.1-SNAPSHOT.jar > /dev/null 2>&1 &
log "bit-rss 服务已启动"
log "部署完成."
