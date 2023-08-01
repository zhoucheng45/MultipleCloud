git pull
docker build -t multiple-cloud:v1 .

docker tag multiple-cloud:v1  registry.cn-hongkong.aliyuncs.com/hikbs/multiple-cloud:v1
docker push  registry.cn-hongkong.aliyuncs.com/hikbs/multiple-cloud:v1
