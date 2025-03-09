
读取护照MRZ信息，如何得到密钥，去读取芯片信息

MRZ（Machine Readable Zone，机读区）：护照底部两行包含个人信息和证件信息的区域。这些信息是后续访问芯片的关键。




从MRZ信息中派生密钥

BAC密钥的派生涉及对MRZ中的某些字段进行特定的组合和处理。这些字段通常包括：
护照号码
出生日期
有效期
这些数据需要按照ICAO 9303标准规定的方式进行处理，生成用于后续芯片通讯的密钥。


建立与芯片的安全通信

使用派生出的BAC密钥，通过特定的通信协议（如ISO 14443）与护照芯片建立安全的通信通道。
这个过程包括相互认证，以确保只有授权的设备才能访问芯片数据。
4. 读取芯片数据

一旦建立了安全的通信通道，就可以按照ICAO LDS（Logical Data Structure）标准读取芯片中存储的个人生物特征信息和其他数据。
这些数据可能包括：
持照人照片
指纹信息
其他个人数据


ISS CHOUTRY  :CN
doc type:  passport
doc number:         ea...
expriry date: 2035-11-11
given name:   ttt
surname:  aaa
nationliy  :cn
issuer:   psss signg certfify ,ministr forengn arffair,,cn gov
foto:   xxx


相关标准和规范：

ICAO Doc 9303：机读旅行证件标准，详细规定了MRZ的格式和BAC机制。
ISO/IEC 14443：非接触式智能卡标准，定义了与芯片通信的协议。
ICAO LDS：逻辑数据结构标准，定义了芯片中数据的存储格式