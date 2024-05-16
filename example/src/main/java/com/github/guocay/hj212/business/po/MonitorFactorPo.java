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
 * 存储监控因素相关的数据
 */
@TableName("t_monitor_factor")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitorFactorPo {
	/**
     * 主键
     */
    @TableId("id")
    private String id;

	/**
     * 监控设备ID
     */
    @TableField("mncode")
    private String mncode;

	/**
     * 监控数据时间
     */
    @TableField("data_time")
    private String dataTime;

	/**
     * 监控因子
     */
    @TableField("factor")
    private String factor;

	/**
     * 监控因子值
     */
    @TableField("value")
    private String value;

	/**
     * 监控数据状态
     */
    @TableField("data_status")
    private String dataStatus;

    @TableField("time")
    private LocalDateTime time;
}
