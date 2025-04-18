package service;

import entityx.usr.MRZ;
import entityx.usr.Passport;
import entityx.usr.Visa;

import java.time.LocalDate;
import java.util.List;


import static util.algo.EncryUtil.*;
import static util.oo.JsonUtil.deserializeFromJson;
import static util.oo.JsonUtil.serializeToJson;

public class VisaService {

    public Visa applyForVisa(Passport passport, String country, String visaType) throws Exception {
        // 1. 检查护照是否有效
//        if (!isPassportValid(passport)) {
//            throw new Exception("护照已过期或无效");
//        }

        // 2. 检查护照签发国是否符合该国签证政策
//        if (!isEligibleForVisa(passport.getNationality(), country)) {
//            throw new Exception("您的国籍不符合 " + country + " 的签证政策");
//        }

        // 3. 检查签证类型是否合法
        if (!isValidVisaType(visaType)) {
            throw new Exception("签证类型 " + visaType + " 不被支持");
        }

        // 4. 检查是否有拒签历史（此处仅为示例，可扩展数据库查询）
//        if (hasVisaRejectionHistory(passport.getPassportNumber(), country)) {
//            throw new Exception("您曾被 " + country + " 拒签，无法申请签证");
//        }

        // 5. 所有条件符合，发放签证
        return issueVisa(passport, country, visaType);
    }

    private boolean isPassportValid(Passport passport) {
        return LocalDate.parse(passport.getExpiryDate()).isAfter(LocalDate.now());
    }

    private boolean isEligibleForVisa(String nationality, String country) {
        // 假设有一份名单规定了哪些国家公民可以申请
        List<String> allowedCountries = List.of("Philippines", "Thailand", "Malaysia", "India");
        return allowedCountries.contains(nationality);
    }

    private boolean isValidVisaType(String visaType) {
        // 假设支持这些签证类型
        List<String> validVisaTypes = List.of("Tourist", "Business", "Work", "Student");
        return validVisaTypes.contains(visaType);
    }

    private boolean hasVisaRejectionHistory(String passportNumber, String country) {
        // 假设数据库中有历史记录（此处返回 false，模拟没有拒签历史）
        return false;
    }

    private Visa issueVisa(Passport passport, String country, String visaType) throws Exception {
        Visa visa = new Visa();
        visa.setVisaNumber("VISA" + System.currentTimeMillis());
        visa.setCountry(country);
        visa.setVisaType(visaType);
        visa.setIssueDate(LocalDate.now().toString());
        LocalDate localDateExp = LocalDate.now().plusMonths(6);
        visa.setExpiryDate(localDateExp.toString()); // 假设签证有效期6个月
        visa.setStatus("Approved");
        visa.linkPassport = passport;
        MRZ MRZ1 = new MRZ("uke", visa.getVisaNumber(), "", passport.getHolderName(), passport.getPassportNumber(), LocalDate.now(), localDateExp);
        visa.MRZ1 = encryVisaMrz(MRZ1);
        // 关联签证到护照
       // passport.getVisas().add(visa);
        return visa;
    }
    //使用des加密 ，密钥为a1235678
    private String encryVisaMrz(MRZ mrz1) throws Exception {
        String mrzData = serializeToJson(mrz1);// 获取 MRZ 码


        return encryptAesToStrBase64(mrzData, Key4pwd4aeskey);
    }

    private MRZ decryVisaMrz(String encryptedData) throws Exception {
        var jsonstr = decryptAesFromStrBase64(encryptedData,  Key4pwd4aeskey);
        return deserializeFromJson(jsonstr, MRZ.class);
    }



}

