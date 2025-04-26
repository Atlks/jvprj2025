
sftwr dev spec model 

常见实体规范

<!-- TOC -->

- [1. user，，openid  passport规范](#1-useropenid--passport规范)
- [2. pwd 没有规范，awsp推荐](#2-pwd-没有规范awsp推荐)
- [3. auth token，，visa规范](#3-auth-tokenvisa规范)
- [4. 金融行业acc，trax   obie openbank规范](#4-金融行业acctrax---obie-openbank规范)
- [5. ogs开放游戏api](#5-ogs开放游戏api)
- [6. ------------nml](#6-------------nml)
- [7. store  jpa  jdo](#7-store--jpa--jdo)
- [8. role](#8-role)
- [9. sns  new](#9-sns--new)
- [10. im，，tg group实体，msg实体规范](#10-imtg-group实体msg实体规范)
- [11. bean vald](#11-bean-vald)

<!-- /TOC -->


常见实体规范

# 1. user，，openid  passport规范

# 2. pwd 没有规范，awsp推荐

# 3. auth token，，visa规范

# 4. 金融行业acc，trax   obie openbank规范

# 5. ogs开放游戏api


# 6. ------------nml

# 7. store  jpa  jdo

# 8. role   

医疗物流行业
电商
教育
制造业，通讯，交通。娱乐
gv，，

# 9. sns  new
音乐的元数据规范

# 10. im，，tg group实体，msg实体规范
得推荐：

参照tg吧，有文档规范

参考 XMPP 和 Matrix 协议：它们是 IM 行业的“事实标准


# 11. bean vald

# Open Game Data Schemas（社区规范

player , game 

2. 大型平台的“隐性规范”
✅ Steamworks（Valve）
提供了对玩家、成就、排行榜、匹配的结构化支持：

CUserStats: 记录玩家分数、经验、成就。

ISteamMatchmaking: 处理 lobby、matchmaking 等字段。

玩家字段示例：steamID、personaName、score、achievements


✅ PlayFab（微软）
PlayFab 是一个后端即服务平台，公开了大量实体模型：

Player Entity
playerId

displayName

inventory

virtualCurrency

statisticValues

linkedAccounts

tags

GameSession Entity
sessionId

players

region

state

createdAt