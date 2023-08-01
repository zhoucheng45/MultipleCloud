git pull
docker build --build-arg PROJECT_NAME=$1 -t multiple-cloud:v1 .

docker tag multiple-cloud:v1  registry.cn-hongkong.aliyuncs.com/hikbs/multiple-cloud:v1
docker push  registry.cn-hongkong.aliyuncs.com/hikbs/multiple-cloud:v1
