#docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=base-admin-web

#容器id
CID=$(docker ps | grep "$SERVER_NAME" | awk '{print $1}')
#镜像id
IID=$(docker images | grep "$SERVER_NAME" | awk '{print $3}')

if [ -e dist ]
then
    rm -f $FILE
fi
cp -r ../dist ./

# 构建docker镜像
if [ -n "$IID" ]; then
        echo "删除旧$SERVER_NAME镜像，IID=$IID"
        docker rmi $IID
        echo "已删除$SERVER_NAME，重新构建镜像"
        docker build -t $SERVER_NAME .
else
        echo "不存在$SERVER_NAME镜像，开始构建镜像"
        docker build -t $SERVER_NAME .
        echo "构建完成！即将运行$SERVER_NAME"
fi
# 停掉原来的
if [ -n "$CID" ]; then
        echo "正在停止$SERVER_NAME，CID=$CID"
        docker stop $CID
        echo "已停止！"
else
        echo "容器 $SERVER_NAME 未运行"
fi

# 运行docker容器
docker run \
--name $SERVER_NAME\
--rm \
-d\
-p 18092:80\
$SERVER_NAME

echo "$SERVER_NAME 已启动..."
