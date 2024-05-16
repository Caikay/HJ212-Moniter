package com.github.guocay.hj212.netty;

import com.github.guocay.hj212.business.service.MonitorService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

    private final MonitorService service;

    public NettyServerHandler(MonitorService service) {
        this.service = service;
    }

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        //ctx.channel().remoteAddress().toString()的第一位是"/".
        log.info("System Message ==> Client Channel Create Success, Client IP/Port: {}",
                ctx.channel().remoteAddress().toString().substring(1));
    }

    /**
     * 对每一个传入的消息都要调用；
     * @param ctx 上下文
     * @param msg 消息
     * @throws Exception 异常
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx,msg);
        String response = service.monitor((String)msg,ctx.channel().remoteAddress().toString().substring(1));
        if(response != null) {ctx.write(response);}
        ctx.flush();
    }

    /**
     * 通知ChannelInboundHandler最后一次对channelRead()的调用时当前批量读取中的的最后一条消息。
     * @param ctx 上下文
     * @throws Exception 异常
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 在读取操作期间，有异常抛出时会调用。
     * 关闭TCP连接。
     * @param ctx 上下文
     * @param cause 异常
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx,cause);
        cause.printStackTrace();
        ctx.close();
    }
}
