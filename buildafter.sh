git pull
img_version=v1$1

cd $1
mvn clean package -DskipTests=true
cd ..
docker build -f Dockerfileafter1 -t multiple-cloud:$img_version .

docker tag multiple-cloud:$img_version  registry-intl.cn-hongkong.aliyuncs.com/my-link/multiple-cloud:$img_version
docker push  registry-intl.cn-hongkong.aliyuncs.com/my-link/multiple-cloud:$img_version
