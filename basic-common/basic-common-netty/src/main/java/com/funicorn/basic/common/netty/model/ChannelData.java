package com.funicorn.basic.common.netty.model;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aimee
 * @since 2021/5/10 13:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelData {

    /** 通道id */
    private Channel channel;
    /** 客户端ip */
    private String ipAddress;
}
