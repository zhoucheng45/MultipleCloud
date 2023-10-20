import subprocess

def get_mac_address():
    cmd = 'ifconfig | grep "ether" | awk "{print $2}"'
    p = subprocess.Popen(cmd, stdout=subprocess.PIPE, shell=True)
    output, error = p.communicate()
    if error:
        print(f"Error: {error}")
    return output.decode('utf-8')

def get_ip_address():
    cmd = 'ifconfig | grep "inet " | awk "{print $2}"'
    p = subprocess.Popen(cmd, stdout=subprocess.PIPE, shell=True)
    output, error = p.communicate()
    if error:
        print(f"Error: {error}")
    return output.decode('utf-8')

print(get_mac_address())