package com.github.guocay.hj212.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.guocay.hj212.core.validator.field.CharArray;
import com.github.guocay.hj212.core.validator.field.Date;
import com.github.guocay.hj212.model.verify.groups.ModeGroup;
import com.github.guocay.hj212.model.verify.groups.VersionGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 数据段
 * @author aCay
 */
@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unused")
public class Data {

    public static String CP = "CP";
    public static String FLAG = "Flag";

    /**
     * 请求编号
     */
    @Date(format = "yyyyMMddHHmmssSSS")
    @JsonProperty("QN")
    private String qn;

    /**
     * 总包号
     */
    @Max(value = 9999)
    @Min(value = 1, groups = ModeGroup.UseSubPacket.class)
    @JsonProperty("PNUM")
    private Integer pNum;

    /**
     * 包号
     */
    @Max(value = 9999)
    @Min(value = 1, groups = ModeGroup.UseSubPacket.class)
    @JsonProperty("PNO")
    private Integer pNo;

    /**
     * 系统编号
     */
    @CharArray(len = 2)
    @JsonProperty("ST")
    private String st;

    /**
     * 命令编号
     */
    @CharArray(len = 4)
    @JsonProperty("CN")
    private String cn;

    /**
     * 访问密码
     */
    @CharArray(len = 6)
    @JsonProperty("PW")
    private String pw;

    /**
     * 设备唯一标识
     */
    @CharArray(len = 14, groups = VersionGroup.V2005.class)
    @CharArray(len = 24, groups = VersionGroup.V2017.class)
    @JsonProperty("MN")
    private String mn;

    /**
     * 是否拆分包及应答标志
     */
    @JsonProperty("Flag")
    private List<DataFlag> dataFlag;

    /**
     * 指令参数
     */
    @Valid
    @JsonProperty("CP")
    private CpData cp;


}
