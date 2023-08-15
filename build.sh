git pull
img_version=v1$1
docker build -f Dockerfile$1 -t multiple-cloud:$img_version .

docker tag multiple-cloud:v1  registry.cn-hongkong.aliyuncs.com/hikbs/multiple-cloud:$img_version
docker push  registry.cn-hongkong.aliyuncs.com/hikbs/multiple-cloud:$img_version
