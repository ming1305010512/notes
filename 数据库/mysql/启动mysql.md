在命令提示框中输入net start mysql命令即可，在这过程中，如果出现服务名无效，解决方法如下：
1、使用管理员的身份打开命令提示框
2、在mysql安装的bin目录下执行：mysqld --install
在这个步骤中，如果出现install.Remove of the service Denied错误，则是因为打开cmd.exe程序时没有以管理员的身份打开
3、在输入net start mysql