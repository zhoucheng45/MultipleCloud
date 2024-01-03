import paramiko

# 远程服务器的信息
hostname = '94.74.126.193'  #  '远程服务器IP'
port = 22  # SSH端口，默认为22
username = 'root'
password = 'Hwcloud@123'

# 创建SSH客户端
client = paramiko.SSHClient()
client.set_missing_host_key_policy(paramiko.AutoAddPolicy())  # 自动添加主机密钥

# 连接服务器
client.connect(hostname, port, username, password)

# 在远程服务器上执行命令
stdin, stdout, stderr = client.exec_command('ls -al')  # 用你想要执行的命令替换'ls'

# 输出命令结果
for line in stdout:
    print('... ' + line.strip('\n'))

# 关闭连接
client.close()