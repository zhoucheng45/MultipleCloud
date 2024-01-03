#!/usr/bin/python3
import random
from datetime import datetime
import redis
import threading
import time

# 连接Redis服务器
# client = redis.Redis(host='redis-0c543330-4917-46a4-9b0f-76cdee755b6b.ap-southeast-1.dcs.myhuaweicloud.com', port=6379, password="Hwcloud@123", db=1)
#client = redis.Redis(host='r-3nswk6rvbbfat509mm.redis.rds.aliyuncs.com', port=6379, password="Hwcloud@123", db=1)
begin = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
print("begin=", begin)


# 循环写数据
def writeRedis(number):
    threading.current_thread().name = "worker_thread" + str(number)
    client1 = redis.Redis(host='r-3nswk6rvbbfat509mm.redis.rds.aliyuncs.com', port=6379, password="Hwcloud@123", db=1)
    # randint = random.randint(a=1, b=100)
    i = 0
    while True:
        # client1.setex("key:" + str(randint) + ":" + str(i), time=60 * 60, value=str(randint))
        client1.incr("counter")
        if i % 100 == 0:
            time.sleep(1)  # 暂停1毫秒

        if i % 100 == 0:
            print(threading.current_thread().name, ": i=", i)

        i += 1



# 创建线程对象
threads = []
for i in range(3):
    t = threading.Thread(target=writeRedis, args=(i,))  # target是要执行的函数，args是函数的参数
    threads.append(t)
    t.start()  # 启动线程

# 等待线程结束
for t in threads:
    t.join()

end = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
print("begin=", begin)
print("end=", end)
