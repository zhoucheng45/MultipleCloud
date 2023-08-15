git pull
img_version=v1$1
docker build -f Dockerfile$1 -t multiple-cloud:$img_version .

docker tag multiple-cloud:v1  registry-intl.cn-hongkong.aliyuncs.com/my-link/multiple-cloud:$img_version
docker push  registry-intl.cn-hongkong.aliyuncs.com/my-link/multiple-cloud:$img_version
