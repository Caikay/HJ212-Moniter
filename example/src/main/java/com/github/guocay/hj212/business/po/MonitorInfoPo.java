package com.github.guocay.hj212.business.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 接收到的原始监测信息
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_monitor_info")
public class MonitorInfoPo {

    @TableId("id")
    private String id;
	/**
	 * 原始内容
	 */
    @TableField("info")
    private String info;

	/**
	 * ip地址
	 */
    @TableField("ip_address")
    private String IPAddress;

    @TableField("time")
    private LocalDateTime time;

}
