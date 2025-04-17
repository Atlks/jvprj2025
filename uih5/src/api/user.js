import service from '@/utils/request';

export function login({username, password, validate}) {
  return service({
    url: '/api/user/auth/account-login',
    method: 'post',
    data: {userName: username, password, ...validate},
  });
}

export function register({username, password, validate}) {
  const inviteCode = getInviteCode();
  const referCode = getReferCode();
  const regDomain = window.location.origin;
  return service({
    url: '/api/user/auth/account-register',
    method: 'post',
    data: {userName: username, password, countryCode: 'PH', inviteCode, referCode, ...validate, regDomain},
  });
}

export function phoneLogin({phone, code}) {
  const inviteCode = getInviteCode();
  const referCode = getReferCode();
  return service({
    url: '/api/user/auth/mobile-login',
    method: 'post',
    data: {mobile: phone, smsCode: code, countryCode: 'PH', inviteCode, referCode},
  });
}

export function phoneRegister(params) {
  let data = params;
  data.regDomain = window.location.origin;
  data.countryCode = 'PH';
  return service({
    url: '/api/user/auth/ph/register/v1',
    method: 'post',
    data,
  });
}

export function registerCheckByMobile(params) {
  return service({
    url: `/api/user/auth/mobile-registerCheckByMobile/${params.phone}/${params.phoneCode}`,
    method: 'get',
  });
}

export function registerCheckByCode(params) {
  return service({
    url: `/api/user/auth/mobile-registerCheckByCode/${params.phone}/${params.code}`,
    method: 'get',
  });
}

export function registerCreatName() {
  return service({
    url: `/api/user/auth/mobile-registerCreatName`,
    method: 'get',
  });
}

export function getLogout(data) {
  return service({
    url: '/api/user/auth/logout',
    method: 'post',
    data,
  });
}

// 一键回收
export function getCollect(data) {
  data = data || {};
  data.noNeedResponseMsg = true;
  return service({
    url: `/api/game/fund/collect`,
    method: 'post',
    data,
  });
}

// 获取用户钱包余额
export function getBalance(data) {
  data = data || {};
  data.noNeedResponseMsg = true;
  return service({
    url: `/api/game/fund/wallet`,
    method: 'post',
    data,
  });
}

// 个人识别码
export function getUserRefreshIdentityCode(params) {
  params = params || {};
  params.noNeedResponseMsg = true;
  return service({
    url: '/api/member/refreshIdentityCode',
    method: 'get',
    params,
  });
}

// 用户信息
export function getUserInfo(params) {
  params = params || {};
  params.noNeedResponseMsg = true;
  return service({
    url: '/api/member/get',
    method: 'get',
    params,
  });
}

// 领取奖励
export function drawAward(data) {
  return service({
    url: `/api/member/invite/drawAward`,
    method: 'post',
    data: data || {},
  });
}

// 生成邀请码
export function generateReferralCode(params) {
  return service({
    url: `/api/member/invite/generateReferralCode`,
    method: 'get',
    params,
  });
}

// 跑马灯
export function queryHorseRing(params) {
  return service({
    url: `/api/member/invite/queryHorseRing`,
    method: 'get',
    params,
  });
}

// 查询邀请好友流水奖励配置
export function queryInviteBetConfig(params) {
  return service({
    url: `/api/member/invite/queryInviteBetConfig`,
    method: 'get',
    params,
  });
}

// 查询邀请好友首存奖励配置
export function queryInviteDepConfig(params) {
  return service({
    url: `/api/member/invite/queryInviteDepConfig`,
    method: 'get',
    params,
  });
}

// 邀请信息
export function queryInviteInfo(params) {
  return service({
    url: `/api/member/invite/queryInviteInfo`,
    method: 'get',
    params,
  });
}

// 查询邀请好友vip奖励配置
export function queryInviteVipConfig(params) {
  return service({
    url: `/api/member/invite/queryInviteVipConfig`,
    method: 'get',
    params,
  });
}

// 邀请详情流水分成奖励列表
export function queryInviteBetAwardList(params) {
  return service({
    url: `/api/member/invite/queryInviteBetAwardList`,
    method: 'get',
    params,
  });
}

// 邀请详情首存奖励列表
export function queryInviteDepAward(params) {
  return service({
    url: `/api/member/invite/queryInviteDepAward`,
    method: 'get',
    params,
  });
}

// 邀请详情累计发放金额和邀请人数
export function queryInviteInfoTotalAward(params) {
  return service({
    url: `/api/member/invite/queryInviteInfoTotalAward`,
    method: 'get',
    params,
  });
}

export function getKycDays() {
  return service({
    url: `/api/operation/advice/config/kycDays`,
    method: 'post',
  });
}

export function sum(data) {
  return service({
    url: `/api/game/bet/order/mybet/sum`,
    method: 'post',
    data: data || {},
  });
}
export function sumDetail(data) {
  return service({
    url: `/api/game/bet/order/mybet/detail`,
    method: 'post',
    data: data || {},
  });
}

// 查询城市地址
export function getCity(data) {
  return service({
    url: '/api/user/sys/province',
    method: 'post',
    data,
  });
}
// 用户提交个人信息
export function submitPersonalApi(data) {
  return service({
    url: `/api/member/personal/information/submit`,
    method: 'post',
    data,
  });
}

// 提交意见反馈
export const submitAdvice = (data) => {
  return service({
    url: `/api/operation/advice/config/saveAdviceConfig`,
    method: 'post',
    data,
  });
};
// 提交意见反馈
export function getGameListApi(params) {
  return service({
    url: '/api/game/home/bar/list',
    method: 'get',
    params,
  });
}
export function getGameBetDetail(data) {
  return service({
    url: '/api/game/bet/order/mybet/detail',
    method: 'post',
    data,
  });
}
// 获取用户IP是否可访问
export function getStoreIp(data) {
  return service({
    url: '/api/user/getStoreIp',
    method: 'post',
    data,
  });
}

//注册KYC 图片上传
export function regKycUpload(data) {
  return service({
    url: '/api/file/registerKyc/image/upload',
    method: 'post',
    data,
  });
}

//判断是否展示 注册KYC
export function regKyc(data) {
  return service({
    url: '/api/user/auth/ph/registerKycFlag',
    method: 'post',
    data,
  });
}

// /app/latestApp
export function getQRcode(data) {
  return service({
    url: `/api/user/app/latestApp`,
    method: 'post',
    data,
  });
}

export function checkAgentIdApi(params) {
  return service({
    url: `/api/user/getStatusByAgentAccount`,
    method: 'get',
    params,
  });
}
