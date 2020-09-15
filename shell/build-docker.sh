#docker 镜像/容器名字或者jar名字 这里都命名为这个
SERVER_NAME=base-admin-web
echo "---------start 准备构建$SERVER_NAME---------"

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
        echo "删除$SERVER_NAME镜像，IID=$IID"
        docker rmi $IID
        echo "已删除$SERVER_NAME，重新构建镜像"
        docker build -t $SERVER_NAME .
else
        echo "开始构建$SERVER_NAME"
        docker build -t $SERVER_NAME .
        echo "$SERVER_NAME构建完成！准备启动"
fi
# 停掉原来的
if [ -n "$CID" ]; then
        echo "正在停止$SERVER_NAME，CID=$CID"
        docker stop $CID
        echo "$SERVER_NAME已停止！"
else
        echo "容器 $SERVER_NAME 未运行，即将启动..."
fi

# 运行docker容器
echo docker run \
--rm \
--name $SERVER_NAME \
-d \
-p 8103:80 \
$SERVER_NAME

if [ $? -ne 0 ];then
    echo "$SERVER_NAME 启动失败！"
else
    echo "$SERVER_NAME 已启动..."
fi
echo "---------end 准备构建$SERVER_NAME---------"
